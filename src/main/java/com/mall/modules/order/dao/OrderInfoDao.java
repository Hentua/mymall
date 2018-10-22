package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderInfo;

import java.util.Map;

/**
 * 订单信息DAO接口
 * @author wankang
 * @version 2018-10-12
 */
@MyBatisDao
public interface OrderInfoDao extends CrudDao<OrderInfo> {

    /**
     * 统计用户订单状态数量
     *
     * @param customerCode 用户ID
     * @return 状态数量
     */
    Map<String, String> orderCount(String customerCode);
	
}