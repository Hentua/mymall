package com.mall.modules.gift.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.mall.common.persistence.DataEntity;

import java.util.Date;

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
	private String giftMerchantCode; // 赠送商户礼包ID
	private String giftCustomerCode; // 获得会员礼包ID

	private Date beginCreateDate;
	private Date endCreateDate;

	private String customerName; // 获得会员昵称
	private String giftConfigCategoryName; // 礼包名称
	private String customerMobile; // 会员手机号
	
	public GiftTransferLog() {
		super();
	}

	public GiftTransferLog(String id){
		super(id);
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

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getGiftConfigCategoryName() {
		return giftConfigCategoryName;
	}

	public void setGiftConfigCategoryName(String giftConfigCategoryName) {
		this.giftConfigCategoryName = giftConfigCategoryName;
	}

	public String getGiftMerchantCode() {
		return giftMerchantCode;
	}

	public void setGiftMerchantCode(String giftMerchantCode) {
		this.giftMerchantCode = giftMerchantCode;
	}

	public String getGiftCustomerCode() {
		return giftCustomerCode;
	}

	public void setGiftCustomerCode(String giftCustomerCode) {
		this.giftCustomerCode = giftCustomerCode;
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