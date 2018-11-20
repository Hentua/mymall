package com.mall.modules.coupon.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.coupon.entity.CouponLog;

/**
 * 优惠券记录DAO接口
 * @author wankang
 * @version 2018-11-20
 */
@MyBatisDao
public interface CouponLogDao extends CrudDao<CouponLog> {
	
}