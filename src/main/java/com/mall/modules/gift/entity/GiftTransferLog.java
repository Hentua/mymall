package com.mall.modules.gift.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.mall.common.persistence.DataEntity;

/**
 * 礼包赠送记录Entity
 * @author wankang
 * @version 2018-11-07
 */
public class GiftTransferLog extends DataEntity<GiftTransferLog> {
	
	private static final long serialVersionUID = 1L;
	private String merchantCode;		// 商户ID
	private String customerCode;		// 获得会员ID
	private String giftCategory;		// 赠送分类ID
	private Integer giftCount;		// 赠送数量
	
	public GiftTransferLog() {
		super();
	}

	public GiftTransferLog(String id){
		super(id);
	}

	@Length(min=1, max=64, message="商户ID长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	@Length(min=1, max=64, message="获得会员ID长度必须介于 1 和 64 之间")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	@Length(min=1, max=64, message="赠送分类ID长度必须介于 1 和 64 之间")
	public String getGiftCategory() {
		return giftCategory;
	}

	public void setGiftCategory(String giftCategory) {
		this.giftCategory = giftCategory;
	}
	
	@NotNull(message="赠送数量不能为空")
	public Integer getGiftCount() {
		return giftCount;
	}

	public void setGiftCount(Integer giftCount) {
		this.giftCount = giftCount;
	}
	
}