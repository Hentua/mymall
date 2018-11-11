package com.mall.modules.account.service;

import com.mall.common.service.CrudService;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.IdGen;
import com.mall.modules.account.dao.AccountFlowDao;
import com.mall.modules.account.entity.AccountFlow;
import com.mall.modules.commission.entity.CommissionInfo;
import com.mall.modules.commission.service.CommissionConfigService;
import com.mall.modules.commission.service.CommissionInfoService;
import com.mall.modules.member.dao.MemberInfoDao;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.order.entity.OrderSettlement;
import com.mall.modules.order.service.OrderSettlementService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import com.sohu.idcenter.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 账户Service
 * @author hub
 * @version 2018-11-10
 */
@Service
@Transactional(readOnly = true)
public class AccountService extends CrudService<AccountFlowDao, AccountFlow> {

	private static IdWorker idWorker = new IdWorker();

	@Autowired
	private AccountFlowDao accountFlowDao;

	@Autowired
	private MemberInfoDao memberInfoDao;

	@Autowired
	private CommissionConfigService commissionConfigService;

	@Autowired
	private CommissionInfoService commissionInfoService;

	@Autowired
	private OrderSettlementService orderSettlementService;

	/**
	 * 修改账户余额
	 * @param balance
	 * @param commission
	 * @param userId
	 */
	@Transactional(readOnly = false)
	public void editAccount(Double balance,Double commission,String userId){
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("balance",balance);
		paramMap.put("commission",commission);
		paramMap.put("userId",userId);
		dao.editAccount(paramMap);
	}


	/**
	 * 余额消费
	 * @param amount
	 * @param paymentNo
	 * @param userId
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void consumption(Double amount,String paymentNo,String userId){

		MemberInfo m = new MemberInfo();
		m.setId(userId);
		MemberInfo memberInfo = memberInfoDao.get(m);
		if(null == memberInfo){
			throw new ServiceException("账户信息不存在");
		}
		if(amount > memberInfo.getBalance()){
			throw new ServiceException("账户余额不足");
		}

		//创建消费流水
		AccountFlow accountFlow = new AccountFlow();
		accountFlow.setFlowNo(String.valueOf(idWorker.getId()));
		accountFlow.setId(IdGen.uuid());
		accountFlow.setUserId(userId);
		accountFlow.setAmount(amount);
		accountFlow.setType("2");//支出
		accountFlow.setMode("4");//消费
		accountFlow.setOrderId(paymentNo);
		accountFlow.setCheckStatus("2");
		accountFlow.setCreateDate(new Date());
		accountFlowDao.insert(accountFlow);

		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("balance",memberInfo.getBalance() - amount);
		paramMap.put("commission",null);
		paramMap.put("userId",userId);
		dao.editAccount(paramMap);
	}



	/**
	 * 根据订单信息创建账单流水
	 * @param orderInfo
	 */
	@Transactional(readOnly = false)
	public void createAccountFlow(OrderInfo orderInfo) throws Exception{



		User merchantRefereeUser = null ;
		User customerRefereeUser = null;
		User merchant = null;

		if(null == orderInfo){
			logger.error("创建佣金流水失败：订单信息为空");
			return ;
		}
//		if(!"2".equals(orderInfo.getOrderStatus())){
//			logger.error("创建账单流水失败：订单状态无效");
//			return ;
//		}
		//卖家信息
		merchant = UserUtils.get(orderInfo.getMerchantCode());
		//买家信息
		User customer = UserUtils.get(orderInfo.getCustomerCode());
		if(null == merchant){
			logger.error("创建佣金流水失败：卖家信息为空");
			return ;
		}
		if(null == customer){
			logger.error("创建佣金流水失败：买家信息为空");
			return ;
		}
		//卖家推荐人
		merchantRefereeUser = UserUtils.get(merchant.getRefereeId());
		//买家推荐人
		customerRefereeUser = UserUtils.get(customer.getRefereeId());
		if(null == merchantRefereeUser){
			logger.error("创建佣金流水失败：卖家推荐人为空");
			return ;
		}
		if(null == customerRefereeUser){
			logger.error("创建佣金流水失败：买家推荐人为空");
			return ;
		}
		if(null == merchantRefereeUser || null == customerRefereeUser
				|| null == merchant){
			return;
		}
		//1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣
		//订单类型（0-平台自主下单，1-礼包兑换）
		if("0".equals(orderInfo.getOrderType())){
			//创建订单结算信息
			OrderSettlement orderSettlement = new OrderSettlement();
			orderSettlement.setOrderId(orderInfo.getId());
			orderSettlement.setSettlementAmount(orderInfo.getSettlementsAmount());
			orderSettlement.setUserId(orderInfo.getMerchantCode());
			orderSettlementService.save(orderSettlement);
			//新增佣金记录
			//卖家推荐人佣金

			CommissionInfo merchantRefereeCommission = new CommissionInfo();
			merchantRefereeCommission.setType("2");
			merchantRefereeCommission.setUserId(merchantRefereeUser.getId());
			merchantRefereeCommission.setProduceUserId(merchant.getId());
			merchantRefereeCommission.setOriginAmount(orderInfo.getGoodsAmountTotal());
			merchantRefereeCommission.setAmount(commissionConfigService.getCommissionAmount("2",orderInfo.getGoodsAmountTotal()));
			merchantRefereeCommission.setUnionId(orderInfo.getId());
			commissionInfoService.save(merchantRefereeCommission);

			//买家推荐人佣金
			CommissionInfo customerRefereeCommission = new CommissionInfo();
			customerRefereeCommission.setType("1");
			customerRefereeCommission.setUserId(customerRefereeUser.getId());
			customerRefereeCommission.setProduceUserId(customer.getId());
			customerRefereeCommission.setOriginAmount(orderInfo.getGoodsAmountTotal());
			customerRefereeCommission.setAmount(commissionConfigService.getCommissionAmount("1",orderInfo));
			customerRefereeCommission.setUnionId(orderInfo.getId());
			commissionInfoService.save(customerRefereeCommission);



		}
		//订单类型（0-平台自主下单，1-礼包兑换）
		if("1".equals(orderInfo.getOrderType())){
			//新增佣金记录
			//卖家推荐人佣金
			CommissionInfo merchantRefereeCommission = new CommissionInfo();
			//1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣
			merchantRefereeCommission.setType("4");
			merchantRefereeCommission.setUserId(merchantRefereeUser.getId());
			merchantRefereeCommission.setProduceUserId(merchant.getId());
			merchantRefereeCommission.setOriginAmount(orderInfo.getGoodsAmountTotal());
			merchantRefereeCommission.setAmount(commissionConfigService.getCommissionAmount("4",orderInfo.getGoodsAmountTotal()));
			merchantRefereeCommission.setUnionId(orderInfo.getId());
			commissionInfoService.save(merchantRefereeCommission);

			CommissionInfo merchantCommission = new CommissionInfo();
			//1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣
			merchantCommission.setType("5");
			merchantCommission.setUserId(merchant.getId());
			merchantCommission.setProduceUserId(merchant.getId());
			merchantCommission.setOriginAmount(orderInfo.getGoodsAmountTotal());
			merchantCommission.setAmount(commissionConfigService.getCommissionAmount("5",orderInfo.getGoodsAmountTotal()));
			merchantCommission.setUnionId(orderInfo.getId());
			commissionInfoService.save(merchantCommission);

		}
	}



}