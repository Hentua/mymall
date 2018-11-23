package com.mall.modules.order.service;

import java.util.List;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyCoupon;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.utils.StringUtils;
import com.mall.modules.order.entity.OrderPaymentWeixinCallback;
import com.mall.modules.order.dao.OrderPaymentWeixinCallbackDao;
import com.mall.modules.order.entity.OrderPaymentWeixinCallbackCoupon;
import com.mall.modules.order.dao.OrderPaymentWeixinCallbackCouponDao;

/**
 * 微信支付回调结果Service
 * @author wankang
 * @version 2018-11-23
 */
@Service
@Transactional(readOnly = true)
public class OrderPaymentWeixinCallbackService extends CrudService<OrderPaymentWeixinCallbackDao, OrderPaymentWeixinCallback> {

	@Autowired
	private OrderPaymentWeixinCallbackCouponDao orderPaymentWeixinCallbackCouponDao;
	
	public OrderPaymentWeixinCallback get(String id) {
		OrderPaymentWeixinCallback orderPaymentWeixinCallback = super.get(id);
		orderPaymentWeixinCallback.setOrderPaymentWeixinCallbackCouponList(orderPaymentWeixinCallbackCouponDao.findList(new OrderPaymentWeixinCallbackCoupon(orderPaymentWeixinCallback)));
		return orderPaymentWeixinCallback;
	}
	
	public List<OrderPaymentWeixinCallback> findList(OrderPaymentWeixinCallback orderPaymentWeixinCallback) {
		return super.findList(orderPaymentWeixinCallback);
	}
	
	public Page<OrderPaymentWeixinCallback> findPage(Page<OrderPaymentWeixinCallback> page, OrderPaymentWeixinCallback orderPaymentWeixinCallback) {
		return super.findPage(page, orderPaymentWeixinCallback);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderPaymentWeixinCallback orderPaymentWeixinCallback) {
		super.save(orderPaymentWeixinCallback);
		for (OrderPaymentWeixinCallbackCoupon orderPaymentWeixinCallbackCoupon : orderPaymentWeixinCallback.getOrderPaymentWeixinCallbackCouponList()){
			if (orderPaymentWeixinCallbackCoupon.getId() == null){
				continue;
			}
			if (OrderPaymentWeixinCallbackCoupon.DEL_FLAG_NORMAL.equals(orderPaymentWeixinCallbackCoupon.getDelFlag())){
				if (StringUtils.isBlank(orderPaymentWeixinCallbackCoupon.getId())){
					orderPaymentWeixinCallbackCoupon.setCallbackId(orderPaymentWeixinCallback);
					orderPaymentWeixinCallbackCoupon.preInsert();
					orderPaymentWeixinCallbackCouponDao.insert(orderPaymentWeixinCallbackCoupon);
				}else{
					orderPaymentWeixinCallbackCoupon.preUpdate();
					orderPaymentWeixinCallbackCouponDao.update(orderPaymentWeixinCallbackCoupon);
				}
			}else{
				orderPaymentWeixinCallbackCouponDao.delete(orderPaymentWeixinCallbackCoupon);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderPaymentWeixinCallback orderPaymentWeixinCallback) {
		super.delete(orderPaymentWeixinCallback);
		orderPaymentWeixinCallbackCouponDao.delete(new OrderPaymentWeixinCallbackCoupon(orderPaymentWeixinCallback));
	}

	public void save(WxPayOrderNotifyResult result) {
		OrderPaymentWeixinCallback orderPaymentWeixinCallback = new OrderPaymentWeixinCallback();
		orderPaymentWeixinCallback.setReturnCode(result.getReturnCode());
		orderPaymentWeixinCallback.setReturnMsg(result.getReturnMsg());
		orderPaymentWeixinCallback.setResultCode(result.getResultCode());
		orderPaymentWeixinCallback.setAppid(result.getAppid());
		orderPaymentWeixinCallback.setMchId(result.getMchId());
		orderPaymentWeixinCallback.setDeviceInfo(result.getDeviceInfo());
		orderPaymentWeixinCallback.setNonceStr(result.getNonceStr());
		orderPaymentWeixinCallback.setSign(result.getSign());
		orderPaymentWeixinCallback.setErrCode(result.getErrCode());
		orderPaymentWeixinCallback.setErrCodeDes(result.getErrCodeDes());
		orderPaymentWeixinCallback.setOpenid(result.getOpenid());
		orderPaymentWeixinCallback.setIsSubscribe(result.getIsSubscribe());
		orderPaymentWeixinCallback.setTradeType(result.getTradeType());
		orderPaymentWeixinCallback.setBankType(result.getBankType());
		orderPaymentWeixinCallback.setTotalFee(result.getTotalFee());
		orderPaymentWeixinCallback.setFeeType(result.getFeeType());
		orderPaymentWeixinCallback.setCashFee(result.getCashFee());
		orderPaymentWeixinCallback.setCashFeeType(result.getCashFeeType());
		orderPaymentWeixinCallback.setCouponCount(result.getCouponCount());
		orderPaymentWeixinCallback.setCouponFee(result.getCouponFee());
		orderPaymentWeixinCallback.setTransactionId(result.getTransactionId());
		orderPaymentWeixinCallback.setOutTradeNo(result.getOutTradeNo());
		orderPaymentWeixinCallback.setAttach(result.getAttach());
		orderPaymentWeixinCallback.setTimeEnd(result.getTimeEnd());
		orderPaymentWeixinCallback.setPromotionDetail(result.getPromotionDetail());
		orderPaymentWeixinCallback.setVersion(result.getVersion());
		orderPaymentWeixinCallback.setSettlementTotalFee(result.getSettlementTotalFee());
		List<WxPayOrderNotifyCoupon> couponList = result.getCouponList();
		List<OrderPaymentWeixinCallbackCoupon> orderPaymentWeixinCallbackCoupons = Lists.newArrayList();
		for (WxPayOrderNotifyCoupon c : couponList) {
			OrderPaymentWeixinCallbackCoupon orderPaymentWeixinCallbackCoupon = new OrderPaymentWeixinCallbackCoupon();
			orderPaymentWeixinCallbackCoupon.setId("");
			orderPaymentWeixinCallbackCoupon.setCouponId(c.getCouponId());
			orderPaymentWeixinCallbackCoupon.setCouponFee(c.getCouponFee());
			orderPaymentWeixinCallbackCoupon.setCouponType(c.getCouponType());
			orderPaymentWeixinCallbackCoupons.add(orderPaymentWeixinCallbackCoupon);
		}
		orderPaymentWeixinCallback.setOrderPaymentWeixinCallbackCouponList(orderPaymentWeixinCallbackCoupons);
		this.save(orderPaymentWeixinCallback);
	}
	
}