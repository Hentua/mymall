package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderSettlement;

import java.util.Map;

/**
 * 订单结算DAO接口
 *
 * @author hub
 * @version 2018-11-11
 */
@MyBatisDao
public interface OrderSettlementDao extends CrudDao<OrderSettlement> {

    /**
     * 查询合计
     *
     * @param orderSettlement 查询条件
     * @return 合计
     */
    Map<String, String> findTotal(OrderSettlement orderSettlement);

}