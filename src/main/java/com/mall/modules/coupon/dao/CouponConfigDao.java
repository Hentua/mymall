package com.mall.modules.coupon.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.coupon.entity.CouponConfig;

/**
 * 优惠券规则配置DAO接口
 * @author wankang
 * @version 2018-10-28
 */
@MyBatisDao
public interface CouponConfigDao extends CrudDao<CouponConfig> {
	
}