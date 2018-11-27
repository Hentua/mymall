package com.mall.modules.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mall.common.persistence.DataEntity;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 订单结算Entity
 * @author hub
 * @version 2018-11-11
 */
public class OrderSettlement extends DataEntity<OrderSettlement> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// order_id
	private String userId;		// user_id
	private Double settlementAmount;		// 结算金额
	private String status;		// 结算状态 0:未清算1:已清算2:已结算
	private Date settlementDate;		// 结算时间
	private String settlementUserId;		// 结算人

	private String merchantName;
	private String merchantId;
	private String customerName;
	private String customerId;
	private Date orderDate;
	private String orderNo;
	private Double orderAmount;
	private Date startDate;
	private Date endDate;
	private Date customerAccount;
	private Date merchantAccount;

	public OrderSettlement() {
		super();
	}

	public OrderSettlement(String id){
		super(id);
	}

	@ExcelField(title = "下单人账号", sort = 6)
	public Date getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(Date customerAccount) {
		this.customerAccount = customerAccount;
	}

	@ExcelField(title = "商家账号", sort = 2)
	public Date getMerchantAccount() {
		return merchantAccount;
	}

	public void setMerchantAccount(Date merchantAccount) {
		this.merchantAccount = merchantAccount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@ExcelField(title = "订单号", sort = 4)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@ExcelField(title = "商家名称", sort = 1)
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	@ExcelField(title = "下单人", sort = 5)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@ExcelField(title = "订单时间", sort = 3)
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@ExcelField(title = "订单金额", sort = 7)
	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	@Length(min=0, max=64, message="order_id长度必须介于 0 和 64 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=64, message="user_id长度必须介于 0 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@NotNull(message="结算金额不能为空")
	@ExcelField(title = "结算金额", sort = 8)
	public Double getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	
	@Length(min=1, max=1, message="结算状态 0:未清算1:已清算2:已结算长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}
	
	@Length(min=0, max=64, message="结算人长度必须介于 0 和 64 之间")
	public String getSettlementUserId() {
		return settlementUserId;
	}

	public void setSettlementUserId(String settlementUserId) {
		this.settlementUserId = settlementUserId;
	}

	@ExcelField(title = "状态", sort = 9)
	public String getStatusZh() {
		String statusZh = "";
		if(StringUtils.isNotBlank(this.status)) {
			switch (this.status) {
				case "1": statusZh = "未清算";break;
				case "2": statusZh = "已清算";break;
				case "3": statusZh = "已结算";break;
				default:;
			}
		}
		return statusZh;
	}
	
}