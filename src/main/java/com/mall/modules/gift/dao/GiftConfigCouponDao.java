package com.mall.modules.gift.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;

/**
 * 礼包配置DAO接口
 * @author wankang
 * @version 2018-11-07
 */
@MyBatisDao
public interface GiftConfigCouponDao extends CrudDao<GiftConfigCoupon> {
	
}