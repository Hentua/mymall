package com.mall.modules.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mall.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 订单信息Entity
 * @author wankang
 * @version 2018-10-12
 */
public class OrderLogistics extends DataEntity<OrderLogistics> {
	
	private static final long serialVersionUID = 1L;
	private String orderNo;		// 订单号 父类
	private String expressNo;		// 物流编号
	private String consigneeRealname;		// 收货人姓名
	private String consigneeTelphone;		// 收货人联系电话
	private String consigneeTelphoneBackup;		// 备用联系电话
	private String consigneeAddress;		// 收货地址
	private String consigneeZip;		// 邮政编码
	private String logisticsType;		// 物流类型
	private String logisticsId;		// 物流商家编号
	private Double logisticsFee;		// 物流费
	private Double agencyFee;		// 快递公司代收货款费率，如货值的2%-5%，一般月结
	private Double deliveryAmount;		// 实际物流成本
	private String orderlogisticsStatus;		// 物流状态
	private String logisticsSettlementStatus;		// 物流结算状态（0-未结算，1-已结算，2-部分结算）
	private String logisticsResultLast;		// 物流最后状态描述
	private String logisticsResult;		// 物流描述
	private Date logisticsCreateTime;		// 发货时间
	private Date logisticsUpdateTime;		// 物流更新时间
	private Date logisticsSettlementTime;		// 物流结算时间
	private String logisticsPayChannel;		// 物流支付渠道
	private String logisticsPayNo;		// 物流支付单号
	private String reconciliationStatus;		// 物流公司对账状态（0-未对账，1-已对账）
	private Date reconciliationTime;		// 物流公司对账时间
	
	public OrderLogistics() {
		super();
	}

	public OrderLogistics(String orderNo){
		this.orderNo = orderNo;
	}

	@Length(min=1, max=32, message="订单号长度必须介于 1 和 32 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=0, max=100, message="物流编号长度必须介于 0 和 100 之间")
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
	
	@Length(min=0, max=20, message="收货人联系电话长度必须介于 0 和 20 之间")
	public String getConsigneeTelphone() {
		return consigneeTelphone;
	}

	public void setConsigneeTelphone(String consigneeTelphone) {
		this.consigneeTelphone = consigneeTelphone;
	}
	
	@Length(min=0, max=20, message="备用联系电话长度必须介于 0 和 20 之间")
	public String getConsigneeTelphoneBackup() {
		return consigneeTelphoneBackup;
	}

	public void setConsigneeTelphoneBackup(String consigneeTelphoneBackup) {
		this.consigneeTelphoneBackup = consigneeTelphoneBackup;
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
	
	@Length(min=0, max=20, message="物流类型长度必须介于 0 和 20 之间")
	public String getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}
	
	@Length(min=0, max=100, message="物流商家编号长度必须介于 0 和 100 之间")
	public String getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(String logisticsId) {
		this.logisticsId = logisticsId;
	}
	
	public Double getLogisticsFee() {
		return logisticsFee;
	}

	public void setLogisticsFee(Double logisticsFee) {
		this.logisticsFee = logisticsFee;
	}
	
	public Double getAgencyFee() {
		return agencyFee;
	}

	public void setAgencyFee(Double agencyFee) {
		this.agencyFee = agencyFee;
	}
	
	public Double getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(Double deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}
	
	@Length(min=0, max=10, message="物流状态长度必须介于 0 和 10 之间")
	public String getOrderlogisticsStatus() {
		return orderlogisticsStatus;
	}

	public void setOrderlogisticsStatus(String orderlogisticsStatus) {
		this.orderlogisticsStatus = orderlogisticsStatus;
	}
	
	@Length(min=0, max=1, message="物流结算状态（0-未结算，1-已结算，2-部分结算）长度必须介于 0 和 1 之间")
	public String getLogisticsSettlementStatus() {
		return logisticsSettlementStatus;
	}

	public void setLogisticsSettlementStatus(String logisticsSettlementStatus) {
		this.logisticsSettlementStatus = logisticsSettlementStatus;
	}
	
	@Length(min=0, max=100, message="物流最后状态描述长度必须介于 0 和 100 之间")
	public String getLogisticsResultLast() {
		return logisticsResultLast;
	}

	public void setLogisticsResultLast(String logisticsResultLast) {
		this.logisticsResultLast = logisticsResultLast;
	}
	
	@Length(min=0, max=255, message="物流描述长度必须介于 0 和 255 之间")
	public String getLogisticsResult() {
		return logisticsResult;
	}

	public void setLogisticsResult(String logisticsResult) {
		this.logisticsResult = logisticsResult;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLogisticsCreateTime() {
		return logisticsCreateTime;
	}

	public void setLogisticsCreateTime(Date logisticsCreateTime) {
		this.logisticsCreateTime = logisticsCreateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLogisticsUpdateTime() {
		return logisticsUpdateTime;
	}

	public void setLogisticsUpdateTime(Date logisticsUpdateTime) {
		this.logisticsUpdateTime = logisticsUpdateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLogisticsSettlementTime() {
		return logisticsSettlementTime;
	}

	public void setLogisticsSettlementTime(Date logisticsSettlementTime) {
		this.logisticsSettlementTime = logisticsSettlementTime;
	}
	
	@Length(min=0, max=2, message="物流支付渠道长度必须介于 0 和 2 之间")
	public String getLogisticsPayChannel() {
		return logisticsPayChannel;
	}

	public void setLogisticsPayChannel(String logisticsPayChannel) {
		this.logisticsPayChannel = logisticsPayChannel;
	}
	
	@Length(min=0, max=100, message="物流支付单号长度必须介于 0 和 100 之间")
	public String getLogisticsPayNo() {
		return logisticsPayNo;
	}

	public void setLogisticsPayNo(String logisticsPayNo) {
		this.logisticsPayNo = logisticsPayNo;
	}
	
	@Length(min=0, max=1, message="物流公司对账状态（0-未对账，1-已对账）长度必须介于 0 和 1 之间")
	public String getReconciliationStatus() {
		return reconciliationStatus;
	}

	public void setReconciliationStatus(String reconciliationStatus) {
		this.reconciliationStatus = reconciliationStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReconciliationTime() {
		return reconciliationTime;
	}

	public void setReconciliationTime(Date reconciliationTime) {
		this.reconciliationTime = reconciliationTime;
	}
	
}