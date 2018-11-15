package com.mall.modules.account.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.account.dao.AccountInfoDao;
import com.mall.modules.account.entity.AccountInfo;
import com.mall.modules.commission.dao.CommissionInfoDao;
import com.mall.modules.commission.service.CommissionConfigService;
import com.mall.modules.commission.service.CommissionInfoService;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.order.entity.OrderInfo;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 账户明细Service
 * @author hub
 * @version 2018-10-21
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class AccountInfoService extends CrudService<AccountInfoDao, AccountInfo> {

	@Autowired
	private MemberInfoService memberInfoService;


	public AccountInfo get(String id) {
		return super.get(id);
	}

	public List<AccountInfo> findList(AccountInfo accountInfo) {
		return super.findList(accountInfo);
	}

	public Page<AccountInfo> findPage(Page<AccountInfo> page, AccountInfo accountInfo) {
		return super.findPage(page, accountInfo);
	}

	@Transactional(readOnly = false)
	public void save(AccountInfo accountInfo) {
		//修改用户金额
		MemberInfo m = new MemberInfo();
		m.setId(accountInfo.getUserId());
		m = memberInfoService.get(m);
		if(null == m) {
			logger.info("新增交易流水失败：memberInfo信息不存在");
			return;
		}
		// 1：收入 2：支出长
		if("1".equals(accountInfo.getType())){
			m.setBalance(m.getBalance() + accountInfo.getAmount());
		}else{
			m.setBalance(m.getBalance() - accountInfo.getAmount());
		}
		memberInfoService.save(m);
		super.save(accountInfo);
	}

	@Transactional(readOnly = false)
	public void delete(AccountInfo accountInfo) {
		super.delete(accountInfo);
	}


	//佣金详情service
	@Autowired
	private CommissionInfoService commissionInfoService;

	//佣金配置service
	@Autowired
	private CommissionConfigService commissionConfigService;


	/**
	 * 根据订单信息创建账单流水
	 * @param orderInfo
	 */
	@Transactional(readOnly = false)
	public void createAccountFlow(OrderInfo orderInfo) throws Exception{
//		User merchantRefereeUser = null ;
//		User customerRefereeUser = null;
//		User merchant = null;
//
//		if(null == orderInfo){
//			logger.error("创建账单流水失败：订单信息为空");
//			return ;
//		}
////		if(!"2".equals(orderInfo.getOrderStatus())){
////			logger.error("创建账单流水失败：订单状态无效");
////			return ;
////		}
//		//卖家信息
//		merchant = UserUtils.get(orderInfo.getMerchantCode());
//		//买家信息
//		User customer = UserUtils.get(orderInfo.getCustomerCode());
//		if(null == merchant){
//			logger.error("创建账单流水失败：卖家信息为空");
//			return ;
//		}
//		if(null == customer){
//			logger.error("创建账单流水失败：买家信息为空");
//			return ;
//		}
//		//卖家推荐人
//		merchantRefereeUser = UserUtils.get(merchant.getRefereeId());
//		//买家推荐人
//		customerRefereeUser = UserUtils.get(customer.getRefereeId());
//		if(null == merchantRefereeUser){
//			logger.error("创建账单流水失败：卖家推荐人为空");
//			return ;
//		}
//		if(null == customerRefereeUser){
//			logger.error("创建账单流水失败：买家推荐人为空");
//			return ;
//		}
//		if(null == merchantRefereeUser || null == customerRefereeUser
//				|| null == merchant){
//			return;
//		}
//		//订单类型（0-平台自主下单，1-礼包兑换）
//		if("0".equals(orderInfo.getOrderType())){
//			//新增佣金记录
//			//卖家推荐人佣金
//			CommissionInfo merchantRefereeCommission = new CommissionInfo();
//			merchantRefereeCommission.setType("2");
//			merchantRefereeCommission.setUserId(merchantRefereeUser.getId());
//			merchantRefereeCommission.setProduceUserId(merchant.getId());
//			merchantRefereeCommission.setOriginAmount(orderInfo.getGoodsAmountTotal());
//			merchantRefereeCommission.setAmount(commissionConfigService.getCommissionAmount("2",orderInfo.getGoodsAmountTotal()));
//			merchantRefereeCommission.setUnionId(orderInfo.getId());
//			commissionInfoService.save(merchantRefereeCommission);
//
//			//买家推荐人佣金
//			CommissionInfo customerRefereeCommission = new CommissionInfo();
//			customerRefereeCommission.setType("1");
//			customerRefereeCommission.setUserId(merchantRefereeUser.getId());
//			customerRefereeCommission.setProduceUserId(merchant.getId());
//			customerRefereeCommission.setOriginAmount(orderInfo.getGoodsAmountTotal());
//			customerRefereeCommission.setAmount(commissionConfigService.getCommissionAmount("1",orderInfo.getGoodsAmountTotal()));
//			customerRefereeCommission.setUnionId(orderInfo.getId());
//			commissionInfoService.save(customerRefereeCommission);
//
//
//			//新增账单流水记录
//			//卖家账单流水
//			AccountInfo merchantAccountInfo = new AccountInfo();
//			merchantAccountInfo.setUserId(merchant.getId());
//			merchantAccountInfo.setType("1"); //收支类型 1：收入 2：支出
//			merchantAccountInfo.setWay("2");//收支方式（1：佣金收益 2：销售收益） 支出（3：提现 4：结算）
//			merchantAccountInfo.setAmount(orderInfo.getGoodsAmountTotal());
//			merchantAccountInfo.setUnionId(orderInfo.getId());
//			merchantAccountInfo.setStatus("2");//状态 （1：已到账 2：未到账 3：未提现结算 4：已提现结算 ）
//			this.save(merchantAccountInfo);
//			//卖家推荐人账单流水
//			AccountInfo merchantRefereeAccountInfo = new AccountInfo();
//			merchantRefereeAccountInfo.setUserId(merchantRefereeUser.getId());
//			merchantRefereeAccountInfo.setType("1"); //收支类型 1：收入 2：支出
//			merchantRefereeAccountInfo.setWay("1");//收支方式（1：佣金收益 2：销售收益） 支出（3：提现 4：结算）
//			merchantRefereeAccountInfo.setAmount(merchantRefereeCommission.getAmount());//推荐卖家销售返佣
//			merchantRefereeAccountInfo.setUnionId(merchantRefereeCommission.getId());
//			merchantRefereeAccountInfo.setStatus("2");//状态 （1：已到账 2：未到账 3：未提现结算 4：已提现结算 ）
//			this.save(merchantRefereeAccountInfo);
//			//买家推荐人账单流水
//			AccountInfo customerRefereeAccountInfo = new AccountInfo();
//			customerRefereeAccountInfo.setUserId(merchantRefereeUser.getId());
//			customerRefereeAccountInfo.setType("1"); //收支类型 1：收入 2：支出
//			customerRefereeAccountInfo.setWay("1");//收支方式（1：佣金收益 2：销售收益） 支出（3：提现 4：结算）
//			customerRefereeAccountInfo.setAmount(customerRefereeCommission.getAmount());//推荐买家消费返佣
//			customerRefereeAccountInfo.setUnionId(customerRefereeCommission.getId());
//			customerRefereeAccountInfo.setStatus("2");//状态 （1：已到账 2：未到账 3：未提现结算 4：已提现结算 ）
//			this.save(customerRefereeAccountInfo);
//		}
//		//订单类型（0-平台自主下单，1-礼包兑换）
//		if("1".equals(orderInfo.getOrderType())){
//			//新增佣金记录
//			//卖家推荐人佣金
//			CommissionInfo merchantRefereeCommission = new CommissionInfo();
//			//1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣
//			merchantRefereeCommission.setType("4");
//			merchantRefereeCommission.setUserId(merchantRefereeUser.getId());
//			merchantRefereeCommission.setProduceUserId(merchant.getId());
//			merchantRefereeCommission.setOriginAmount(orderInfo.getGoodsAmountTotal());
//			merchantRefereeCommission.setAmount(commissionConfigService.getCommissionAmount("4",orderInfo.getGoodsAmountTotal()));
//			merchantRefereeCommission.setUnionId(orderInfo.getId());
//			commissionInfoService.save(merchantRefereeCommission);
//
//			CommissionInfo merchantCommission = new CommissionInfo();
//			//1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣
//			merchantCommission.setType("5");
//			merchantCommission.setUserId(merchant.getId());
//			merchantCommission.setProduceUserId(merchant.getId());
//			merchantCommission.setOriginAmount(orderInfo.getGoodsAmountTotal());
//			merchantCommission.setAmount(commissionConfigService.getCommissionAmount("5",orderInfo.getGoodsAmountTotal()));
//			merchantCommission.setUnionId(orderInfo.getId());
//			commissionInfoService.save(merchantCommission);
//
//			//新增账单流水记录
//			//卖家账单流水
//			AccountInfo merchantAccountInfo = new AccountInfo();
//			merchantAccountInfo.setUserId(merchant.getId());
//			merchantAccountInfo.setType("1"); //收支类型 1：收入 2：支出
//			merchantAccountInfo.setWay("1");//收支方式（1：佣金收益 2：销售收益） 支出（3：提现 4：结算）
//			merchantAccountInfo.setAmount(merchantCommission.getAmount());
//			merchantAccountInfo.setUnionId(merchantCommission.getId());
//			merchantAccountInfo.setStatus("2");//状态 （1：已到账 2：未到账 3：未提现结算 4：已提现结算 ）
//			this.save(merchantAccountInfo);
//
//			//卖家推荐人账单流水
//			AccountInfo merchantRefereeAccountInfo = new AccountInfo();
//			merchantRefereeAccountInfo.setUserId(merchantRefereeUser.getId());
//			merchantRefereeAccountInfo.setType("1"); //收支类型 1：收入 2：支出
//			merchantRefereeAccountInfo.setWay("1");//收支方式（1：佣金收益 2：销售收益） 支出（3：提现 4：结算）
//			merchantRefereeAccountInfo.setAmount(merchantRefereeCommission.getAmount());//推荐卖家销售返佣
//			merchantRefereeAccountInfo.setUnionId(merchantRefereeCommission.getId());
//			merchantRefereeAccountInfo.setStatus("2");//状态 （1：已到账 2：未到账 3：未提现结算 4：已提现结算 ）
//			this.save(merchantRefereeAccountInfo);
//
//
//		}
//
//
	}



	public Page<AccountInfo> getAccountInfos(AccountInfo accountInfo, Page<AccountInfo> page){
		accountInfo.setPage(page);
		page.setList(findListByApi(accountInfo));
		return page;
	}

	public List<AccountInfo> findListByApi(AccountInfo accountInfo){
		return dao.findListByApi(accountInfo);
	}
	public AccountInfo getByApi(AccountInfo accountInfo){
		return dao.getByApi(accountInfo);
	}

	public Map<String,Object> getStsInfo(AccountInfo accountInfo){
		return dao.getStsInfo(accountInfo);
	}

	@Autowired
	private CommissionInfoDao commissionInfoDao;

	/**
	 * 修改订单状态
	 * @param orderId 订单id
	 */
	@Transactional(readOnly = false)
	public void toAccount(String orderId){
		//更新订单状态
		commissionInfoDao.editOrderStatus(orderId);
		//
	}
}