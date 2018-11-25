package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderWeixinReturnResult;

/**
 * 微信支付退款结果DAO接口
 * @author wankang
 * @version 2018-11-25
 */
@MyBatisDao
public interface OrderWeixinReturnResultDao extends CrudDao<OrderWeixinReturnResult> {
	
}