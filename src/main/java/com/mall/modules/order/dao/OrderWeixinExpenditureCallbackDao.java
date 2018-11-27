package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderWeixinExpenditureCallback;

/**
 * 微信提现返回结果DAO接口
 * @author wankang
 * @version 2018-11-27
 */
@MyBatisDao
public interface OrderWeixinExpenditureCallbackDao extends CrudDao<OrderWeixinExpenditureCallback> {
	
}