package com.mall.modules.gift.entity;

import com.mall.common.persistence.DataEntity;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
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
	private String customerId;
	private String merchantName;
	private String merchantId;
	private Double giftPrice;
	private String status;
	private String customerAccount;
	private String merchantAccount;

	public GiftTransferLog() {
		super();
	}

	public GiftTransferLog(String id){
		super(id);
	}

	@ExcelField(title = "会员账号", sort = 6)
	public String getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}

	@ExcelField(title = "商家账号", sort = 2)
	public String getMerchantAccount() {
		return merchantAccount;
	}

	public void setMerchantAccount(String merchantAccount) {
		this.merchantAccount = merchantAccount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@ExcelField(title = "商家名称", sort = 1)
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	@ExcelField(title = "金额", sort = 4)
	public Double getGiftPrice() {
		return giftPrice;
	}

	public void setGiftPrice(Double giftPrice) {
		this.giftPrice = giftPrice;
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

	@ExcelField(title = "会员名称", sort = 5)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@ExcelField(title = "礼包名称", sort = 3)
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

	@ExcelField(title = "赠送时间", sort = 7)
	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@ExcelField(title = "状态", sort = 8)
	public String statusZh() {
		String statusZh = "";
		if(StringUtils.isNotBlank(this.status)) {
			switch (this.status) {
				case "0": statusZh = "已使用";break;
				case "1": statusZh = "未使用";break;
				default: statusZh = "未使用";
			}
		}
		return statusZh;
	}
}