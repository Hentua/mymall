package com.mall.modules.coupon.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.mall.common.persistence.DataEntity;

/**
 * 用户优惠券Entity
 * @author wankang
 * @version 2018-11-07
 */
public class CouponCustomer extends DataEntity<CouponCustomer> {
	
	private static final long serialVersionUID = 1L;
	private String configId;		// config_id
	private String couponType;		// 优惠券类型（0-折扣减免，1-金额减免）
	private String couponName;		// 优惠券名称
	private String customerCode;		// 用户ID（可使用人）
	private Date startDate;		// 开始时间
	private Double limitAmount;		// 最高折扣限额
	private Date endDate;		// 结束时间
	private String transferMerchantCode;		// 赠送商户ID
	private String accessChannel;		// 获得渠道 0-礼包赠送 1-商户赠送 2-平台赠送
	private String giftCode;		// 来源礼包id
	private String couponStatus;		// 优惠券状态（0-未使用，1-已使用，2-已过期）
	private Double balance;		// 优惠券可用余额
	
	public CouponCustomer() {
		super();
	}

	public CouponCustomer(String id){
		super(id);
	}

	@Length(min=1, max=64, message="config_id长度必须介于 1 和 64 之间")
	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
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
	
	@NotNull(message="最高折扣限额不能为空")
	public Double getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(Double limitAmount) {
		this.limitAmount = limitAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结束时间不能为空")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=0, max=64, message="赠送商户ID长度必须介于 0 和 64 之间")
	public String getTransferMerchantCode() {
		return transferMerchantCode;
	}

	public void setTransferMerchantCode(String transferMerchantCode) {
		this.transferMerchantCode = transferMerchantCode;
	}
	
	@Length(min=1, max=1, message="获得渠道 0-礼包赠送 1-商户赠送 2-平台赠送长度必须介于 1 和 1 之间")
	public String getAccessChannel() {
		return accessChannel;
	}

	public void setAccessChannel(String accessChannel) {
		this.accessChannel = accessChannel;
	}
	
	@Length(min=0, max=64, message="来源礼包id长度必须介于 0 和 64 之间")
	public String getGiftCode() {
		return giftCode;
	}

	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}
	
	@Length(min=1, max=1, message="优惠券状态（0-未使用，1-已使用，2-已过期）长度必须介于 1 和 1 之间")
	public String getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(String couponStatus) {
		this.couponStatus = couponStatus;
	}
	
	@NotNull(message="优惠券可用余额不能为空")
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
}