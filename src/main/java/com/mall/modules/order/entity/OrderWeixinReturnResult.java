package com.mall.modules.order.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 微信支付退款结果Entity
 * @author wankang
 * @version 2018-11-25
 */
public class OrderWeixinReturnResult extends DataEntity<OrderWeixinReturnResult> {
	
	private static final long serialVersionUID = 1L;
	private String returnCode;		// return_code
	private String returnMsg;		// return_msg
	private String resultCode;		// result_code
	private String errCode;		// err_code
	private String errCodeDes;		// err_code_des
	private String appid;		// appid
	private String mchId;		// mch_id
	private String nonceStr;		// nonce_str
	private String sign;		// sign
	private String transactionId;		// transaction_id
	private String outTradeNo;		// out_trade_no
	private String outRefundNo;		// out_refund_no
	private String refundId;		// refund_id
	private Integer refundFee;		// refund_fee
	private Integer settlementRefundFee;		// settlement_refund_fee
	private Integer totalFee;		// total_fee
	private String feeType;		// fee_type
	private Integer settlementTotalFee;		// settlement_total_fee
	private Integer cashFee;		// cash_fee
	private String cashFeeType;		// cash_fee_type
	private Integer cashRefundFee;		// cash_refund_fee
	private Integer couponRefundFee;		// coupon_refund_fee
	private Integer couponRefundCount;		// coupon_refund_count
	
	public OrderWeixinReturnResult() {
		super();
	}

	public OrderWeixinReturnResult(String id){
		super(id);
	}

	@Length(min=1, max=16, message="return_code长度必须介于 1 和 16 之间")
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	
	@Length(min=0, max=128, message="return_msg长度必须介于 0 和 128 之间")
	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
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
	
	@Length(min=0, max=32, message="out_refund_no长度必须介于 0 和 32 之间")
	public String getOutRefundNo() {
		return outRefundNo;
	}

	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}
	
	@Length(min=0, max=32, message="refund_id长度必须介于 0 和 32 之间")
	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	
	public Integer getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Integer refundFee) {
		this.refundFee = refundFee;
	}
	
	public Integer getSettlementRefundFee() {
		return settlementRefundFee;
	}

	public void setSettlementRefundFee(Integer settlementRefundFee) {
		this.settlementRefundFee = settlementRefundFee;
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
	
	public Integer getSettlementTotalFee() {
		return settlementTotalFee;
	}

	public void setSettlementTotalFee(Integer settlementTotalFee) {
		this.settlementTotalFee = settlementTotalFee;
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
	
	public Integer getCashRefundFee() {
		return cashRefundFee;
	}

	public void setCashRefundFee(Integer cashRefundFee) {
		this.cashRefundFee = cashRefundFee;
	}
	
	public Integer getCouponRefundFee() {
		return couponRefundFee;
	}

	public void setCouponRefundFee(Integer couponRefundFee) {
		this.couponRefundFee = couponRefundFee;
	}
	
	public Integer getCouponRefundCount() {
		return couponRefundCount;
	}

	public void setCouponRefundCount(Integer couponRefundCount) {
		this.couponRefundCount = couponRefundCount;
	}
	
}