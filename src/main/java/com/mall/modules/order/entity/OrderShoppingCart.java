package com.mall.modules.order.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 购物车Entity
 * @author wankang
 * @version 2018-10-16
 */
public class OrderShoppingCart extends DataEntity<OrderShoppingCart> {
	
	private static final long serialVersionUID = 1L;
	private String merchantCode;		// 卖家ID
	private String goodsId;		// 商品ID
	private String customerCode;		// 买家ID
	private Double goodsCount;		// 商品数量
	
	public OrderShoppingCart() {
		super();
	}

	public OrderShoppingCart(String id){
		super(id);
	}

	@Length(min=0, max=100, message="卖家ID长度必须介于 0 和 100 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	@Length(min=0, max=64, message="商品ID长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=0, max=64, message="买家ID长度必须介于 0 和 64 之间")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	public Double getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Double goodsCount) {
		this.goodsCount = goodsCount;
	}
	
}