package com.mall.modules.coupon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mall.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * 用户优惠券Entity
 * @author wankang
 * @version 2018-10-25
 */
public class CouponCustomer extends DataEntity<CouponCustomer> {
	
	private static final long serialVersionUID = 1L;
	private String couponNo;		// 优惠券编号
	private String couponType;		// 优惠券类型（0-折扣减免，1-金额减免，2-满减）
	private String couponName;		// 优惠券名称
	private String customerCode;		// 用户ID（可使用人）
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	private String merchantCode;		// 发放商家ID，仅可在该商家使用
	private Double discountRate;		// 扣减比例
	private Double discountAmount;		// 扣减金额
	private String couponStatus;		// 优惠券状态（0-未使用，1-已使用，2-已过期）
	private Date usedTime;		// 使用时间
	private Double limitAmount; // 满减金额限制

	private String merchantName; // 可用商家名称

	@JsonIgnore
	private Set<String> merchantCodes; // 查询条件使用 商家ID
	
	public CouponCustomer() {
		super();
	}

	public CouponCustomer(String id){
		super(id);
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Double getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(Double limitAmount) {
		this.limitAmount = limitAmount;
	}

	public Set<String> getMerchantCodes() {
		return merchantCodes;
	}

	public void setMerchantCodes(Set<String> merchantCodes) {
		this.merchantCodes = merchantCodes;
	}

	@Length(min=1, max=32, message="优惠券编号长度必须介于 1 和 32 之间")
	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
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
	
	@Length(min=1, max=64, message="用户ID（可使用人）长度必须介于 1 和 64 之间")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开始时间不能为空")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结束时间不能为空")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=1, max=64, message="发放商家ID，仅可在该商家使用长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
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
	
	@Length(min=1, max=1, message="优惠券状态（0-未使用，1-已使用，2-已过期）长度必须介于 1 和 1 之间")
	public String getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(String couponStatus) {
		this.couponStatus = couponStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}
	
}