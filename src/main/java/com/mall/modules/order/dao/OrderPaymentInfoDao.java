package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderPaymentInfo;
import org.springframework.beans.factory.annotation.Autowired;

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
	
}