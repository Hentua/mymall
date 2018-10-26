package com.mall.modules.coupon.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.coupon.entity.CouponCustomer;

import java.util.List;

/**
 * 用户优惠券DAO接口
 *
 * @author wankang
 * @version 2018-10-25
 */
@MyBatisDao
public interface CouponCustomerDao extends CrudDao<CouponCustomer> {

    /**
     * 根据条件获取可用优惠券数量
     *
     * @param couponCustomer 查询条件
     * @return 可用优惠券数量
     */
    List<CouponCustomer> orderEnabledCoupons(CouponCustomer couponCustomer);

}