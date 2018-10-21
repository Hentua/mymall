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
	
}