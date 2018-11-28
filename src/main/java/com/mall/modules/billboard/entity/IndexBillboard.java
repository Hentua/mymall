package com.mall.modules.billboard.entity;

import com.mall.common.config.Global;
import com.mall.modules.goods.entity.GoodsInfo;
import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

import java.util.List;

/**
 * 首页广告位Entity
 * @author hub
 * @version 2018-10-14
 */
public class IndexBillboard extends DataEntity<IndexBillboard> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String image;		// 轮播图片
	private Integer sort;		// 排序
	private String type;		// 类型：1轮播图广告位 2独立广告位 3开机广告 4后台首页广告
	private String appFlag;		// app广告位标识
	private String jumpId;      //跳转ID
	private String categoryId;      //跳转分类ID
	private String jumpGoodsImage; //商品图片
	private String jumpGoodsName;
	private String[] goodsId;   //form表单商品ID集合

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	//广告位关联商品集合
	private List<GoodsInfo> goodsList;
	
	public IndexBillboard() {
		super();
	}

	public IndexBillboard(String id){
		super(id);
	}

	public String getJumpGoodsName() {
		return jumpGoodsName;
	}

	public void setJumpGoodsName(String jumpGoodsName) {
		this.jumpGoodsName = jumpGoodsName;
	}

	public String getJumpId() {
		return jumpId;
	}

	public void setJumpId(String jumpId) {
		this.jumpId = jumpId;
	}

	public String getJumpGoodsImage() {
		return jumpGoodsImage;
	}

	public void setJumpGoodsImage(String jumpGoodsImage) {
		this.jumpGoodsImage = jumpGoodsImage;
	}

	public String[] getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String[] goodsId) {
		this.goodsId = goodsId;
	}

	public List<GoodsInfo> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<GoodsInfo> goodsList) {
		this.goodsList = goodsList;
	}

	@Length(min=0, max=100, message="标题长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=500, message="轮播图片长度必须介于 0 和 100 之间")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFullImageUrl() {
		String fullImageUrl = "";
		if (null != image) {
			fullImageUrl = Global.getConfig("userfiles.baseURL") + image;
		}
		return fullImageUrl;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=10, message="类型：1轮播图广告位 2独立广告位长度必须介于 0 和 10 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=10, message="app广告位标识长度必须介于 0 和 10 之间")
	public String getAppFlag() {
		return appFlag;
	}

	public void setAppFlag(String appFlag) {
		this.appFlag = appFlag;
	}
	
}