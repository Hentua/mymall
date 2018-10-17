package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderShoppingCart;
import com.mall.modules.order.entity.OrderShoppingCartVo;

import java.util.List;

/**
 * 购物车DAO接口
 * @author wankang
 * @version 2018-10-16
 */
@MyBatisDao
public interface OrderShoppingCartDao extends CrudDao<OrderShoppingCart> {

    /**
     * 获取购物车列表
     *
     * @param customerCode 用户ID
     * @return 购物车实体列表
     */
    List<OrderShoppingCartVo> findShoppingCartDetailList(String customerCode);

    /**
     * 根据条件获取购物车实体信息
     *
     * @param orderShoppingCart 购物车实体条件
     * @return 购物车实体
     */
    OrderShoppingCart getByCondition(OrderShoppingCart orderShoppingCart);

}