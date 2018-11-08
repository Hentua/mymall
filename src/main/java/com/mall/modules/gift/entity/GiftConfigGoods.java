package com.mall.modules.gift.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.mall.common.persistence.DataEntity;

/**
 * 礼包配置Entity
 * @author wankang
 * @version 2018-11-07
 */
public class GiftConfigGoods extends DataEntity<GiftConfigGoods> {
	
	private static final long serialVersionUID = 1L;
	private String giftConfigId;		// 礼包配置ID 父类
	private String goodsId;		// 商品ID
	private Integer goodsCount;		// 商品数量
	private String merchantCode;		// 店铺ID
	private Double goodsSettlementPrice;		// 单个商品结算价格
	private String goodsStandardId;		// 商品规格ID

	private String goodsName; // 商品名称
	private String standardName; // 商品规格名称
	
	public GiftConfigGoods() {
		super();
	}

	public GiftConfigGoods(String id){
		super(id);
	}

	public GiftConfigGoods(GiftConfig giftConfigId){
		this.giftConfigId = giftConfigId.getId();
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	@Length(min=1, max=64, message="礼包配置ID长度必须介于 1 和 64 之间")
	public String getGiftConfigId() {
		return giftConfigId;
	}

	public void setGiftConfigId(String giftConfigId) {
		this.giftConfigId = giftConfigId;
	}
	
	@Length(min=1, max=64, message="商品ID长度必须介于 1 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@NotNull(message="商品数量不能为空")
	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}
	
	@Length(min=1, max=64, message="店铺ID长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	@NotNull(message="单个商品结算价格不能为空")
	public Double getGoodsSettlementPrice() {
		return goodsSettlementPrice;
	}

	public void setGoodsSettlementPrice(Double goodsSettlementPrice) {
		this.goodsSettlementPrice = goodsSettlementPrice;
	}
	
	@Length(min=1, max=64, message="商品规格ID长度必须介于 1 和 64 之间")
	public String getGoodsStandardId() {
		return goodsStandardId;
	}

	public void setGoodsStandardId(String goodsStandardId) {
		this.goodsStandardId = goodsStandardId;
	}
	
}