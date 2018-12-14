package com.mall.modules.goods.entity;

import com.mall.common.config.Global;
import com.mall.common.persistence.DataEntity;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.TreeNodeData;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类Entity
 * @author hub
 * @version 2018-10-12
 */
public class GoodsCategory extends DataEntity<GoodsCategory> implements TreeNodeData<GoodsCategory> {
	
	private static final long serialVersionUID = 1L;
	private String parentCategoryId;		// 父分类id
	private String categoryName;		// 分类名称
	private Integer depth;		// 层级
	private Integer status;		// 状态 1有效 0无效
	private Long sort; //排序标识
	private String image;		//分类图片
	private String parentCategoryName;//父分类名称


	private String jumpId;      //跳转ID
	private String jumpGoodsImage; //商品图片
	private String jumpGoodsName;
	/**
	 * 子节点集合
	 */
	private List<GoodsCategory> childrens = new ArrayList<>();

	//佣金产生方式
	private String commissionMode;
	//佣金产生比例
	private Double commissionNumber;


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

	public String getJumpGoodsName() {
		return jumpGoodsName;
	}

	public void setJumpGoodsName(String jumpGoodsName) {
		this.jumpGoodsName = jumpGoodsName;
	}

	public GoodsCategory() {
		super();
	}

	public GoodsCategory(String id){
		super(id);
	}


	/**
	 * 父ID
	 *
	 * @return
	 */
	@Override
	public String getParentCode() {
		return this.parentCategoryId;
	}

	/**
	 * 层级
	 *
	 * @return
	 */
	@Override
	public Integer getTreeLevel() {
		return this.depth;
	}

	/**
	 * 设置子集合
	 *
	 * @param childrens
	 */
	@Override
	public void setChildrens(List childrens) {
		this.childrens=childrens;
	}

	/**
	 * 获取子集合
	 *
	 * @return
	 */
	@Override
	public List getChildrens() {
		return this.childrens;
	}

	/**
	 * 排序标识
	 *
	 * @return
	 */
	@Override
	public Long getTreeSort() {
		return getSort();
	}


	public String getCommissionMode() {
		return commissionMode;
	}

	public void setCommissionMode(String commissionMode) {
		this.commissionMode = commissionMode;
	}

	public Double getCommissionNumber() {
		return commissionNumber;
	}

	public void setCommissionNumber(Double commissionNumber) {
		this.commissionNumber = commissionNumber;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFullImageUrl() {
		String fullImageUrl = "";
		if (StringUtils.isNotBlank(this.image)) {
			fullImageUrl = Global.getConfig("userfiles.baseURL") + this.image;
		}
		return fullImageUrl;
	}

	@Length(min=0, max=64, message="父分类id长度必须介于 0 和 64 之间")
	public String getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
	

	@Length(min=1, max=50, message="分类名称长度必须介于 1 和 50 之间")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getParentCategoryName() {
		return parentCategoryName;
	}

	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}
}