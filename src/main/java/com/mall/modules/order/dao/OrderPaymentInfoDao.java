package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderPaymentInfo;

/**
 * 支付信息DAO接口
 * @author wankang
 * @version 2018-10-19
 */
@MyBatisDao
public interface OrderPaymentInfoDao extends CrudDao<OrderPaymentInfo> {

    /**
     * 根据条件查询支付信息
     *
     * @param orderPaymentInfo 查询条件
     * @return 支付信息实体
     */
    OrderPaymentInfo getByCondition(OrderPaymentInfo orderPaymentInfo);

    /**
     * 修改支付信息状态
     *
     * @param orderPaymentInfo 支付信息实体
     * @return 修改条目数
     */
    int modifyPaymentInfoStatus(OrderPaymentInfo orderPaymentInfo);
	
}