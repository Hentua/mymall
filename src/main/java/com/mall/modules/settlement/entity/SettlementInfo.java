package com.mall.modules.settlement.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.mall.common.persistence.DataEntity;

/**
 * 提现结算信息Entity
 * @author hub
 * @version 2018-10-21
 */
public class SettlementInfo extends DataEntity<SettlementInfo> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 提现结算类型 （1：佣金提现 2：订单交易结算）
	private String unionId;		// 关联单号（佣金明细ID 订单ID）
	private Double amount;		// 结算金额
	private String status;		// 结算审核状态 1：待提交 2:待结算 3：已结算
	private String subUserId;		// 提交人
	private String subUserName;		// 提交人
	private Date subDate;		// 提交时间
	private String auditUserId;		// 审核人
	private Date auditDate;		// 审核时间
	private String auditRemarks;		// 审核备注
	private String settlementUserId;		// 结算人
	private String settlementUserName;		// 结算人
	private Date settlementDate;		// 结算时间
	private String settlementRemarks;		// 结束备注

	private Date startSubDate;
	private Date endSubDate;

	public Date getStartSubDate() {
		return startSubDate;
	}

	public void setStartSubDate(Date startSubDate) {
		this.startSubDate = startSubDate;
	}

	public Date getEndSubDate() {
		return endSubDate;
	}

	public void setEndSubDate(Date endSubDate) {
		this.endSubDate = endSubDate;
	}

	public String getSubUserName() {
		return subUserName;
	}

	public void setSubUserName(String subUserName) {
		this.subUserName = subUserName;
	}

	public String getSettlementUserName() {
		return settlementUserName;
	}

	public void setSettlementUserName(String settlementUserName) {
		this.settlementUserName = settlementUserName;
	}

	public SettlementInfo() {
		super();
	}

	public SettlementInfo(String id){
		super(id);
	}

	@Length(min=0, max=10, message="提现结算类型 （1：佣金提现 2：订单交易结算）长度必须介于 0 和 10 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="关联单号（佣金明细ID 订单ID）长度必须介于 0 和 64 之间")
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=10, message="结算审核状态 1：待提交 2:已审核 3：已结算长度必须介于 0 和 10 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=64, message="提交人长度必须介于 0 和 64 之间")
	public String getSubUserId() {
		return subUserId;
	}

	public void setSubUserId(String subUserId) {
		this.subUserId = subUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSubDate() {
		return subDate;
	}

	public void setSubDate(Date subDate) {
		this.subDate = subDate;
	}
	
	@Length(min=0, max=64, message="审核人长度必须介于 0 和 64 之间")
	public String getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	
	@Length(min=0, max=500, message="审核备注长度必须介于 0 和 500 之间")
	public String getAuditRemarks() {
		return auditRemarks;
	}

	public void setAuditRemarks(String auditRemarks) {
		this.auditRemarks = auditRemarks;
	}
	
	@Length(min=0, max=64, message="结算人长度必须介于 0 和 64 之间")
	public String getSettlementUserId() {
		return settlementUserId;
	}

	public void setSettlementUserId(String settlementUserId) {
		this.settlementUserId = settlementUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}
	
	@Length(min=0, max=500, message="结束备注长度必须介于 0 和 500 之间")
	public String getSettlementRemarks() {
		return settlementRemarks;
	}

	public void setSettlementRemarks(String settlementRemarks) {
		this.settlementRemarks = settlementRemarks;
	}
	
}