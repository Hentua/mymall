package com.mall.modules.coupon.utils;

/**
 * 优惠券来源
 *
 * @author wankang
 * @date 2018-11-21
 */
public class CouponProduceChannel {

    /**
     * 优惠券来源转中文
     *
     * @param produceChannel 优惠券来源标记
     * @return 中文来源信息
     */
    public static String produceChannel2Zh(String produceChannel) {
        String produceChannelZh = "";
        switch (produceChannel) {
            case "0":
                produceChannelZh = "礼包兑换";
                break;
            case "1":
                produceChannelZh = "商家赠送";
                break;
            case "2":
                produceChannelZh = "平台赠送";
                break;
            case "3":
                produceChannelZh = "佣金转余额";
                break;
            case "4":
                produceChannelZh = "充值赠送";
                break;
            case "5":
                produceChannelZh = "订单使用";
                break;
            case "6":
                produceChannelZh = "赠送";
                break;
            default:
                ;
        }
        return produceChannelZh;
    }
}
