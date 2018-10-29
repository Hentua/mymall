package com.mall.modules.gift.entity;

import com.mall.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 礼包赠送记录Entity
 * @author wankang
 * @version 2018-10-28
 */
public class GiftGiveLog extends DataEntity<GiftGiveLog> {
	
	private static final long serialVersionUID = 1L;
	private String giftConfigId;		// 购买礼包配置ID
	private String giftMerchantId;		// 购买商家礼包ID
	private String giftCustomerId;		// 收到礼包ID
	private String merchantCode;		// 赠送商家ID
	private String customerCode;		// 收到会员ID
	private Integer giftGoodsCount;		// 赠送礼包商品数量
	private Integer giftCount;		// 赠送数量
	private String giftName;		// 礼包名称

	private String customerName; // 收到礼包会员昵称
	
	public GiftGiveLog() {
		super();
	}

	public GiftGiveLog(String id){
		super(id);
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Length(min=1, max=64, message="购买礼包配置ID长度必须介于 1 和 64 之间")
	public String getGiftConfigId() {
		return giftConfigId;
	}

	public void setGiftConfigId(String giftConfigId) {
		this.giftConfigId = giftConfigId;
	}
	
	@Length(min=1, max=64, message="购买商家礼包ID长度必须介于 1 和 64 之间")
	public String getGiftMerchantId() {
		return giftMerchantId;
	}

	public void setGiftMerchantId(String giftMerchantId) {
		this.giftMerchantId = giftMerchantId;
	}
	
	@Length(min=1, max=64, message="收到礼包ID长度必须介于 1 和 64 之间")
	public String getGiftCustomerId() {
		return giftCustomerId;
	}

	public void setGiftCustomerId(String giftCustomerId) {
		this.giftCustomerId = giftCustomerId;
	}
	
	@Length(min=1, max=64, message="赠送商家ID长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	@Length(min=1, max=64, message="收到会员ID长度必须介于 1 和 64 之间")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	@NotNull(message="赠送礼包商品数量不能为空")
	public Integer getGiftGoodsCount() {
		return giftGoodsCount;
	}

	public void setGiftGoodsCount(Integer giftGoodsCount) {
		this.giftGoodsCount = giftGoodsCount;
	}
	
	@NotNull(message="赠送数量不能为空")
	public Integer getGiftCount() {
		return giftCount;
	}

	public void setGiftCount(Integer giftCount) {
		this.giftCount = giftCount;
	}
	
	@Length(min=1, max=20, message="礼包名称长度必须介于 1 和 20 之间")
	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	
}