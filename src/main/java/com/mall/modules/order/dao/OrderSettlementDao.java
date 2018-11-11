package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderSettlement;

/**
 * 订单结算DAO接口
 * @author hub
 * @version 2018-11-11
 */
@MyBatisDao
public interface OrderSettlementDao extends CrudDao<OrderSettlement> {
	
}