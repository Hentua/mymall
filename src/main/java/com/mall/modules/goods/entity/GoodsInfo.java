package com.mall.modules.goods.entity;

import com.mall.common.config.Global;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.mall.common.persistence.DataEntity;

/**
 * 商品信息Entity
 *
 * @author hub
 * @version 2018-10-12
 */
public class GoodsInfo extends DataEntity<GoodsInfo> {

    private static final long serialVersionUID = 1L;
    private String goodsCategoryId;        // 商品分类 二级分类
    private String goodsName;        // 商品名称
    private String goodsBarcode;        // 商品条码
    private String goodsTitle;        // 商品标题
    private Integer goodsType;        // 商品类型
    private Integer status;        // 商品状态 0待提交 1待审核 2已上架
    private String unit;        // 单位
    private Double originalPrice;        // 商品原价
    private Double goodsPrice;        // 商品现价
    private Integer salesTotal;        // 销量
    private String image;        // 商品图片
    private String goodsDesp;        // 商品描述
    private String isDeleted;        // 是否已经删除
    private Date onlinetime;        // 上架时间
    private Date beginOnlinetime;        // 开始 上架时间
    private Date endOnlinetime;        // 结束 上架时间
    private Date beginCreateDate;        // 开始 创建时间
    private Date endCreateDate;        // 结束 创建时间

    private String goodsBrandId; //商品品牌 三级分类
    private String merchantId; //商家id
    private String colour; //商品颜色
    private String billboardType;
    private List<GoodsImage> goodsImages;
    private List<String> despImages;

    private String goodsCategoryName;

    public GoodsInfo() {
        super();
    }

    public GoodsInfo(String id) {
        super(id);
    }


    public String getGoodsCategoryName() {
        return goodsCategoryName;
    }

    public void setGoodsCategoryName(String goodsCategoryName) {
        this.goodsCategoryName = goodsCategoryName;
    }

    public List<GoodsImage> getGoodsImages() {
        return goodsImages;
    }

    public void setGoodsImages(List<GoodsImage> goodsImages) {
        this.goodsImages = goodsImages;
    }

    public List<String> getDespImages() {
        return despImages;
    }

    public void setDespImages(List<String> despImages) {
        this.despImages = despImages;
    }

    @Length(min = 0, max = 64, message = "商品分类长度必须介于 0 和 64 之间")
    public String getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(String goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    @Length(min = 0, max = 200, message = "商品名称长度必须介于 0 和 200 之间")
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Length(min = 0, max = 20, message = "商品条码长度必须介于 0 和 20 之间")
    public String getGoodsBarcode() {
        return goodsBarcode;
    }

    public void setGoodsBarcode(String goodsBarcode) {
        this.goodsBarcode = goodsBarcode;
    }

    @Length(min = 0, max = 200, message = "商品标题长度必须介于 0 和 200 之间")
    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Length(min = 0, max = 10, message = "单位长度必须介于 0 和 10 之间")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getSalesTotal() {
        return salesTotal;
    }

    public void setSalesTotal(Integer salesTotal) {
        this.salesTotal = salesTotal;
    }

    @Length(min = 0, max = 200, message = "商品图片长度必须介于 0 和 200 之间")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
//		if(null != image){
//			image = Global.getConfig("userfiles.baseURL")+image;
//		}
        this.image = image;
    }

    public String getGoodsDesp() {
        return goodsDesp;
    }

    public void setGoodsDesp(String goodsDesp) {
        this.goodsDesp = goodsDesp;
    }

    @Length(min = 0, max = 4, message = "是否已经删除长度必须介于 0 和 4 之间")
    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getOnlinetime() {
        return onlinetime;
    }

    public void setOnlinetime(Date onlinetime) {
        this.onlinetime = onlinetime;
    }

    public Date getBeginOnlinetime() {
        return beginOnlinetime;
    }

    public void setBeginOnlinetime(Date beginOnlinetime) {
        this.beginOnlinetime = beginOnlinetime;
    }

    public Date getEndOnlinetime() {
        return endOnlinetime;
    }

    public void setEndOnlinetime(Date endOnlinetime) {
        this.endOnlinetime = endOnlinetime;
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


    public String getGoodsBrandId() {
        return goodsBrandId;
    }

    public void setGoodsBrandId(String goodsBrandId) {
        this.goodsBrandId = goodsBrandId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getBillboardType() {
        return billboardType;
    }

    public void setBillboardType(String billboardType) {
        this.billboardType = billboardType;
    }

    public String getFullImageUrl() {
        String fullImageUrl = "";
        if (null != image) {
            fullImageUrl = Global.getConfig("userfiles.baseURL") + image;
        }
        return fullImageUrl;
    }
}