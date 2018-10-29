package com.mall.modules.member.entity;

import com.mall.common.config.Global;
import com.mall.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 用户收藏Entity
 * @author wankang
 * @version 2018-10-29
 */
public class MemberFavorite extends DataEntity<MemberFavorite> {
	
	private static final long serialVersionUID = 1L;
	private String customerCode;		// 会员ID
	private String goodsId;		// 商品ID

	private Double goodsPrice; // 商品价格
	private String goodsName; // 商品名称
	private String image; // 商品图片
	private String unit; // 商品单位
	
	public MemberFavorite() {
		super();
	}

	public MemberFavorite(String id){
		super(id);
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

	@Length(min=1, max=64, message="会员ID长度必须介于 1 和 64 之间")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	@Length(min=1, max=64, message="商品ID长度必须介于 1 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getFullImageUrl() {
		String fullImageUrl = "";
		if(null != image){
			fullImageUrl = Global.getConfig("userfiles.baseURL")+image;
		}
		return fullImageUrl;
	}
}