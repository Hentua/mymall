package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderInfo;

import java.util.List;
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

    /**
     * 根据条件查询订单列表
     *
     * @param orderInfo 查询条件
     * @return 订单列表
     */
    List<OrderInfo> findOrderDetailList(OrderInfo orderInfo);

    /**
     * 删除用户订单数据
     *
     * @param orderInfo 条件
     * @return 删除条目数
     */
    int deleteByUser(OrderInfo orderInfo);

    /**
     * 查询基础订单信息 查询当前用户的单个订单
     *
     * @param orderInfo 查询条件
     * @return 基础订单信息
     */
    OrderInfo getOrderBasicInfo(OrderInfo orderInfo);

    /**
     * 支付成功后修改订单状态 条件为支付单号
     *
     * @param paymentNo 支付单号
     * @return 修改条目数
     */
    int paySuccessModifyOrderStatus(String paymentNo);

    /**
     * 用户主动取消订单
     *
     * @param orderInfo 条件
     * @return 操作条目数
     */
    int orderCancel(OrderInfo orderInfo);

    /**
     * 用户点击确认收货完成交易
     *
     * @param orderInfo 条件
     * @return 操作条目数
     */
    int orderComplete(OrderInfo orderInfo);
	
}