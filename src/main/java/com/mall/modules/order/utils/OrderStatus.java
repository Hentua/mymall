package com.mall.modules.order.utils;

/**
 * 订单状态
 *
 * @author wankang
 * @since 2018-10-29
 */
public class OrderStatus {

    /**
     * 订单状态转为中文字符
     *
     * @param orderStatus 订单状态
     * @return 中文状态字符
     */
    public static String status2Zh(String orderStatus) {
        String orderStatusZh = "";
        switch (orderStatus) {
            case "0":
                orderStatusZh = "待付款";
                break;
            case "1":
                orderStatusZh = "待发货";
                break;
            case "2":
                orderStatusZh = "待收货";
                break;
            case "3":
                orderStatusZh = "交易完成";
                break;
            case "4":
                orderStatusZh = "交易关闭";
                break;
            case "5":
                orderStatusZh = "退款申请";
                break;
            case "6":
                orderStatusZh = "已退款";
                break;
            default:
                ;
                break;
        }
        return orderStatusZh;
    }
}
