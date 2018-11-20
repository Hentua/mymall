package com.mall.modules.coupon.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.mall.common.persistence.DataEntity;

/**
 * 优惠券记录Entity
 * @author wankang
 * @version 2018-11-20
 */
public class CouponLog extends DataEntity<CouponLog> {
	
	private static final long serialVersionUID = 1L;
	private String couponType;		// 优惠券类型
	private Double amount;		// 金额
	private String produceChannel;		// 产生渠道
	private String type;		// 类型
	private String giftId;		// 礼包
	private String merchantCode;		// 商家ID 为0则为平台
	private String customerCode;		// 获赠会员ID
	private Double productAmount;		// 产生金额
	private String orderNo;		// 订单号

	public CouponLog() {
		super();
	}

	public CouponLog(String id){
		super(id);
	}

	public Double getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(Double productAmount) {
		this.productAmount = productAmount;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Length(min=1, max=2, message="优惠券类型长度必须介于 1 和 2 之间")
	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	
	@NotNull(message="金额不能为空")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Length(min=1, max=1, message="产生渠道长度必须介于 1 和 1 之间")
	public String getProduceChannel() {
		return produceChannel;
	}

	public void setProduceChannel(String produceChannel) {
		this.produceChannel = produceChannel;
	}
	
	@Length(min=1, max=1, message="类型长度必须介于 1 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="礼包长度必须介于 0 和 64 之间")
	public String getGiftId() {
		return giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}
	
	@Length(min=0, max=64, message="商家ID 为0则为平台长度必须介于 0 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	@Length(min=0, max=64, message="获赠会员ID长度必须介于 0 和 64 之间")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
}