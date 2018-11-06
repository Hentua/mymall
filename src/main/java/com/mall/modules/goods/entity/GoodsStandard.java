package com.mall.modules.goods.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 商品规格Entity
 * @author hub
 * @version 2018-11-06
 */
public class GoodsStandard extends DataEntity<GoodsStandard> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private Double price;		// price
	private String goodsId;		// goods_id
	private String categoryId;		// category_id
	
	public GoodsStandard() {
		super();
	}

	public GoodsStandard(String id){
		super(id);
	}

	@Length(min=1, max=200, message="name长度必须介于 1 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Length(min=1, max=64, message="goods_id长度必须介于 1 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=1, max=64, message="category_id长度必须介于 1 和 64 之间")
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
}