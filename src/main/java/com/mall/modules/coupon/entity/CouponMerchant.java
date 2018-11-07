package com.mall.modules.coupon.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.mall.common.persistence.DataEntity;

/**
 * 商家优惠券Entity
 * @author wankang
 * @version 2018-11-07
 */
public class CouponMerchant extends DataEntity<CouponMerchant> {
	
	private static final long serialVersionUID = 1L;
	private String configId;		// config_id
	private String couponType;		// 0-五折券 1-七折券
	private String couponName;		// coupon_name
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	private Double limitAmount;		// 最高折扣限额
	private String merchantCode;		// 商户ID
	private String couponStatus;		// 0-未赠送 1-已赠送 2-已过期
	private Date transferTime;		// 赠送时间
	private String transferCustomerCode;		// 赠送会员ID
	private String accessChannel;		// 获得渠道 0-礼包赠送 1-平台赠送
	private String giftCode;		// 来源礼包id
	
	public CouponMerchant() {
		super();
	}

	public CouponMerchant(String id){
		super(id);
	}

	@Length(min=1, max=64, message="config_id长度必须介于 1 和 64 之间")
	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}
	
	@Length(min=1, max=1, message="0-五折券 1-七折券长度必须介于 1 和 1 之间")
	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	
	@Length(min=1, max=20, message="coupon_name长度必须介于 1 和 20 之间")
	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
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
	
	@NotNull(message="最高折扣限额不能为空")
	public Double getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(Double limitAmount) {
		this.limitAmount = limitAmount;
	}
	
	@Length(min=1, max=64, message="商户ID长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	@Length(min=1, max=1, message="0-未赠送 1-已赠送 2-已过期长度必须介于 1 和 1 之间")
	public String getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(String couponStatus) {
		this.couponStatus = couponStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}
	
	@Length(min=0, max=64, message="赠送会员ID长度必须介于 0 和 64 之间")
	public String getTransferCustomerCode() {
		return transferCustomerCode;
	}

	public void setTransferCustomerCode(String transferCustomerCode) {
		this.transferCustomerCode = transferCustomerCode;
	}
	
	@Length(min=1, max=1, message="获得渠道 0-礼包赠送 1-平台赠送长度必须介于 1 和 1 之间")
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
	
}