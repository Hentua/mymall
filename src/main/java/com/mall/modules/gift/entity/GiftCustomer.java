package com.mall.modules.gift.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.mall.common.persistence.DataEntity;

/**
 * 会员礼包Entity
 * @author wankang
 * @version 2018-11-07
 */
public class GiftCustomer extends DataEntity<GiftCustomer> {
	
	private static final long serialVersionUID = 1L;
	private String giftCategory;		// 礼包分类ID
	private String giftMerchantId;		// 赠送礼包ID
	private String transferMerchantCode;		// 赠送商户ID
	private String customerCode;		// 收礼包会员ID
	private Integer giftCount;		// 礼包数量

	private String giftConfigCategoryName; // 礼包名称
	private Double giftPrice; // 礼包价格
	private String merchantQualification; // 是否赠送商户资格 0-否 1-是

	public GiftCustomer() {
		super();
	}

	public GiftCustomer(String id){
		super(id);
	}

	public Double getGiftPrice() {
		return giftPrice;
	}

	public void setGiftPrice(Double giftPrice) {
		this.giftPrice = giftPrice;
	}

	public String getMerchantQualification() {
		return merchantQualification;
	}

	public void setMerchantQualification(String merchantQualification) {
		this.merchantQualification = merchantQualification;
	}

	public String getGiftConfigCategoryName() {
		return giftConfigCategoryName;
	}

	public void setGiftConfigCategoryName(String giftConfigCategoryName) {
		this.giftConfigCategoryName = giftConfigCategoryName;
	}

	@Length(min=1, max=64, message="礼包分类ID长度必须介于 1 和 64 之间")
	public String getGiftCategory() {
		return giftCategory;
	}

	public void setGiftCategory(String giftCategory) {
		this.giftCategory = giftCategory;
	}
	
	@Length(min=1, max=64, message="赠送礼包ID长度必须介于 1 和 64 之间")
	public String getGiftMerchantId() {
		return giftMerchantId;
	}

	public void setGiftMerchantId(String giftMerchantId) {
		this.giftMerchantId = giftMerchantId;
	}
	
	@Length(min=1, max=64, message="赠送商户ID长度必须介于 1 和 64 之间")
	public String getTransferMerchantCode() {
		return transferMerchantCode;
	}

	public void setTransferMerchantCode(String transferMerchantCode) {
		this.transferMerchantCode = transferMerchantCode;
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
	
}