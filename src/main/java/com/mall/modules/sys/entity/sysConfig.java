package com.mall.modules.sys.entity;

/**
 * 系统参数
 *
 */
public class sysConfig {
    //提现手续费
    private String serviceCharge;

    //结算周期
    private String settlementCycle;

    //充值赠送折扣券比例
    private String rechargeDiscount;

    //转余额送折扣券
    private String toAccountDiscount;

    public String getToAccountDiscount() {
        return toAccountDiscount;
    }

    public void setToAccountDiscount(String toAccountDiscount) {
        this.toAccountDiscount = toAccountDiscount;
    }

    public String getRechargeDiscount() {
        return rechargeDiscount;
    }

    public void setRechargeDiscount(String rechargeDiscount) {
        this.rechargeDiscount = rechargeDiscount;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getSettlementCycle() {
        return settlementCycle;
    }

    public void setSettlementCycle(String settlementCycle) {
        this.settlementCycle = settlementCycle;
    }
}
