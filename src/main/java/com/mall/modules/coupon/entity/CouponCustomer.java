package com.mall.modules.coupon.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.mall.common.persistence.DataEntity;

/**
 * 用户优惠券Entity
 * @author wankang
 * @version 2018-11-20
 */
public class CouponCustomer extends DataEntity<CouponCustomer> {
	
	private static final long serialVersionUID = 1L;
	private String couponType;		// 优惠券类型
	private String customerCode;		// 用户
	private Double balance;		// 可用余额
	
	public CouponCustomer() {
		super();
	}

	public CouponCustomer(String id){
		super(id);
	}

	@Length(min=1, max=2, message="优惠券类型长度必须介于 1 和 2 之间")
	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	
	@Length(min=1, max=64, message="用户长度必须介于 1 和 64 之间")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	@NotNull(message="可用余额不能为空")
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
}