package com.mall.modules.order.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.order.entity.OrderLogisticsInfo;

/**
 * 物流信息DAO接口
 * @author wankang
 * @version 2018-11-22
 */
@MyBatisDao
public interface OrderLogisticsInfoDao extends CrudDao<OrderLogisticsInfo> {
	
}