package com.mall.modules.gift.entity;

import com.mall.common.config.Global;
import com.mall.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 会员礼包Entity
 * @author wankang
 * @version 2018-10-28
 */
public class GiftCustomerGoods extends DataEntity<GiftCustomerGoods> {
	
	private static final long serialVersionUID = 1L;
	private String giftCustomerId;		// 礼包配置ID 父类
	private String goodsId;		// 商品ID
	private int goodsCount;		// 商品数量
	private String merchantCode;		// 店铺ID

	private Double goodsPrice; // 商品价格
	private String goodsName; // 商品名称
	private String image; // 商品图片
	private String unit; // 商品单位
	
	public GiftCustomerGoods() {
		super();
	}

	public GiftCustomerGoods(String id){
		super(id);
	}

	public GiftCustomerGoods(GiftCustomer giftCustomerId){
		this.giftCustomerId = giftCustomerId.getId();
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Length(min=1, max=64, message="礼包配置ID长度必须介于 1 和 64 之间")
	public String getGiftCustomerId() {
		return giftCustomerId;
	}

	public void setGiftCustomerId(String giftCustomerId) {
		this.giftCustomerId = giftCustomerId;
	}
	
	@Length(min=1, max=64, message="商品ID长度必须介于 1 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=1, max=11, message="商品数量长度必须介于 1 和 11 之间")
	public int getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}
	
	@Length(min=1, max=64, message="店铺ID长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getFullImageUrl() {
		String fullImageUrl = "";
		if(null != image){
			fullImageUrl = Global.getConfig("userfiles.baseURL")+image;
		}
		return fullImageUrl;
	}
}