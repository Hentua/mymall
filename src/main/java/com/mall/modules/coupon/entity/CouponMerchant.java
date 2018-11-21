package com.mall.modules.coupon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 商家优惠券Entity
 * @author wankang
 * @version 2018-11-20
 */
public class CouponMerchant extends DataEntity<CouponMerchant> {
	
	private static final long serialVersionUID = 1L;
	private String couponType;		// 优惠券类型
	private String merchantCode;		// 商户ID
	private Double balance;		// 可用余额

	@JsonIgnore
	private String customerReferee; // 获赠会员
	@JsonIgnore
	private Double transferAmount; // 赠送金额
	
	public CouponMerchant() {
		super();
	}

	public CouponMerchant(String id){
		super(id);
	}

	public Double getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(Double transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getCustomerReferee() {
		return customerReferee;
	}

	public void setCustomerReferee(String customerReferee) {
		this.customerReferee = customerReferee;
	}

	@Length(min=1, max=1, message="优惠券类型长度必须介于 1 和 1 之间")
	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	
	@Length(min=1, max=64, message="商户ID长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
}