package com.mall.modules.account.service;

import com.mall.common.service.CrudService;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.IdGen;
import com.mall.common.utils.StringUtils;
import com.mall.modules.account.dao.AccountFlowDao;
import com.mall.modules.account.entity.AccountFlow;
import com.mall.modules.commission.entity.CommissionConfig;
import com.mall.modules.commission.entity.CommissionInfo;
import com.mall.modules.commission.service.CommissionConfigService;
import com.mall.modules.commission.service.CommissionInfoService;
import com.mall.modules.goods.dao.GoodsCategoryDao;
import com.mall.modules.goods.dao.GoodsRecommendDao;
import com.mall.modules.goods.entity.GoodsCategory;
import com.mall.modules.goods.entity.GoodsRecommend;
import com.mall.modules.member.dao.MemberInfoDao;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.order.dao.OrderGoodsDao;
import com.mall.modules.order.entity.OrderGoods;
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

import java.util.*;

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

	@Autowired
	private OrderGoodsDao orderGoodsDao;

	@Autowired
	private GoodsRecommendDao goodsRecommendDao;

	@Autowired
	private GoodsCategoryDao goodsCategoryDao;

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
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
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
		merchantRefereeUser = UserUtils.get(merchant.getMerchantRefereeId());
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
		//1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣
		//订单类型（0-平台自主下单，1-礼包兑换）
		if("0".equals(orderInfo.getOrderType())){
			//创建订单结算信息
			OrderSettlement orderSettlement = new OrderSettlement();
			orderSettlement.setOrderId(orderInfo.getId());
			orderSettlement.setSettlementAmount(orderInfo.getSettlementsAmount());
			orderSettlement.setUserId(orderInfo.getMerchantCode());
			orderSettlement.setStatus("1");
			orderSettlementService.save(orderSettlement);
			//新增佣金记录
			//卖家推荐人佣金
			CommissionConfig config = commissionConfigService.getConfig("2");
			CommissionInfo merchantRefereeCommission = new CommissionInfo();
			merchantRefereeCommission.setType("2");
			merchantRefereeCommission.setUserId(merchantRefereeUser.getId());
			merchantRefereeCommission.setProduceUserId(merchant.getId());
			merchantRefereeCommission.setOriginAmount(orderInfo.getSettlementsAmount());
			merchantRefereeCommission.setAmount(commissionConfigService.getCommissionAmount("2",orderInfo.getSettlementsAmount()));
			merchantRefereeCommission.setUnionId(orderInfo.getOrderNo());
			merchantRefereeCommission.setMode(config.getMode());
			merchantRefereeCommission.setNumber(config.getNumber());
			commissionInfoService.save(merchantRefereeCommission);

			//买家推荐人佣金
			config = commissionConfigService.getConfig("1");
			CommissionInfo customerRefereeCommission = new CommissionInfo();
			customerRefereeCommission.setType("1");
			customerRefereeCommission.setUserId(customerRefereeUser.getId());
			customerRefereeCommission.setProduceUserId(customer.getId());
			customerRefereeCommission.setOriginAmount(orderInfo.getOrderAmountTotal());
			customerRefereeCommission.setAmount(commissionConfigService.getCommissionAmount("1",orderInfo));
			customerRefereeCommission.setUnionId(orderInfo.getOrderNo());
			customerRefereeCommission.setMode(config.getMode());
			customerRefereeCommission.setNumber(config.getNumber());
			commissionInfoService.save(customerRefereeCommission);

			List<OrderGoods> list = null;
			if(null != orderInfo.getOrderGoodsList() && orderInfo.getOrderGoodsList().size()!=0 ) {
				list = orderInfo.getOrderGoodsList();
			}else{
				list = orderGoodsDao.findList(new OrderGoods(orderInfo.getOrderNo()));
			}

			for (OrderGoods og: list) {
				if(!StringUtils.isEmpty(og.getGoodsRecommendId())){
					GoodsCategory gc = goodsCategoryDao.get(og.getGoodsCategoryId());
					Double amount = 0.0;
					//按固定金额计算
					if("1".equals(gc.getCommissionMode())){
						amount+= gc.getCommissionNumber();
					}
					//按百分比计算
					if("2".equals(gc.getCommissionMode())){
						if(null == gc.getCommissionNumber() || gc.getCommissionNumber() <= 0.0){
							amount+= 0.0;
						}
						//按交易金额 计算百分比
						amount+= gc.getCommissionNumber()/og.getGoodsPrice()*100;
					}

					String goodsRecommendId = og.getGoodsRecommendId();
					GoodsRecommend g = goodsRecommendDao.get(goodsRecommendId);
					CommissionInfo commissionInfo = new CommissionInfo();
					commissionInfo.setType("1");
					commissionInfo.setUserId(g.getUserId());
					commissionInfo.setProduceUserId(customer.getId());
					commissionInfo.setOriginAmount(og.getGoodsPrice());
					commissionInfo.setAmount(amount);
					commissionInfo.setMode(gc.getCommissionMode());
					commissionInfo.setNumber(gc.getCommissionNumber());
					commissionInfo.setUnionId(orderInfo.getOrderNo());
					commissionInfoService.save(commissionInfo);
				}
				orderGoodsDao.editGoodsSalesTotal(og);
			}
			commissionConfigService.setConfigs(null);
		}
	}


	/**
	 * 礼包送出创建佣金接口
	 * @param senUserId 送出用户ID
	 * @param receiverId  接收佣金人ID
	 * @param amount 礼包金额
	 * @param giftId 礼包ID
	 * @param merchantQualification  是否赠送商家资格 0-否 1-是
	 * @throws Exception
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void createCommissionInfo(String senUserId,String receiverId,Double amount,String giftId,String  merchantQualification) throws Exception{
		User merchantRefereeUser = null ;
		User customerRefereeUser = null;
		User merchant = null;
//		User customer = null;
		//卖家信息
		merchant = UserUtils.get(senUserId);
//		customer = UserUtils.get(receiverId);
		//买家信息
		if(null == merchant){
			logger.error("创建佣金流水失败：卖家信息为空");
			return ;
		}
		//卖家推荐人
		merchantRefereeUser = UserUtils.get(merchant.getMerchantRefereeId());
		customerRefereeUser = UserUtils.get(receiverId);
		//买家推荐人
		if(null == merchantRefereeUser){
			logger.error("创建佣金流水失败：卖家推荐入驻人为空");
			return ;
		}
		if(null == customerRefereeUser){
			logger.error("创建佣金流水失败：买家推荐入驻人为空");
			return ;
		}
		if("1".equals(merchantQualification)){
			//卖家推荐人佣金
			CommissionInfo merchantRefereeCommission = new CommissionInfo();
			//1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣
			merchantRefereeCommission.setType("3");
			merchantRefereeCommission.setUserId(customerRefereeUser.getId());
			merchantRefereeCommission.setProduceUserId(merchant.getId());
			merchantRefereeCommission.setOriginAmount(amount);
			merchantRefereeCommission.setAmount(commissionConfigService.getCommissionAmount("3",amount));
			merchantRefereeCommission.setUnionId(giftId);
			commissionInfoService.save(merchantRefereeCommission);
		}else{
			//新增佣金记录
			//卖家推荐人佣金
			CommissionInfo merchantRefereeCommission = new CommissionInfo();
			//1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣
			merchantRefereeCommission.setType("4");
			merchantRefereeCommission.setUserId(merchantRefereeUser.getId());
			merchantRefereeCommission.setProduceUserId(merchant.getId());
			merchantRefereeCommission.setOriginAmount(amount);
			merchantRefereeCommission.setAmount(commissionConfigService.getCommissionAmount("4",amount));
			merchantRefereeCommission.setUnionId(giftId);
			commissionInfoService.save(merchantRefereeCommission);

			CommissionInfo merchantCommission = new CommissionInfo();
			//1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣
			merchantCommission.setType("5");
			merchantCommission.setUserId(merchant.getId());
			merchantCommission.setProduceUserId(merchant.getId());
			merchantCommission.setOriginAmount(amount);
			merchantCommission.setAmount(commissionConfigService.getCommissionAmount("5",amount));
			merchantCommission.setUnionId(giftId);
			commissionInfoService.save(merchantCommission);
		}
        commissionConfigService.setConfigs(null);
	}


	/**
	 * 消费退款
	 * @param userId 退款人
	 * @param amount 退款金额
	 * @param refundNo 退款单号（订单号）
	 */
	public void createRefund(String userId,Double amount,String refundNo){
		//创建消费流水
		MemberInfo m = new MemberInfo();
		m.setId(userId);
		MemberInfo memberInfo = memberInfoDao.get(m);
		if(null == memberInfo){
			throw new ServiceException("账户不存在");
		}
		AccountFlow accountFlow = new AccountFlow();
		accountFlow.setFlowNo(String.valueOf(idWorker.getId()));
		accountFlow.setId(IdGen.uuid());
		accountFlow.setUserId(userId);
		accountFlow.setAmount(amount);
		accountFlow.setType("1");//收入
		accountFlow.setMode("5");//消费
		accountFlow.setOrderId(refundNo);
		accountFlow.setCheckStatus("2");
		accountFlow.setCreateDate(new Date());
		accountFlowDao.insert(accountFlow);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("balance",memberInfo.getBalance() + amount);
		paramMap.put("commission",null);
		paramMap.put("userId",userId);
		dao.editAccount(paramMap);
	}



}