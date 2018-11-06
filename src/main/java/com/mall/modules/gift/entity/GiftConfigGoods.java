package com.mall.modules.gift.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 礼包配置Entity
 * @author wankang
 * @version 2018-11-06
 */
public class GiftConfigGoods extends DataEntity<GiftConfigGoods> {
	
	private static final long serialVersionUID = 1L;
	private GiftConfig giftConfigId;		// 礼包配置ID 父类
	private String goodsId;		// 商品ID
	private String goodsCount;		// 商品数量
	private String merchantCode;		// 店铺ID
	
	public GiftConfigGoods() {
		super();
	}

	public GiftConfigGoods(String id){
		super(id);
	}

	public GiftConfigGoods(GiftConfig giftConfigId){
		this.giftConfigId = giftConfigId;
	}

	@Length(min=1, max=64, message="礼包配置ID长度必须介于 1 和 64 之间")
	public GiftConfig getGiftConfigId() {
		return giftConfigId;
	}

	public void setGiftConfigId(GiftConfig giftConfigId) {
		this.giftConfigId = giftConfigId;
	}
	
	@Length(min=1, max=64, message="商品ID长度必须介于 1 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=1, max=11, message="商品数量长度必须介于 1 和 11 之间")
	public String getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(String goodsCount) {
		this.goodsCount = goodsCount;
	}
	
	@Length(min=1, max=64, message="店铺ID长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
}