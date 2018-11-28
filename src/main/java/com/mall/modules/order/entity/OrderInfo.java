package com.mall.modules.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.mall.common.persistence.DataEntity;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.excel.annotation.ExcelField;
import com.mall.modules.order.utils.OrderStatus;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 订单信息Entity
 * @author wankang
 * @version 2018-10-12
 */
public class OrderInfo extends DataEntity<OrderInfo> {
	
	private static final long serialVersionUID = 1L;
	private String orderNo;		// 订单号
	private String merchantCode;		// 卖家店铺ID
	private String orderStatus;		// 订单状态（0-待付款，1-待发货，2-已发货，3-交易成功，4-交易关闭，5-售后处理中，6-处理完成）
	private String orderType;		// 订单类型（0-平台自主下单，1-礼包兑换）
	private Double goodsCount;		// 商品总数量
	private Double goodsAmountTotal;		// 订单商品总金额
	private Double logisticsFee;		// 运费
	private Double orderAmountTotal;		// 订单应付总金额
	private Double discountAmountTotal;		// 订单优惠扣减金额
	private String customerCode;		// 买家ID
	private Date payTime;		// 平台记录的支付时间
	private Date payChannelTime;		// 支付渠道记录的支付时间
	private String couponCode; // 优惠券ID
	private List<OrderGoods> orderGoodsList = Lists.newArrayList();		// 子表列表
	private OrderLogistics orderLogistics;		// 子表列表
	private Double discountRate; // 折扣比例
	private String paymentNo;
	private Date closedTime; // 交易关闭时间
	private Date autoClosedTime; // 自动关闭交易时间
	private Date completedTime; // 交易完成时间
	private Date autoCompletedTime; // 自动完成交易时间
	private Double settlementsAmount;//结算金额
	private String setFlag; //清算标记
	private Double activityDiscountAmount; //活动扣减
	private String activityId; //活动ID
	private String remindFlag; // 发货提醒
	private Date deliveryTime; // 发货时间

	private String merchantName;
	private String merchantAccount; // 卖家账号
	private String customerName;
	private String customerAccount; // 买家账号
	private String settlementStatus; // 结算状态
	private Date startDate; // 查询开始时间
	private Date endDate; // 查询结束时间

	public OrderInfo() {
		super();
	}

	public OrderInfo(String id){
		super(id);
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

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	@ExcelField(title = "商家账号", sort = 5)
	public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

	@ExcelField(title = "下单人账号", sort = 14)
    public String getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount;
    }

    public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getRemindFlag() {
		return remindFlag;
	}

	public void setRemindFlag(String remindFlag) {
		this.remindFlag = remindFlag;
	}

	@ExcelField(title = "活动扣减金额", sort = 9)
	public Double getActivityDiscountAmount() {
		return activityDiscountAmount;
	}

	public void setActivityDiscountAmount(Double activityDiscountAmount) {
		this.activityDiscountAmount = activityDiscountAmount;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getSetFlag() {
		return setFlag;
	}

	public void setSetFlag(String setFlag) {
		this.setFlag = setFlag;
	}

	@ExcelField(title = "应收货款", sort = 12)
	public Double getSettlementsAmount() {
		return settlementsAmount;
	}

	public void setSettlementsAmount(Double settlementsAmount) {
		this.settlementsAmount = settlementsAmount;
	}

	@ExcelField(title = "自动确认收货时间", sort = 18)
	public Date getAutoCompletedTime() {
		return autoCompletedTime;
	}

	public void setAutoCompletedTime(Date autoCompletedTime) {
		this.autoCompletedTime = autoCompletedTime;
	}

	@ExcelField(title = "下单人", sort = 13)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@ExcelField(title = "商家", sort = 4)
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Date getClosedTime() {
		return closedTime;
	}

	public void setClosedTime(Date closedTime) {
		this.closedTime = closedTime;
	}

	public Date getAutoClosedTime() {
		return autoClosedTime;
	}

	public void setAutoClosedTime(Date autoClosedTime) {
		this.autoClosedTime = autoClosedTime;
	}

	@ExcelField(title = "确认收货时间", sort = 17)
	public Date getCompletedTime() {
		return completedTime;
	}

	public void setCompletedTime(Date completedTime) {
		this.completedTime = completedTime;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public Double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	@ExcelField(title = "订单号", sort = 1)
	@Length(min=1, max=32, message="订单号长度必须介于 1 和 32 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=1, max=100, message="卖家店铺ID长度必须介于 1 和 100 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	@Length(min=1, max=2, message="订单状态（0-待付款，1-已付款，2-已取消，3-退款申请，4-已退款）长度必须介于 1 和 2 之间")
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Length(min=1, max=2, message="订单类型（0-平台自主下单，1-礼包兑换）长度必须介于 1 和 2 之间")
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@ExcelField(title = "商品总数量", sort = 6)
	@NotNull(message="商品总数量不能为空")
	public Double getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Double goodsCount) {
		this.goodsCount = goodsCount;
	}

	@ExcelField(title = "商品总金额", sort = 7)
	@NotNull(message="订单商品总金额不能为空")
	public Double getGoodsAmountTotal() {
		return goodsAmountTotal;
	}

	public void setGoodsAmountTotal(Double goodsAmountTotal) {
		this.goodsAmountTotal = goodsAmountTotal;
	}

	@ExcelField(title = "运费", sort = 10)
	@NotNull(message="运费不能为空")
	public Double getLogisticsFee() {
		return logisticsFee;
	}

	public void setLogisticsFee(Double logisticsFee) {
		this.logisticsFee = logisticsFee;
	}

	@ExcelField(title = "订单金额", sort = 11)
	@NotNull(message="订单应付总金额不能为空")
	public Double getOrderAmountTotal() {
		return orderAmountTotal;
	}

	public void setOrderAmountTotal(Double orderAmountTotal) {
		this.orderAmountTotal = orderAmountTotal;
	}

	@ExcelField(title = "优惠金额", sort = 8)
	@NotNull(message="订单优惠扣减金额不能为空")
	public Double getDiscountAmountTotal() {
		return discountAmountTotal;
	}

	public void setDiscountAmountTotal(Double discountAmountTotal) {
		this.discountAmountTotal = discountAmountTotal;
	}

	@Length(min=1, max=100, message="买家ID长度必须介于 1 和 100 之间")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	@ExcelField(title = "支付时间", sort = 16)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayChannelTime() {
		return payChannelTime;
	}

	public void setPayChannelTime(Date payChannelTime) {
		this.payChannelTime = payChannelTime;
	}
	
	public List<OrderGoods> getOrderGoodsList() {
		return orderGoodsList;
	}

	public void setOrderGoodsList(List<OrderGoods> orderGoodsList) {
		this.orderGoodsList = orderGoodsList;
	}

	public OrderLogistics getOrderLogistics() {
		return orderLogistics;
	}

	public void setOrderLogistics(OrderLogistics orderLogistics) {
		this.orderLogistics = orderLogistics;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	@ExcelField(title = "订单状态", sort = 2)
	public String getOrderStatusZh() {
		return OrderStatus.status2Zh(this.orderStatus);
	}

	@ExcelField(title = "订单类型", sort = 3)
	public String getOrderTypeZh() {
		String orderTypeZh = "";
		if(StringUtils.isNotBlank(this.orderType)) {
			switch (this.orderType) {
				case "0": orderTypeZh = "自主下单";break;
				case "1": orderTypeZh = "礼包兑换";break;
				case "2": orderTypeZh = "礼包购买";break;
				default:;
			}
		}
		return orderTypeZh;
	}

	@ExcelField(title = "下单时间", sort = 15)
	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@ExcelField(title = "结款状态", sort = 19)
	public String settlementStatusZh() {
		String settlementStatusZh = "";
		if(StringUtils.isNotBlank(this.settlementStatus)) {
			switch (this.settlementStatus) {
				case "1": settlementStatusZh = "未清算";break;
				case "2": settlementStatusZh = "已清算";break;
				case "3": settlementStatusZh = "已结算";break;
				default:;
			}
		}
		return settlementStatusZh;
	}
}