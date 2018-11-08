package com.mall.modules.gift.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.mall.common.persistence.DataEntity;

/**
 * 礼包配置Entity
 * @author wankang
 * @version 2018-11-07
 */
public class GiftConfigCoupon extends DataEntity<GiftConfigCoupon> {
	
	private static final long serialVersionUID = 1L;
	private GiftConfig giftConfigId;		// 礼包配置ID 父类
	private String couponId;		// 优惠券ID
	private Integer couponCount;		// 优惠券数量
	
	public GiftConfigCoupon() {
		super();
	}

	public GiftConfigCoupon(String id){
		super(id);
	}

	public GiftConfigCoupon(GiftConfig giftConfigId){
		this.giftConfigId = giftConfigId;
	}

	@Length(min=1, max=64, message="礼包配置ID长度必须介于 1 和 64 之间")
	public GiftConfig getGiftConfigId() {
		return giftConfigId;
	}

	public void setGiftConfigId(GiftConfig giftConfigId) {
		this.giftConfigId = giftConfigId;
	}
	
	@Length(min=1, max=64, message="优惠券ID长度必须介于 1 和 64 之间")
	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	
	@NotNull(message="优惠券数量不能为空")
	public Integer getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}
	
}