package com.mall.modules.commission.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 佣金明细Entity
 * @author hub
 * @version 2018-10-21
 */
public class CommissionInfo extends DataEntity<CommissionInfo> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣
	private String userId;		// 佣金收入人ID
	private String produceUserId;		// 被推荐人ID
	private Double originAmount;		// 产生佣金金额
	private Double amount;		// 佣金金额
	private String unionId;		// 关联订单号 【推荐商家入驻无订单号】
	private String settlementId;		// 提现结算单ID
	private String produceUserName; //被推荐人名称

	
	public CommissionInfo() {
		super();
	}

	public CommissionInfo(String id){
		super(id);
	}

	public String getProduceUserName() {
		return produceUserName;
	}

	public void setProduceUserName(String produceUserName) {
		this.produceUserName = produceUserName;
	}

	@Length(min=0, max=10, message="1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣长度必须介于 0 和 10 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="佣金收入人ID长度必须介于 0 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="被推荐人ID长度必须介于 0 和 64 之间")
	public String getProduceUserId() {
		return produceUserId;
	}

	public void setProduceUserId(String produceUserId) {
		this.produceUserId = produceUserId;
	}
	
	public Double getOriginAmount() {
		return originAmount;
	}

	public void setOriginAmount(Double originAmount) {
		this.originAmount = originAmount;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=64, message="关联订单号 【推荐商家入驻无订单号】长度必须介于 0 和 64 之间")
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
	@Length(min=0, max=64, message="提现结算单ID长度必须介于 0 和 64 之间")
	public String getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}
	
}