package com.mall.modules.coupon.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.coupon.entity.CouponMerchant;

/**
 * 商家优惠券DAO接口
 * @author wankang
 * @version 2018-11-07
 */
@MyBatisDao
public interface CouponMerchantDao extends CrudDao<CouponMerchant> {
	
}