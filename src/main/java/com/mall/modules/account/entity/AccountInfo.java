package com.mall.modules.account.entity;

import com.mall.modules.commission.entity.CommissionInfo;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.settlement.entity.SettlementInfo;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.mall.common.persistence.DataEntity;

/**
 * 账户明细Entity
 * @author hub
 * @version 2018-10-21
 */
public class AccountInfo extends DataEntity<AccountInfo> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String type;		// 收支类型 1：收入 2：支出
	private String way;		// 收支方式（1：佣金收益 2：销售收益） 支出（3：提现 4：结算）
	private String unionId;		// 关联ID（佣金明细ID 销售订单ID 提现记录ID 消费订单ID）
	private Double amount;		// 金额
	private String status;		// 状态 （1：已到账 2：未到账 3：未提现结算 4：已提现结算 ）【订单交易成功后可退货期内（7天）不可以提现结算】【推荐商家入驻除外， 入驻成功则佣金到账可提现】
	private Date toAccountDate;		// 到账时间
	private String isSub; //是否提交 0未提交 已提交



	//查询起始时间
	private Date startDate;
	private Date endDate;

	//佣金信息
	private CommissionInfo commissionInfo;

	//订单信息
	private OrderInfo orderInfo;

	private SettlementInfo settlementInfo;//结算信息



	public AccountInfo() {
		super();
	}

	public AccountInfo(String id){
		super(id);
	}

	public String getIsSub() {
		return isSub;
	}

	public void setIsSub(String isSub) {
		this.isSub = isSub;
	}

	public SettlementInfo getSettlementInfo() {
		return settlementInfo;
	}

	public void setSettlementInfo(SettlementInfo settlementInfo) {
		this.settlementInfo = settlementInfo;
	}

	public CommissionInfo getCommissionInfo() {
		return commissionInfo;
	}

	public void setCommissionInfo(CommissionInfo commissionInfo) {
		this.commissionInfo = commissionInfo;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
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

	@Length(min=0, max=64, message="用户id长度必须介于 0 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=10, message="收支类型 1：收入 2：支出长度必须介于 0 和 10 之间")
	public String getType() {
		return type;
	}

	public String getTypeText(){
		if("1".equals(this.getType())){
			return "收入";
		}else{
			return "支出";
		}
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=10, message="收入（1：佣金收益 2：销售收益） 支出（3：提现 4：结算）长度必须介于 0 和 10 之间")
	public String getWay() {
		return way;
	}

	public String getWayText() {
		if("1".equals(this.getWay())){
			return "佣金收益";
		}
		if("2".equals(this.getWay())){
			return "销售收益";
		}
		if("3".equals(this.getWay())){
			return "提现";
		}
		if("4".equals(this.getWay())){
			return "结算";
		}
		return "--";
	}

	public void setWay(String way) {
		this.way = way;
	}
	
	@Length(min=0, max=64, message="关联ID（佣金明细ID 销售订单ID 提现记录ID 消费订单ID）长度必须介于 0 和 64 之间")
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
	
	@Length(min=0, max=10, message="状态 （1：已到账 2：未到账 3：未提现结算 4：已提现结算 ）【订单交易成功后可退货期内（7天）不可以提现结算】【推荐商家入驻除外， 入驻成功则佣金到账可提现】长度必须介于 0 和 10 之间")
	public String getStatus() {
		return status;
	}

	public String getStatusText() {
		if("1".equals(this.getStatus())){
			return "已到账";
		}
		if("2".equals(this.getStatus())){
			return "未到账";
		}
		if("3".equals(this.getStatus())){
			return "未提现结算";
		}
		if("4".equals(this.getStatus())){
			return "已提现结算";
		}
		return "--";
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getToAccountDate() {
		return toAccountDate;
	}

	public void setToAccountDate(Date toAccountDate) {
		this.toAccountDate = toAccountDate;
	}



	
}