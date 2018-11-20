package com.mall.modules.coupon.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.coupon.entity.CouponCustomer;

import java.util.Map;

/**
 * 用户优惠券DAO接口
 * @author wankang
 * @version 2018-11-20
 */
@MyBatisDao
public interface CouponCustomerDao extends CrudDao<CouponCustomer> {

    Map<String, String> enabledCouponsCount(String customerCode);
	
}