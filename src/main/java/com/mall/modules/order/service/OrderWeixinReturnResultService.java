package com.mall.modules.order.service;

import java.util.List;

import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.order.entity.OrderWeixinReturnResult;
import com.mall.modules.order.dao.OrderWeixinReturnResultDao;

/**
 * 微信支付退款结果Service
 * @author wankang
 * @version 2018-11-25
 */
@Service
@Transactional(readOnly = true)
public class OrderWeixinReturnResultService extends CrudService<OrderWeixinReturnResultDao, OrderWeixinReturnResult> {

	public OrderWeixinReturnResult get(String id) {
		return super.get(id);
	}
	
	public List<OrderWeixinReturnResult> findList(OrderWeixinReturnResult orderWeixinReturnResult) {
		return super.findList(orderWeixinReturnResult);
	}
	
	public Page<OrderWeixinReturnResult> findPage(Page<OrderWeixinReturnResult> page, OrderWeixinReturnResult orderWeixinReturnResult) {
		return super.findPage(page, orderWeixinReturnResult);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderWeixinReturnResult orderWeixinReturnResult) {
		super.save(orderWeixinReturnResult);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderWeixinReturnResult orderWeixinReturnResult) {
		super.delete(orderWeixinReturnResult);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void save(WxPayRefundResult wxPayRefundResult) {
		OrderWeixinReturnResult orderWeixinReturnResult = new OrderWeixinReturnResult();
		orderWeixinReturnResult.setReturnCode(wxPayRefundResult.getReturnCode());
		orderWeixinReturnResult.setReturnMsg(wxPayRefundResult.getReturnMsg());
		orderWeixinReturnResult.setResultCode(wxPayRefundResult.getResultCode());
		orderWeixinReturnResult.setErrCode(wxPayRefundResult.getErrCode());
		orderWeixinReturnResult.setErrCodeDes(wxPayRefundResult.getErrCodeDes());
		orderWeixinReturnResult.setAppid(wxPayRefundResult.getAppid());
		orderWeixinReturnResult.setMchId(wxPayRefundResult.getMchId());
		orderWeixinReturnResult.setNonceStr(wxPayRefundResult.getNonceStr());
		orderWeixinReturnResult.setSign(wxPayRefundResult.getSign());
		orderWeixinReturnResult.setTransactionId(wxPayRefundResult.getTransactionId());
		orderWeixinReturnResult.setOutTradeNo(wxPayRefundResult.getOutTradeNo());
		orderWeixinReturnResult.setOutRefundNo(wxPayRefundResult.getOutRefundNo());
		orderWeixinReturnResult.setRefundId(wxPayRefundResult.getRefundId());
		orderWeixinReturnResult.setRefundFee(wxPayRefundResult.getRefundFee());
		orderWeixinReturnResult.setSettlementTotalFee(wxPayRefundResult.getSettlementTotalFee());
		orderWeixinReturnResult.setSettlementRefundFee(wxPayRefundResult.getSettlementRefundFee());
		orderWeixinReturnResult.setTotalFee(wxPayRefundResult.getTotalFee());
		orderWeixinReturnResult.setFeeType(wxPayRefundResult.getFeeType());
		orderWeixinReturnResult.setCashFee(wxPayRefundResult.getCashFee());
		orderWeixinReturnResult.setCashFeeType(wxPayRefundResult.getCashFeeType());
		orderWeixinReturnResult.setCashRefundFee(wxPayRefundResult.getRefundFee());
		orderWeixinReturnResult.setCouponRefundFee(wxPayRefundResult.getCouponRefundFee());
		orderWeixinReturnResult.setCouponRefundCount(wxPayRefundResult.getCouponRefundCount());
		this.save(orderWeixinReturnResult);
	}
	
}