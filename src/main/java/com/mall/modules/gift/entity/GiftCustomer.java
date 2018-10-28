package com.mall.modules.gift.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;

import com.mall.common.persistence.DataEntity;

/**
 * 会员礼包Entity
 * @author wankang
 * @version 2018-10-28
 */
public class GiftCustomer extends DataEntity<GiftCustomer> {
	
	private static final long serialVersionUID = 1L;
	private String giftMerchantId;		// 赠送礼包ID
	private String giftName;		// 礼包名称
	private Double originalPrice;		// 原价
	private Double giftPrice;		// 现价
	private Integer goodsCount;		// 商品总数量
	private String merchantCode;		// 赠送商家ID
	private String customerCode;		// 收礼包会员ID
	private Integer giftCount;		// 礼包数量
	private List<GiftCustomerGoods> giftCustomerGoodsList = Lists.newArrayList();		// 子表列表
	
	public GiftCustomer() {
		super();
	}

	public GiftCustomer(String id){
		super(id);
	}

	@Length(min=1, max=64, message="赠送礼包ID长度必须介于 1 和 64 之间")
	public String getGiftMerchantId() {
		return giftMerchantId;
	}

	public void setGiftMerchantId(String giftMerchantId) {
		this.giftMerchantId = giftMerchantId;
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
	
	@Length(min=1, max=64, message="赠送商家ID长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	@Length(min=1, max=64, message="收礼包会员ID长度必须介于 1 和 64 之间")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	@NotNull(message="礼包数量不能为空")
	public Integer getGiftCount() {
		return giftCount;
	}

	public void setGiftCount(Integer giftCount) {
		this.giftCount = giftCount;
	}
	
	public List<GiftCustomerGoods> getGiftCustomerGoodsList() {
		return giftCustomerGoodsList;
	}

	public void setGiftCustomerGoodsList(List<GiftCustomerGoods> giftCustomerGoodsList) {
		this.giftCustomerGoodsList = giftCustomerGoodsList;
	}
}