package com.mall.modules.order.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.mall.common.persistence.DataEntity;

/**
 * 微信支付回调结果Entity
 * @author wankang
 * @version 2018-11-23
 */
public class OrderPaymentWeixinCallback extends DataEntity<OrderPaymentWeixinCallback> {
	
	private static final long serialVersionUID = 1L;
	private String returnCode;		// return_code
	private String returnMsg;		// return_msg
	private String appid;		// appid
	private String mchId;		// mch_id
	private String deviceInfo;		// device_info
	private String nonceStr;		// nonce_str
	private String sign;		// sign
	private String resultCode;		// result_code
	private String errCode;		// err_code
	private String errCodeDes;		// err_code_des
	private String openid;		// openid
	private String isSubscribe;		// is_subscribe
	private String tradeType;		// trade_type
	private String bankType;		// bank_type
	private Integer totalFee;		// total_fee
	private String feeType;		// fee_type
	private Integer cashFee;		// cash_fee
	private String cashFeeType;		// cash_fee_type
	private Integer couponFee;		// coupon_fee
	private Integer couponCount;		// coupon_count
	private String transactionId;		// transaction_id
	private String outTradeNo;		// out_trade_no
	private String attach;		// attach
	private String timeEnd;		// time_end
	private String promotionDetail;		// promotion_detail
	private String version;		// version
	private Integer settlementTotalFee;		// settlement_total_fee
	private List<OrderPaymentWeixinCallbackCoupon> orderPaymentWeixinCallbackCouponList = Lists.newArrayList();		// 子表列表
	
	public OrderPaymentWeixinCallback() {
		super();
	}

	public OrderPaymentWeixinCallback(String id){
		super(id);
	}

	@Length(min=1, max=16, message="return_code长度必须介于 1 和 16 之间")
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	
	@Length(min=1, max=128, message="return_msg长度必须介于 1 和 128 之间")
	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	
	@Length(min=0, max=32, message="appid长度必须介于 0 和 32 之间")
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	@Length(min=0, max=32, message="mch_id长度必须介于 0 和 32 之间")
	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	
	@Length(min=0, max=32, message="device_info长度必须介于 0 和 32 之间")
	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	
	@Length(min=0, max=32, message="nonce_str长度必须介于 0 和 32 之间")
	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	
	@Length(min=0, max=32, message="sign长度必须介于 0 和 32 之间")
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	@Length(min=0, max=16, message="result_code长度必须介于 0 和 16 之间")
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	@Length(min=0, max=32, message="err_code长度必须介于 0 和 32 之间")
	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	
	@Length(min=0, max=128, message="err_code_des长度必须介于 0 和 128 之间")
	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}
	
	@Length(min=0, max=128, message="openid长度必须介于 0 和 128 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Length(min=0, max=1, message="is_subscribe长度必须介于 0 和 1 之间")
	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
	
	@Length(min=0, max=16, message="trade_type长度必须介于 0 和 16 之间")
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	@Length(min=0, max=16, message="bank_type长度必须介于 0 和 16 之间")
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	
	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	
	@Length(min=0, max=8, message="fee_type长度必须介于 0 和 8 之间")
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	public Integer getCashFee() {
		return cashFee;
	}

	public void setCashFee(Integer cashFee) {
		this.cashFee = cashFee;
	}
	
	@Length(min=0, max=8, message="cash_fee_type长度必须介于 0 和 8 之间")
	public String getCashFeeType() {
		return cashFeeType;
	}

	public void setCashFeeType(String cashFeeType) {
		this.cashFeeType = cashFeeType;
	}
	
	public Integer getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}
	
	public Integer getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}
	
	@Length(min=0, max=32, message="transaction_id长度必须介于 0 和 32 之间")
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	@Length(min=0, max=32, message="out_trade_no长度必须介于 0 和 32 之间")
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	
	@Length(min=0, max=128, message="attach长度必须介于 0 和 128 之间")
	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	@Length(min=0, max=14, message="time_end长度必须介于 0 和 14 之间")
	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	
	@Length(min=0, max=256, message="promotion_detail长度必须介于 0 和 256 之间")
	public String getPromotionDetail() {
		return promotionDetail;
	}

	public void setPromotionDetail(String promotionDetail) {
		this.promotionDetail = promotionDetail;
	}
	
	@Length(min=0, max=32, message="version长度必须介于 0 和 32 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public Integer getSettlementTotalFee() {
		return settlementTotalFee;
	}

	public void setSettlementTotalFee(Integer settlementTotalFee) {
		this.settlementTotalFee = settlementTotalFee;
	}
	
	public List<OrderPaymentWeixinCallbackCoupon> getOrderPaymentWeixinCallbackCouponList() {
		return orderPaymentWeixinCallbackCouponList;
	}

	public void setOrderPaymentWeixinCallbackCouponList(List<OrderPaymentWeixinCallbackCoupon> orderPaymentWeixinCallbackCouponList) {
		this.orderPaymentWeixinCallbackCouponList = orderPaymentWeixinCallbackCouponList;
	}
}