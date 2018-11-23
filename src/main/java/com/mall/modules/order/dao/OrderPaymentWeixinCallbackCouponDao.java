package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderPaymentWeixinCallbackCoupon;

/**
 * 微信支付回调结果DAO接口
 * @author wankang
 * @version 2018-11-23
 */
@MyBatisDao
public interface OrderPaymentWeixinCallbackCouponDao extends CrudDao<OrderPaymentWeixinCallbackCoupon> {
	
}