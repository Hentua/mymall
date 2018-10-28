package com.mall.modules.gift.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 礼包兑换记录Entity
 * @author wankang
 * @version 2018-10-28
 */
public class GiftExchangeLog extends DataEntity<GiftExchangeLog> {
	
	private static final long serialVersionUID = 1L;
	private String giftCustomerId;		// 会员礼包ID
	private String customerCode;		// 兑换会员ID
	
	public GiftExchangeLog() {
		super();
	}

	public GiftExchangeLog(String id){
		super(id);
	}

	@Length(min=1, max=64, message="会员礼包ID长度必须介于 1 和 64 之间")
	public String getGiftCustomerId() {
		return giftCustomerId;
	}

	public void setGiftCustomerId(String giftCustomerId) {
		this.giftCustomerId = giftCustomerId;
	}
	
	@Length(min=1, max=64, message="兑换会员ID长度必须介于 1 和 64 之间")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
}