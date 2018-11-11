package com.mall.modules.order.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.mall.common.persistence.DataEntity;

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
	
	public OrderSettlement() {
		super();
	}

	public OrderSettlement(String id){
		super(id);
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
	
}