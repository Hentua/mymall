package com.mall.modules.gift.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import com.google.common.collect.Lists;

import com.mall.common.persistence.DataEntity;

/**
 * 礼包配置Entity
 * @author wankang
 * @version 2018-11-07
 */
public class GiftConfig extends DataEntity<GiftConfig> {
	
	private static final long serialVersionUID = 1L;
	private String giftName;		// 礼包名称
	private Integer goodsCount;		// 商品总数量
	private String showGoodsPrice;		// 是否在APP显示商品价格 0-否 1-是
	private String giftCategory;		// 对应分类ID
	private Integer couponCount;		// 优惠券数量
	@JsonIgnore
	private Date beginCreateDate;		// 开始 create_date
	@JsonIgnore
	private Date endCreateDate;		// 结束 create_date
	private List<GiftConfigCoupon> giftConfigCouponList = Lists.newArrayList();		// 子表列表
	private List<GiftConfigGoods> giftConfigGoodsList = Lists.newArrayList();		// 子表列表

	private String giftCategoryName;
	private String giftCategoryPrice;
	
	public GiftConfig() {
		super();
	}

	public GiftConfig(String id){
		super(id);
	}

	public String getGiftCategoryName() {
		return giftCategoryName;
	}

	public void setGiftCategoryName(String giftCategoryName) {
		this.giftCategoryName = giftCategoryName;
	}

	public String getGiftCategoryPrice() {
		return giftCategoryPrice;
	}

	public void setGiftCategoryPrice(String giftCategoryPrice) {
		this.giftCategoryPrice = giftCategoryPrice;
	}

	@Length(min=1, max=20, message="礼包名称长度必须介于 1 和 20 之间")
	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	
	@NotNull(message="商品总数量不能为空")
	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}
	
	@Length(min=1, max=1, message="是否在APP显示商品价格 0-否 1-是长度必须介于 1 和 1 之间")
	public String getShowGoodsPrice() {
		return showGoodsPrice;
	}

	public void setShowGoodsPrice(String showGoodsPrice) {
		this.showGoodsPrice = showGoodsPrice;
	}
	
	@Length(min=1, max=64, message="对应分类ID长度必须介于 1 和 64 之间")
	public String getGiftCategory() {
		return giftCategory;
	}

	public void setGiftCategory(String giftCategory) {
		this.giftCategory = giftCategory;
	}
	
	@NotNull(message="优惠券数量不能为空")
	public Integer getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
	public List<GiftConfigCoupon> getGiftConfigCouponList() {
		return giftConfigCouponList;
	}

	public void setGiftConfigCouponList(List<GiftConfigCoupon> giftConfigCouponList) {
		this.giftConfigCouponList = giftConfigCouponList;
	}
	public List<GiftConfigGoods> getGiftConfigGoodsList() {
		return giftConfigGoodsList;
	}

	public void setGiftConfigGoodsList(List<GiftConfigGoods> giftConfigGoodsList) {
		this.giftConfigGoodsList = giftConfigGoodsList;
	}
}