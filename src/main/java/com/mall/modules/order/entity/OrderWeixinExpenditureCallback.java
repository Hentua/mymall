package com.mall.modules.order.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 微信提现返回结果Entity
 * @author wankang
 * @version 2018-11-27
 */
public class OrderWeixinExpenditureCallback extends DataEntity<OrderWeixinExpenditureCallback> {
	
	private static final long serialVersionUID = 1L;
	private String returnCode;		// return_code
	private String returnMsg;		// return_msg
	private String mchAppid;		// mch_appid
	private String mchid;		// mchid
	private String deviceInfo;		// device_info
	private String nonceStr;		// nonce_str
	private String resultCode;		// result_code
	private String errCode;		// err_code
	private String errCodeDes;		// err_code_des
	private String partnerTradeNo;		// partner_trade_no
	private String paymentNo;		// payment_no
	private String paymentTime;		// payment_time
	
	public OrderWeixinExpenditureCallback() {
		super();
	}

	public OrderWeixinExpenditureCallback(String id){
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
	
	@Length(min=0, max=128, message="mch_appid长度必须介于 0 和 128 之间")
	public String getMchAppid() {
		return mchAppid;
	}

	public void setMchAppid(String mchAppid) {
		this.mchAppid = mchAppid;
	}
	
	@Length(min=0, max=32, message="mchid长度必须介于 0 和 32 之间")
	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
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
	
	@Length(min=0, max=16, message="result_code长度必须介于 0 和 16 之间")
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	@Length(min=0, max=16, message="err_code长度必须介于 0 和 16 之间")
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
	
	@Length(min=0, max=32, message="partner_trade_no长度必须介于 0 和 32 之间")
	public String getPartnerTradeNo() {
		return partnerTradeNo;
	}

	public void setPartnerTradeNo(String partnerTradeNo) {
		this.partnerTradeNo = partnerTradeNo;
	}
	
	@Length(min=0, max=64, message="payment_no长度必须介于 0 和 64 之间")
	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	
	@Length(min=0, max=32, message="payment_time长度必须介于 0 和 32 之间")
	public String getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	
}