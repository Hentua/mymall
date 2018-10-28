package com.mall.modules.gift.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import com.google.common.collect.Lists;

import com.mall.common.persistence.DataEntity;

/**
 * 礼包配置Entity
 * @author wankang
 * @version 2018-10-28
 */
public class GiftConfig extends DataEntity<GiftConfig> {
	
	private static final long serialVersionUID = 1L;
	private String giftName;		// 礼包名称
	private Double originalPrice;		// 原价
	private Double giftPrice;		// 现价
	private Integer goodsCount;		// 商品总数量
	private Date beginCreateDate;		// 开始 create_date
	private Date endCreateDate;		// 结束 create_date
	private List<GiftConfigGoods> giftConfigGoodsList = Lists.newArrayList();		// 子表列表
	
	public GiftConfig() {
		super();
	}

	public GiftConfig(String id){
		super(id);
	}

	@Length(min=1, max=20, message="礼包名称长度必须介于 1 和 20 之间")
	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	
	@NotNull(message="原价不能为空")
	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	@NotNull(message="现价不能为空")
	public Double getGiftPrice() {
		return giftPrice;
	}

	public void setGiftPrice(Double giftPrice) {
		this.giftPrice = giftPrice;
	}
	
	@NotNull(message="商品总数量不能为空")
	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
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
		
	public List<GiftConfigGoods> getGiftConfigGoodsList() {
		return giftConfigGoodsList;
	}

	public void setGiftConfigGoodsList(List<GiftConfigGoods> giftConfigGoodsList) {
		this.giftConfigGoodsList = giftConfigGoodsList;
	}
}