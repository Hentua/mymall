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
