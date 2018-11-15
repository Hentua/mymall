package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderReturns;

/**
 * 订单售后申请DAO接口
 *
 * @author wankang
 * @version 2018-11-15
 */
@MyBatisDao
public interface OrderReturnsDao extends CrudDao<OrderReturns> {

    /**
     * 审核售后申请
     *
     * @param orderReturns 售后申请实体
     */
    void check(OrderReturns orderReturns);

    /**
     * 处理售后申请
     *
     * @param orderReturns 售后申请实体
     */
    void handle(OrderReturns orderReturns);

}