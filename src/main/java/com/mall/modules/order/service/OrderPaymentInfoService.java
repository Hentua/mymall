package com.mall.modules.order.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.account.entity.AccountFlow;
import com.mall.modules.account.service.AccountFlowService;
import com.mall.modules.account.service.AccountService;
import com.mall.modules.gift.entity.GiftMerchant;
import com.mall.modules.gift.entity.GiftPurchaseLog;
import com.mall.modules.gift.service.GiftMerchantService;
import com.mall.modules.gift.service.GiftPurchaseLogService;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.order.dao.OrderInfoDao;
import com.mall.modules.order.dao.OrderPaymentInfoDao;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.order.entity.OrderPaymentInfo;
import com.sohu.idcenter.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 支付信息Service
 * @author wankang
 * @version 2018-10-19
 */
@Service
@Transactional(readOnly = true)
public class OrderPaymentInfoService extends CrudService<OrderPaymentInfoDao, OrderPaymentInfo> {

	private static IdWorker idWorker = new IdWorker();
	@Autowired
	private OrderPaymentInfoDao orderPaymentInfoDao;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private AccountFlowService accountFlowService;
	@Autowired
	private GiftPurchaseLogService giftPurchaseLogService;
	@Autowired
	private GiftMerchantService giftMerchantService;

	@Autowired
	private OrderInfoDao orderInfoDao;

	public OrderPaymentInfo get(String id) {
		return super.get(id);
	}

	public OrderPaymentInfo getByCondition(OrderPaymentInfo orderPaymentInfo) {
		return orderPaymentInfoDao.getByCondition(orderPaymentInfo);
	}
	
	public List<OrderPaymentInfo> findList(OrderPaymentInfo orderPaymentInfo) {
		return super.findList(orderPaymentInfo);
	}
	
	public Page<OrderPaymentInfo> findPage(Page<OrderPaymentInfo> page, OrderPaymentInfo orderPaymentInfo) {
		return super.findPage(page, orderPaymentInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderPaymentInfo orderPaymentInfo) {
		super.save(orderPaymentInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderPaymentInfo orderPaymentInfo) {
		super.delete(orderPaymentInfo);
	}

	public static OrderPaymentInfo genDefaultPaymentInfo(String orderType) {
		String paymentNo = String.valueOf(idWorker.getId());
		OrderPaymentInfo orderPaymentInfo = new OrderPaymentInfo();
		orderPaymentInfo.setPaymentNo(paymentNo);
		if("1".equals(orderType)) {
			orderPaymentInfo.setPaymentStatus("1");
		}else {
			orderPaymentInfo.setPaymentStatus("0");
		}
		orderPaymentInfo.setPaymentType(orderType);
		return orderPaymentInfo;
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public OrderPaymentInfo genAmountPaymentInfo(String payChannel, String paymentType, Double amountTotal, Double discountAmount) {
		OrderPaymentInfo orderPaymentInfo = genDefaultPaymentInfo(payChannel);
		orderPaymentInfo.setAmountTotal(amountTotal);
		orderPaymentInfo.setDiscountAmount(discountAmount);
		orderPaymentInfo.setPaymentType(paymentType);
		this.save(orderPaymentInfo);
		return orderPaymentInfo;
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void modifyPaymentInfoStatus(OrderPaymentInfo orderPaymentInfo) {
		orderPaymentInfoDao.modifyPaymentInfoStatus(orderPaymentInfo);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void normalOrderPaySuccess(OrderPaymentInfo orderPaymentInfo, Date payChannelTime) {
		String paymentNo = orderPaymentInfo.getPaymentNo();

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setPaymentNo(paymentNo);
		orderInfo.setPayChannelTime(payChannelTime);
		// 修改订单状态
		orderInfoService.paySuccessModifyOrderStatus(orderInfo);

		// 修改支付单状态
		orderPaymentInfo.setPaymentStatus("1");
		this.modifyPaymentInfoStatus(orderPaymentInfo);

		//产生佣金

		List<OrderInfo> orderInfos =orderInfoDao.findOrderNos(orderInfo);
		for (OrderInfo o: orderInfos) {
			//订单完成生成佣金信息 根据订单信息创建账单流水
			try{
				accountService.createAccountFlow(o);
			}catch (Exception e){
				e.printStackTrace();
				logger.error("创建佣金失败["+orderInfo.getPaymentNo()+"]");
			}
		}
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void weixinPayResult(OrderPaymentInfo orderPaymentInfo) {
		orderPaymentInfoDao.weixinPayResult(orderPaymentInfo);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void rechargeOrderPaySuccess(OrderPaymentInfo orderPaymentInfo) {
		String paymentNo = orderPaymentInfo.getPaymentNo();

		// 修改支付流水状态
		AccountFlow accountFlow = accountFlowService.getByFlowNo(paymentNo);
		accountFlow.setCheckStatus("2");
		accountFlowService.save(accountFlow);
		// 新增余额
		MemberInfo queryCondition = new MemberInfo();
		queryCondition.setId(accountFlow.getUserId());
		MemberInfo memberInfo = memberInfoService.get(queryCondition);
		Double balance = memberInfo.getBalance() + orderPaymentInfo.getAmountTotal();
		accountService.editAccount(balance, null, memberInfo.getId());
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void giftOrderPaySuccess(OrderPaymentInfo orderPaymentInfo) {
		String paymentNo = orderPaymentInfo.getPaymentNo();
		GiftPurchaseLog queryCondition = new GiftPurchaseLog();
		queryCondition.setPaymentNo(paymentNo);
		List<GiftPurchaseLog> giftPurchaseLogs = giftPurchaseLogService.findList(queryCondition);
		for (GiftPurchaseLog g : giftPurchaseLogs) {
			g.setStatus("1");
			g.setPayTime(new Date());
			GiftMerchant giftMerchant = giftPurchaseLogService.genGiftMerchant(g);
			giftMerchantService.save(giftMerchant);
			g.setGiftMerchantCode(giftMerchant.getId());
			giftPurchaseLogService.save(g);
		}
	}

}