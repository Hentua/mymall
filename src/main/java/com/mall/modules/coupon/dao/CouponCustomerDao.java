package com.mall.modules.coupon.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.coupon.entity.CouponCustomer;

import java.util.List;
import java.util.Map;

/**
 * 用户优惠券DAO接口
 *
 * @author wankang
 * @version 2018-10-25
 */
@MyBatisDao
public interface CouponCustomerDao extends CrudDao<CouponCustomer> {

    /**
     * 根据条件获取可用优惠券列表(直接折扣减免，直接金额减免)
     *
     * @param couponCustomer 查询条件
     * @return 可用优惠券列表
     */
    List<CouponCustomer> orderEnabledCoupons(CouponCustomer couponCustomer);

    /**
     * 根据条件获取可用优惠券列表(满减)
     *
     * @param couponCustomer 查询条件
     * @return 可用优惠券列表
     */
    List<CouponCustomer> enabledMerchantLimitCoupons(CouponCustomer couponCustomer);

    /**
     * 获取用户可用卡券数量
     *
     * @param customerCode 会员ID
     * @return map
     */
    Map<String, String> enabledCouponsCount(String customerCode);

}