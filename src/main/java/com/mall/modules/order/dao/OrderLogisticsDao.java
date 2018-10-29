package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderLogistics;

/**
 * 订单信息DAO接口
 * @author wankang
 * @version 2018-10-12
 */
@MyBatisDao
public interface OrderLogisticsDao extends CrudDao<OrderLogistics> {

    /**
     * 发货时修改物流状态
     *
     * @param orderLogistics 发货物流信息
     */
    void orderDeliverySave(OrderLogistics orderLogistics);
}