package com.mall.modules.goods.entity;

import com.mall.common.config.Global;
import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 商品图片Entity
 * @author hub
 * @version 2018-10-15
 */
public class GoodsImage extends DataEntity<GoodsImage> {
	
	private static final long serialVersionUID = 1L;
	private String goodsId;		// goods_id
	private String imageUrl;		// image
	
	public GoodsImage() {
		super();
	}

	public GoodsImage(String id){
		super(id);
	}

	@Length(min=0, max=64, message="goods_id长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		if(null != imageUrl){
			imageUrl = Global.getConfig("userfiles.baseURL")+imageUrl;
		}
		this.imageUrl = imageUrl;
	}
}