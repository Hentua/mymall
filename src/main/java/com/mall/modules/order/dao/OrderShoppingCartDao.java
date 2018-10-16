package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderShoppingCart;

/**
 * 购物车DAO接口
 * @author wankang
 * @version 2018-10-16
 */
@MyBatisDao
public interface OrderShoppingCartDao extends CrudDao<OrderShoppingCart> {
	
}