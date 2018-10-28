package com.mall.modules.gift.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import com.google.common.collect.Lists;

import com.mall.common.persistence.DataEntity;

/**
 * 礼包列表Entity
 * @author wankang
 * @version 2018-10-28
 */
public class GiftMerchant extends DataEntity<GiftMerchant> {
	
	private static final long serialVersionUID = 1L;
	private String giftConfigId;		// 购买礼包配置ID
	private String giftName;		// 礼包名称
	private Double originalPrice;		// 原价
	private Double giftPrice;		// 现价
	private Integer goodsCount;		// 商品总数量
	private String merchantCode;		// 购买商家ID
	private String orderNo;		// 购买关联订单号
	private Integer giftCount;		// 礼包数量
	private Date beginCreateDate;		// 开始 create_date
	private Date endCreateDate;		// 结束 create_date
	private List<GiftMerchantGoods> giftMerchantGoodsList = Lists.newArrayList();		// 子表列表
	
	public GiftMerchant() {
		super();
	}

	public GiftMerchant(String id){
		super(id);
	}

	@Length(min=1, max=64, message="购买礼包配置ID长度必须介于 1 和 64 之间")
	public String getGiftConfigId() {
		return giftConfigId;
	}

	public void setGiftConfigId(String giftConfigId) {
		this.giftConfigId = giftConfigId;
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
	
	@Length(min=1, max=64, message="购买商家ID长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	@Length(min=1, max=32, message="购买关联订单号长度必须介于 1 和 32 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@NotNull(message="礼包数量不能为空")
	public Integer getGiftCount() {
		return giftCount;
	}

	public void setGiftCount(Integer giftCount) {
		this.giftCount = giftCount;
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
		
	public List<GiftMerchantGoods> getGiftMerchantGoodsList() {
		return giftMerchantGoodsList;
	}

	public void setGiftMerchantGoodsList(List<GiftMerchantGoods> giftMerchantGoodsList) {
		this.giftMerchantGoodsList = giftMerchantGoodsList;
	}
}