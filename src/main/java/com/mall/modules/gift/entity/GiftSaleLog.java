package com.mall.modules.gift.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.mall.common.persistence.DataEntity;

/**
 * 礼包购买记录Entity
 * @author wankang
 * @version 2018-10-28
 */
public class GiftSaleLog extends DataEntity<GiftSaleLog> {
	
	private static final long serialVersionUID = 1L;
	private String orderNo;		// 购买订单号
	private String flag;		// 购买是否成功（0-待审核，1-购买成功，2-已作废）
	private String giftConfigId;		// 购买礼包配置ID
	private String giftMerchantId;		// 购买商家礼包ID
	private String merchantCode;		// 购买商家ID
	private Double giftPrice;		// 礼包价格
	private Integer giftGoodsCount;		// 礼包商品数量
	private Integer giftCount;		// 购买数量
	private String giftName;		// 礼包名称
	private Double giftAmountTotal; // 购买礼包总价

	private String merchantName; // 购买商家名称
	
	public GiftSaleLog() {
		super();
	}

	public GiftSaleLog(String id){
		super(id);
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Double getGiftAmountTotal() {
		return giftAmountTotal;
	}

	public void setGiftAmountTotal(Double giftAmountTotal) {
		this.giftAmountTotal = giftAmountTotal;
	}

	@Length(min=1, max=32, message="购买订单号长度必须介于 1 和 32 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=1, max=1, message="购买是否成功（0-购买失败，未完成支付，1-购买成功）长度必须介于 1 和 1 之间")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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
	
	@Length(min=1, max=64, message="购买商家ID长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	@NotNull(message="礼包价格不能为空")
	public Double getGiftPrice() {
		return giftPrice;
	}

	public void setGiftPrice(Double giftPrice) {
		this.giftPrice = giftPrice;
	}
	
	@NotNull(message="礼包商品数量不能为空")
	public Integer getGiftGoodsCount() {
		return giftGoodsCount;
	}

	public void setGiftGoodsCount(Integer giftGoodsCount) {
		this.giftGoodsCount = giftGoodsCount;
	}
	
	@NotNull(message="购买数量不能为空")
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