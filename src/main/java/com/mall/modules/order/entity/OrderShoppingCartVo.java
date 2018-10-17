package com.mall.modules.order.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车返回前端展示实体
 *
 * @author wankang
 * @since 2018-10-17
 */
public class OrderShoppingCartVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String merchantCode; // 店铺ID
    private String merchantName; // 店铺名称

    private List<OrderShoppingCart> orderShoppingCarts; // 购物车商品信息

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

    public List<OrderShoppingCart> getOrderShoppingCarts() {
        return orderShoppingCarts;
    }

    public void setOrderShoppingCarts(List<OrderShoppingCart> orderShoppingCarts) {
        this.orderShoppingCarts = orderShoppingCarts;
    }
}
