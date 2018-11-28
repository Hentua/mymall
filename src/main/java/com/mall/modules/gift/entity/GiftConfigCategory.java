package com.mall.modules.gift.entity;

import com.mall.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 礼包类别Entity
 * @author wankang
 * @version 2018-11-06
 */
public class GiftConfigCategory extends DataEntity<GiftConfigCategory> {
	
	private static final long serialVersionUID = 1L;
	private String categoryName;		// 礼包分类名称
	private Double giftPrice;		// 礼包价格
	private String merchantQualification;		// 是否赠送商家资格 0-否 1-是
	private String status;		// 是否上架销售 0-否 1-是
	private String merchantCode; // 定制礼包商户ID
	private String merchantName; // 定制礼包商户名称
	private String merchantMobile; // 定制礼包商户手机号
	private Double commission; // 返佣金额

	private Integer buyCount; // 购买数量
	private String payChannel; // 支付渠道 0-微信支付 3-余额支付 2-打款到财务
	
	public GiftConfigCategory() {
		super();
	}

	public GiftConfigCategory(String id){
		super(id);
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getMerchantMobile() {
		return merchantMobile;
	}

	public void setMerchantMobile(String merchantMobile) {
		this.merchantMobile = merchantMobile;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	@Length(min=1, max=20, message="礼包分类名称长度必须介于 1 和 20 之间")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@NotNull(message="礼包价格不能为空")
	@Min(value = 0, message = "礼包价格不能小于0")
	public Double getGiftPrice() {
		return giftPrice;
	}

	public void setGiftPrice(Double giftPrice) {
		this.giftPrice = giftPrice;
	}
	
	@Length(min=1, max=1, message="是否赠送商家资格 0-否 1-是长度必须介于 1 和 1 之间")
	public String getMerchantQualification() {
		return merchantQualification;
	}

	public void setMerchantQualification(String merchantQualification) {
		this.merchantQualification = merchantQualification;
	}
	
	@Length(min=1, max=1, message="是否上架销售 0-否 1-是长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}