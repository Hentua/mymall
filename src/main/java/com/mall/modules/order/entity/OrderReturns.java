package com.mall.modules.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mall.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 订单售后申请Entity
 * @author wankang
 * @version 2018-11-15
 */
public class OrderReturns extends DataEntity<OrderReturns> {
	
	private static final long serialVersionUID = 1L;
	private String returnsNo;		// 退款订单号
	private String orderNo;		// 原订单号
	private String expressNo;		// 快递单号
	private String consigneeRealname;		// 收货人姓名
	private String consigneeTelphone;		// 联系电话
	private String consigneeAddress;		// 收货地址
	private String consigneeZip;		// 邮政编码
	private String logisticsType;		// 物流方式
	private String returnsType;		// 退货类型
	private String handlingWay;		// 处理方式
	private Double returnsAmount;		// 退款金额
	private Date returnSubmitTime;		// 售后申请时间
	private Date handlingTime;		// 处理时间
	private String status;		// 售后状态
	private String reason;		// 售后原因
	private String reply;		// 运营回复
	private String customerCode;		// 申请人
	private String orderId;		// 原订单ID
	private Date checkTime;		// 审核时间
	private String platformReply;		// 运营回复
	private String merchantServicePhone;		// 商户联系方式

	private String customerName;
	private String customerMobile;
	private String merchantCode;
	private String merchantName;
	private String merchantAccount;

	private Date completeDate; // 完成时间

	private OrderInfo orderInfo;

	public OrderReturns() {
		super();
	}

	public OrderReturns(String id){
		super(id);
	}

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

    public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public String getMerchantServicePhone() {
		return merchantServicePhone;
	}

	public void setMerchantServicePhone(String merchantServicePhone) {
		this.merchantServicePhone = merchantServicePhone;
	}

	public String getPlatformReply() {
		return platformReply;
	}

	public void setPlatformReply(String platformReply) {
		this.platformReply = platformReply;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Length(min=1, max=32, message="退款订单号长度必须介于 1 和 32 之间")
	public String getReturnsNo() {
		return returnsNo;
	}

	public void setReturnsNo(String returnsNo) {
		this.returnsNo = returnsNo;
	}
	
	@Length(min=1, max=32, message="原订单号长度必须介于 1 和 32 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=0, max=100, message="快递单号长度必须介于 0 和 100 之间")
	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	
	@Length(min=0, max=100, message="收货人姓名长度必须介于 0 和 100 之间")
	public String getConsigneeRealname() {
		return consigneeRealname;
	}

	public void setConsigneeRealname(String consigneeRealname) {
		this.consigneeRealname = consigneeRealname;
	}
	
	@Length(min=0, max=20, message="联系电话长度必须介于 0 和 20 之间")
	public String getConsigneeTelphone() {
		return consigneeTelphone;
	}

	public void setConsigneeTelphone(String consigneeTelphone) {
		this.consigneeTelphone = consigneeTelphone;
	}
	
	@Length(min=0, max=200, message="收货地址长度必须介于 0 和 200 之间")
	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	
	@Length(min=0, max=10, message="邮政编码长度必须介于 0 和 10 之间")
	public String getConsigneeZip() {
		return consigneeZip;
	}

	public void setConsigneeZip(String consigneeZip) {
		this.consigneeZip = consigneeZip;
	}
	
	@Length(min=0, max=10, message="物流方式长度必须介于 0 和 10 之间")
	public String getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}
	
	@Length(min=1, max=1, message="退货类型长度必须介于 1 和 1 之间")
	public String getReturnsType() {
		return returnsType;
	}

	public void setReturnsType(String returnsType) {
		this.returnsType = returnsType;
	}
	
	@Length(min=0, max=2, message="处理方式长度必须介于 0 和 2 之间")
	public String getHandlingWay() {
		return handlingWay;
	}

	public void setHandlingWay(String handlingWay) {
		this.handlingWay = handlingWay;
	}
	
	public Double getReturnsAmount() {
		return returnsAmount;
	}

	public void setReturnsAmount(Double returnsAmount) {
		this.returnsAmount = returnsAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="售后申请时间不能为空")
	public Date getReturnSubmitTime() {
		return returnSubmitTime;
	}

	public void setReturnSubmitTime(Date returnSubmitTime) {
		this.returnSubmitTime = returnSubmitTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHandlingTime() {
		return handlingTime;
	}

	public void setHandlingTime(Date handlingTime) {
		this.handlingTime = handlingTime;
	}
	
	@Length(min=0, max=1, message="售后状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=500, message="售后原因长度必须介于 0 和 500 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Length(min=0, max=500, message="运营回复长度必须介于 0 和 500 之间")
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
	
	@Length(min=1, max=64, message="申请人长度必须介于 1 和 64 之间")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
}