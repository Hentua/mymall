package com.mall.modules.order.entity;

import com.mall.common.config.Global;
import com.mall.common.persistence.DataEntity;
import com.mall.common.utils.StringUtils;
import org.hibernate.validator.constraints.Length;

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

	private Double goodsPrice; // 商品价格
	private String goodsName; // 商品名称
	private String image; // 商品图片
	private String unit; // 商品单位
	private String goodsStandard;//商品规格
	private Double settlementsAmount;//结算金额
	private String goodsRecommendId; //商品推荐码

	private String goodsStandardName; // 商品规格名称
	
	public OrderShoppingCart() {
		super();
	}

	public OrderShoppingCart(String id){
		super(id);
	}

	public String getGoodsStandardName() {
		return goodsStandardName;
	}

	public void setGoodsStandardName(String goodsStandardName) {
		this.goodsStandardName = goodsStandardName;
	}

	public String getGoodsRecommendId() {
		return goodsRecommendId;
	}

	public void setGoodsRecommendId(String goodsRecommendId) {
		this.goodsRecommendId = goodsRecommendId;
	}

	public Double getSettlementsAmount() {
		return settlementsAmount;
	}

	public void setSettlementsAmount(Double settlementsAmount) {
		this.settlementsAmount = settlementsAmount;
	}

	public String getGoodsStandard() {
		return goodsStandard;
	}

	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public String getFullImageUrl() {
		String fullImageUrl = "";
		if(StringUtils.isNotBlank(this.image)){
			fullImageUrl = Global.getConfig("userfiles.baseURL")+this.image;
		}
		return fullImageUrl;
	}
}