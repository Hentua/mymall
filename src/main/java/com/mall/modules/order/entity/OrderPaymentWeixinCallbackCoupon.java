package com.mall.modules.order.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 微信支付回调结果Entity
 * @author wankang
 * @version 2018-11-23
 */
public class OrderPaymentWeixinCallbackCoupon extends DataEntity<OrderPaymentWeixinCallbackCoupon> {
	
	private static final long serialVersionUID = 1L;
	private OrderPaymentWeixinCallback callbackId;		// callback_id 父类
	private Integer couponFee;		// coupon_fee
	private String couponId;		// coupon_id
	private String couponType;		// coupon_type
	
	public OrderPaymentWeixinCallbackCoupon() {
		super();
	}

	public OrderPaymentWeixinCallbackCoupon(String id){
		super(id);
	}

	public OrderPaymentWeixinCallbackCoupon(OrderPaymentWeixinCallback callbackId){
		this.callbackId = callbackId;
	}

	@Length(min=1, max=64, message="callback_id长度必须介于 1 和 64 之间")
	public OrderPaymentWeixinCallback getCallbackId() {
		return callbackId;
	}

	public void setCallbackId(OrderPaymentWeixinCallback callbackId) {
		this.callbackId = callbackId;
	}
	
	public Integer getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}
	
	@Length(min=0, max=20, message="coupon_id长度必须介于 0 和 20 之间")
	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	
	@Length(min=0, max=10, message="coupon_type长度必须介于 0 和 10 之间")
	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	
}