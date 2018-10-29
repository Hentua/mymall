package com.mall.modules.gift.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车返回前端展示实体
 *
 * @author wankang
 * @since 2018-10-17
 */
public class GiftCustomerGoodsVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String merchantCode; // 店铺ID
    private String merchantName; // 店铺名称
    private String giftCustomerId; // 对应主表ID

    private List<GiftCustomerGoods> giftCustomerGoods; // 购物车商品信息

    public String getGiftCustomerId() {
        return giftCustomerId;
    }

    public void setGiftCustomerId(String giftCustomerId) {
        this.giftCustomerId = giftCustomerId;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public List<GiftCustomerGoods> getGiftCustomerGoods() {
        return giftCustomerGoods;
    }

    public void setGiftCustomerGoods(List<GiftCustomerGoods> giftCustomerGoods) {
        this.giftCustomerGoods = giftCustomerGoods;
    }
}
