package com.mall.modules.goods.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 商品推荐Entity
 * @author hub
 * @version 2018-11-07
 */
public class GoodsRecommend extends DataEntity<GoodsRecommend> {
	
	private static final long serialVersionUID = 1L;
	private String goodsId;		// 商品id
	private String userId;		// 推荐用户id
	
	public GoodsRecommend() {
		super();
	}

	public GoodsRecommend(String id){
		super(id);
	}

	@Length(min=1, max=64, message="商品id长度必须介于 1 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=1, max=64, message="推荐用户id长度必须介于 1 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}