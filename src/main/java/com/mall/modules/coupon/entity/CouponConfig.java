package com.mall.modules.coupon.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.mall.common.persistence.DataEntity;

/**
 * 优惠券规则配置Entity
 * @author wankang
 * @version 2018-11-07
 */
public class CouponConfig extends DataEntity<CouponConfig> {
	
	private static final long serialVersionUID = 1L;
	private String couponType;		// 优惠券类型（0-折扣减免，1-金额减免）
	private String couponName;		// 优惠券名称
	private Integer expiryTime;		// 过期时间 从发放时间开始计算 单位为天 如果为0 则为不过期
	private String status;		// 是否可使用（0-可使用，1-不可使用） 代表商家是否可发放
	private Double limitAmount;		// 最高折扣
	private Integer transferExpiryTime;		// 转赠过期时间 从获得日开始计算 0为不过期
	
	public CouponConfig() {
		super();
	}

	public CouponConfig(String id){
		super(id);
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
	
	@NotNull(message="过期时间 从发放时间开始计算 单位为天 如果为0 则为不过期不能为空")
	public Integer getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Integer expiryTime) {
		this.expiryTime = expiryTime;
	}
	
	@Length(min=1, max=1, message="是否可使用（0-可使用，1-不可使用） 代表商家是否可发放长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Double getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(Double limitAmount) {
		this.limitAmount = limitAmount;
	}
	
	@NotNull(message="转赠过期时间 从获得日开始计算 0为不过期不能为空")
	public Integer getTransferExpiryTime() {
		return transferExpiryTime;
	}

	public void setTransferExpiryTime(Integer transferExpiryTime) {
		this.transferExpiryTime = transferExpiryTime;
	}
	
}