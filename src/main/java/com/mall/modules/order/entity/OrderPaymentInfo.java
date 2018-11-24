package com.mall.modules.order.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 支付信息Entity
 * @author wankang
 * @version 2018-10-19
 */
public class OrderPaymentInfo extends DataEntity<OrderPaymentInfo> {
	
	private static final long serialVersionUID = 1L;
	private String paymentNo;		// 支付单号
	private String payChannel;		// 支付渠道（0-微信支付，1-礼包兑换）
	private Double amountTotal;		// 总金额
	private String deviceInfo;		// 设备号
	private String returnCode;		// 结果状态码
	private String returnMsg;		// 结果状态信息
	private String resultCode;		// 业务结果
	private String errCode;		// 错误代码
	private String errCodeDes;		// 错误代码描述
	private String tradeType;		// 交易类型
	private String prepayId;		// 预支付交易会话标识
	private String codeUrl;		// 二维码链接
	private String paymentStatus; // 付款状态 0-未付款 1-付款成功 2-付款失败
	private String paymentType; // 支付来源类型 0-订单 1-礼包购买 2-充值
	private Double discountAmount; // 优惠扣减金额
	
	public OrderPaymentInfo() {
		super();
	}

	public OrderPaymentInfo(String id){
		super(id);
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Length(min=1, max=32, message="支付单号长度必须介于 1 和 32 之间")
	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	
	@Length(min=1, max=2, message="支付渠道（0-微信支付，1-礼包兑换）长度必须介于 1 和 2 之间")
	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	
	public Double getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(Double amountTotal) {
		this.amountTotal = amountTotal;
	}
	
	@Length(min=0, max=32, message="设备号长度必须介于 0 和 32 之间")
	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	
	@Length(min=0, max=16, message="结果状态码长度必须介于 0 和 16 之间")
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	
	@Length(min=0, max=128, message="结果状态信息长度必须介于 0 和 128 之间")
	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	
	@Length(min=0, max=16, message="业务结果长度必须介于 0 和 16 之间")
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	@Length(min=0, max=32, message="错误代码长度必须介于 0 和 32 之间")
	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	
	@Length(min=0, max=128, message="错误代码描述长度必须介于 0 和 128 之间")
	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}
	
	@Length(min=0, max=16, message="交易类型长度必须介于 0 和 16 之间")
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	@Length(min=0, max=64, message="预支付交易会话标识长度必须介于 0 和 64 之间")
	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	
	@Length(min=0, max=64, message="二维码链接长度必须介于 0 和 64 之间")
	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	
}