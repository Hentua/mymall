package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderInfo;

/**
 * 订单信息DAO接口
 * @author wankang
 * @version 2018-10-12
 */
@MyBatisDao
public interface OrderInfoDao extends CrudDao<OrderInfo> {
	
}