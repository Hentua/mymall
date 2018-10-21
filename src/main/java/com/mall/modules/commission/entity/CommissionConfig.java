package com.mall.modules.commission.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 佣金比例配置信息Entity
 * @author hub
 * @version 2018-10-21
 */
public class CommissionConfig extends DataEntity<CommissionConfig> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String type;		// 佣金类型 （1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣）
	private String mode;		// 佣金计算方式（1固定金额 2百分比金额）
	private Double number;		// 数值
	private String remarkes;		// remarkes
	
	public CommissionConfig() {
		super();
	}

	public CommissionConfig(String id){
		super(id);
	}

	@Length(min=0, max=200, message="标题长度必须介于 0 和 200 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=10, message="佣金类型 （1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣）长度必须介于 0 和 10 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=10, message="佣金计算方式（1固定金额 2百分比金额）长度必须介于 0 和 10 之间")
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}
	
	@Length(min=0, max=500, message="remarkes长度必须介于 0 和 500 之间")
	public String getRemarkes() {
		return remarkes;
	}

	public void setRemarkes(String remarkes) {
		this.remarkes = remarkes;
	}
	
}