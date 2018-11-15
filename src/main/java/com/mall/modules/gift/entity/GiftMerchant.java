package com.mall.modules.gift.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;

import com.mall.common.persistence.DataEntity;

/**
 * 礼包列表Entity
 * @author wankang
 * @version 2018-11-07
 */
public class GiftMerchant extends DataEntity<GiftMerchant> {
	
	private static final long serialVersionUID = 1L;
	private String giftCategory;		// 礼包分类ID
	private Integer givenCount;		// 已经赠送数量
	private Integer giftCount;		// 礼包数量
	private Integer stock;		// 可赠送数量
	private String merchantCode;		// 商户ID
	private Date beginCreateDate;		// 开始 购买时间
	private Date endCreateDate;		// 结束 购买时间
	private Double commission; // 返佣金额

	private String giftConfigCategoryName; // 礼包名称

	private GiftConfigCategory giftConfigCategory; // 对应礼包
	private String customerMobile; // 赠送的会员手机号

	public GiftMerchant() {
		super();
	}

	public GiftMerchant(String id){
		super(id);
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public GiftConfigCategory getGiftConfigCategory() {
		return giftConfigCategory;
	}

	public void setGiftConfigCategory(GiftConfigCategory giftConfigCategory) {
		this.giftConfigCategory = giftConfigCategory;
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
	
	@NotNull(message="已经赠送数量不能为空")
	public Integer getGivenCount() {
		return givenCount;
	}

	public void setGivenCount(Integer givenCount) {
		this.givenCount = givenCount;
	}
	
	@NotNull(message="礼包数量不能为空")
	public Integer getGiftCount() {
		return giftCount;
	}

	public void setGiftCount(Integer giftCount) {
		this.giftCount = giftCount;
	}
	
	@NotNull(message="可赠送数量不能为空")
	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	@Length(min=1, max=64, message="商户ID长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
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
		
}