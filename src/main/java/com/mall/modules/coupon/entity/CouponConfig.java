package com.mall.modules.coupon.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 优惠券规则配置Entity
 * @author wankang
 * @version 2018-10-28
 */
public class CouponConfig extends DataEntity<CouponConfig> {
	
	private static final long serialVersionUID = 1L;
	private String couponType;		// 优惠券类型（0-折扣减免，1-金额减免，2-满减）
	private String couponName;		// 优惠券名称
	private String eapiryTime;		// 过期时间 从发放时间开始计算 单位为天
	private Double discountRate;		// 扣减比例
	private Double discountAmount;		// 扣减金额
	private String status;		// 是否可使用（0-可使用，1-不可使用） 代表商家是否可发放
	private Double limitAmount; // 满减金额限制
	
	public CouponConfig() {
		super();
	}

	public CouponConfig(String id){
		super(id);
	}

	public Double getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(Double limitAmount) {
		this.limitAmount = limitAmount;
	}

	@Length(min=1, max=2, message="优惠券类型（0-折扣减免，1-金额减免）长度必须介于 1 和 2 之间")
	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	
	@Length(min=1, max=20, message="优惠券名称长度必须介于 1 和 20 之间")
	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	
	@Length(min=1, max=11, message="过期时间 从发放时间开始计算 单位为天长度必须介于 1 和 11 之间")
	public String getEapiryTime() {
		return eapiryTime;
	}

	public void setEapiryTime(String eapiryTime) {
		this.eapiryTime = eapiryTime;
	}
	
	public Double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}
	
	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	@Length(min=1, max=1, message="是否可使用（0-可使用，1-不可使用） 代表商家是否可发放长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}