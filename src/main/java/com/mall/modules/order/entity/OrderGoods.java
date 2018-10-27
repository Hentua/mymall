package com.mall.modules.order.entity;

import com.mall.common.config.Global;
import com.mall.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 订单信息Entity
 * @author wankang
 * @version 2018-10-12
 */
public class OrderGoods extends DataEntity<OrderGoods> {
	
	private static final long serialVersionUID = 1L;
	private String goodsId;		// 商品ID
	private String goodsCategoryId;		// 商品类别ID
	private String goodsName;		// 商品名称
	private String goodsBarcode;		// 商品条码
	private String goodsTitle;		// 商品标题
	private int goodsType;		// 商品类别
	private String unit;		// 商品单位
	private String image;		// 商品图片
	private String goodsDesp;		// 商品描述
	private String orderNo;		// 对应订单编号 父类
	private Double goodsPrice;		// 商品价格
	private Double discountRate;		// 折扣比例
	private Double discountAmount;		// 折扣金额
	private Double count;		// 购买数量
	private Double subtotal;		// 小计金额
	
	public OrderGoods() {
		super();
	}

	public OrderGoods(String orderNo){
		this.orderNo = orderNo;
	}

	@Length(min=1, max=64, message="商品ID长度必须介于 1 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=1, max=64, message="商品类别ID长度必须介于 1 和 64 之间")
	public String getGoodsCategoryId() {
		return goodsCategoryId;
	}

	public void setGoodsCategoryId(String goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}
	
	@Length(min=0, max=20, message="商品名称长度必须介于 0 和 20 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@Length(min=0, max=20, message="商品条码长度必须介于 0 和 20 之间")
	public String getGoodsBarcode() {
		return goodsBarcode;
	}

	public void setGoodsBarcode(String goodsBarcode) {
		this.goodsBarcode = goodsBarcode;
	}
	
	@Length(min=0, max=200, message="商品标题长度必须介于 0 和 200 之间")
	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	
	@Length(min=0, max=64, message="商品类别长度必须介于 0 和 64 之间")
	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}
	
	@Length(min=0, max=10, message="商品单位长度必须介于 0 和 10 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=200, message="商品图片长度必须介于 0 和 200 之间")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getGoodsDesp() {
		return goodsDesp;
	}

	public void setGoodsDesp(String goodsDesp) {
		this.goodsDesp = goodsDesp;
	}
	
	@Length(min=1, max=32, message="对应订单编号长度必须介于 1 和 32 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	
	public Double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}
	
	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}
	
	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public String getFullImageUrl() {
		String fullImageUrl = "";
		if (null != image) {
			fullImageUrl = Global.getConfig("userfiles.baseURL") + image;
		}
		return fullImageUrl;
	}
	
}