package com.mall.modules.order.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 物流信息Entity
 * @author wankang
 * @version 2018-11-22
 */
public class OrderLogisticsInfo extends DataEntity<OrderLogisticsInfo> {
	
	private static final long serialVersionUID = 1L;
	private String expressType;		// 物流类型
	private String expressNo;		// 物流编号
	private String lastResult;		// 最终状态信息
	private String lastResultAll;		// 完整最终状态信息
	
	public OrderLogisticsInfo() {
		super();
	}

	public OrderLogisticsInfo(String id){
		super(id);
	}

	@Length(min=1, max=20, message="物流类型长度必须介于 1 和 20 之间")
	public String getExpressType() {
		return expressType;
	}

	public void setExpressType(String expressType) {
		this.expressType = expressType;
	}
	
	@Length(min=1, max=100, message="物流编号长度必须介于 1 和 100 之间")
	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	
	@Length(min=0, max=1000, message="最终状态信息长度必须介于 0 和 1000 之间")
	public String getLastResult() {
		return lastResult;
	}

	public void setLastResult(String lastResult) {
		this.lastResult = lastResult;
	}
	
	@Length(min=0, max=1000, message="完整最终状态信息长度必须介于 0 和 1000 之间")
	public String getLastResultAll() {
		return lastResultAll;
	}

	public void setLastResultAll(String lastResultAll) {
		this.lastResultAll = lastResultAll;
	}
	
}