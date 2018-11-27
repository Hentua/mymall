package com.mall.modules.order.service;

import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.order.dao.OrderWeixinExpenditureCallbackDao;
import com.mall.modules.order.entity.OrderWeixinExpenditureCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 微信提现返回结果Service
 * @author wankang
 * @version 2018-11-27
 */
@Service
@Transactional(readOnly = true)
public class OrderWeixinExpenditureCallbackService extends CrudService<OrderWeixinExpenditureCallbackDao, OrderWeixinExpenditureCallback> {

	public OrderWeixinExpenditureCallback get(String id) {
		return super.get(id);
	}
	
	public List<OrderWeixinExpenditureCallback> findList(OrderWeixinExpenditureCallback orderWeixinExpenditureCallback) {
		return super.findList(orderWeixinExpenditureCallback);
	}
	
	public Page<OrderWeixinExpenditureCallback> findPage(Page<OrderWeixinExpenditureCallback> page, OrderWeixinExpenditureCallback orderWeixinExpenditureCallback) {
		return super.findPage(page, orderWeixinExpenditureCallback);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderWeixinExpenditureCallback orderWeixinExpenditureCallback) {
		super.save(orderWeixinExpenditureCallback);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderWeixinExpenditureCallback orderWeixinExpenditureCallback) {
		super.delete(orderWeixinExpenditureCallback);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void save(EntPayResult entPayResult) {
		OrderWeixinExpenditureCallback orderWeixinExpenditureCallback = new OrderWeixinExpenditureCallback();
		orderWeixinExpenditureCallback.setReturnCode(entPayResult.getReturnCode());
		orderWeixinExpenditureCallback.setReturnMsg(entPayResult.getReturnMsg());
		orderWeixinExpenditureCallback.setMchAppid(entPayResult.getMchAppid());
		orderWeixinExpenditureCallback.setMchid(entPayResult.getMchId());
		orderWeixinExpenditureCallback.setDeviceInfo(entPayResult.getDeviceInfo());
		orderWeixinExpenditureCallback.setNonceStr(entPayResult.getNonceStr());
		orderWeixinExpenditureCallback.setResultCode(entPayResult.getResultCode());
		orderWeixinExpenditureCallback.setErrCode(entPayResult.getErrCode());
		orderWeixinExpenditureCallback.setErrCodeDes(entPayResult.getErrCodeDes());
		orderWeixinExpenditureCallback.setPartnerTradeNo(entPayResult.getPartnerTradeNo());
		orderWeixinExpenditureCallback.setPaymentNo(entPayResult.getPaymentNo());
		orderWeixinExpenditureCallback.setPaymentTime(entPayResult.getPaymentTime());
		this.save(orderWeixinExpenditureCallback);
	}
	
}