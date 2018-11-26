package com.mall.modules.gift.entity;

import com.mall.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.mall.common.persistence.DataEntity;

/**
 * 礼包购买记录Entity
 * @author wankang
 * @version 2018-11-07
 */
public class GiftPurchaseLog extends DataEntity<GiftPurchaseLog> {
	
	private static final long serialVersionUID = 1L;
	private String merchantCode;		// 购买商户ID
	private String giftCategory;		// 购买分类ID
	private Integer giftCount;		// 购买数量
	private Double giftPrice;		// 购买单价
	private Double giftAmountTotal;		// 总价
	private String paymentNo;		// 支付单号ID
	private Date payTime;		// 支付时间
	private String status;		// 礼包购买状态 0-待支付 1-购买成功 2-支付失败
	private String giftMerchantCode; // 商户礼包ID
	private String payChannel; // 支付渠道 0-微信支付 3-余额支付 2-打款到财务

	private String giftConfigCategoryName; // 礼包名称
	private String merchantName; // 购买商户名称
	private String statusZh; // 购买状态中文名称
	private String payChannelZh; // 支付渠道中文名称
	private Date startDate;
	private Date endDate;
	private Date startPayDate;
	private Date endPayDate;
	private String merchantId;

	public GiftPurchaseLog() {
		super();
	}

	public GiftPurchaseLog(String id){
		super(id);
	}

	@ExcelField(title = "商户ID", sort = 10)
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartPayDate() {
		return startPayDate;
	}

	public void setStartPayDate(Date startPayDate) {
		this.startPayDate = startPayDate;
	}

	public Date getEndPayDate() {
		return endPayDate;
	}

	public void setEndPayDate(Date endPayDate) {
		this.endPayDate = endPayDate;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	@ExcelField(title = "支付渠道", sort = 6)
	public String getPayChannelZh() {
		switch (this.payChannel) {
			case "0" : this.payChannelZh = "微信支付";break;
			case "3" : this.payChannelZh = "余额支付";break;
			case "2" : this.payChannelZh = "打款到财务";break;
			default:;
		}
		return payChannelZh;
	}

	public void setPayChannelZh(String payChannelZh) {
		this.payChannelZh = payChannelZh;
	}

	public String getGiftMerchantCode() {
		return giftMerchantCode;
	}

	public void setGiftMerchantCode(String giftMerchantCode) {
		this.giftMerchantCode = giftMerchantCode;
	}

	@ExcelField(title = "礼包名称", sort = 2)
	public String getGiftConfigCategoryName() {
		return giftConfigCategoryName;
	}

	public void setGiftConfigCategoryName(String giftConfigCategoryName) {
		this.giftConfigCategoryName = giftConfigCategoryName;
	}

	@ExcelField(title = "商户名称", sort = 11)
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	@ExcelField(title = "购买状态", sort = 9)
	public String getStatusZh() {
		switch (this.status) {
			case "0" : this.statusZh = "未支付";break;
			case "1" : this.statusZh = "购买成功";break;
			case "2" : this.statusZh = "购买失败";break;
			default:;
		}
		return statusZh;
	}

	public void setStatusZh(String statusZh) {
		this.statusZh = statusZh;
	}

	@Length(min=1, max=64, message="购买商户ID长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	@Length(min=1, max=64, message="购买分类ID长度必须介于 1 和 64 之间")
	public String getGiftCategory() {
		return giftCategory;
	}

	public void setGiftCategory(String giftCategory) {
		this.giftCategory = giftCategory;
	}

	@ExcelField(title = "购买数量", sort = 3)
	@NotNull(message="购买数量不能为空")
	public Integer getGiftCount() {
		return giftCount;
	}

	public void setGiftCount(Integer giftCount) {
		this.giftCount = giftCount;
	}

	@ExcelField(title = "单价", sort = 4)
	@NotNull(message="购买单价不能为空")
	public Double getGiftPrice() {
		return giftPrice;
	}

	public void setGiftPrice(Double giftPrice) {
		this.giftPrice = giftPrice;
	}

	@ExcelField(title = "总价", sort = 5)
	@NotNull(message="总价不能为空")
	public Double getGiftAmountTotal() {
		return giftAmountTotal;
	}

	public void setGiftAmountTotal(Double giftAmountTotal) {
		this.giftAmountTotal = giftAmountTotal;
	}

	@ExcelField(title = "订单号", sort = 1)
	@Length(min=0, max=32, message="支付单号ID长度必须介于 0 和 32 之间")
	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	@ExcelField(title = "支付时间", sort = 8)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	@Length(min=1, max=1, message="礼包购买状态 0-待支付 1-购买成功 2-支付失败长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ExcelField(title = "购买时间", sort = 7)
	@Override
	public Date getCreateDate() {
		return createDate;
	}
	
}