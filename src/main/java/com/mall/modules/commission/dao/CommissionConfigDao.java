package com.mall.modules.commission.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.commission.entity.CommissionConfig;

/**
 * 佣金比例配置信息DAO接口
 * @author hub
 * @version 2018-10-21
 */
@MyBatisDao
public interface CommissionConfigDao extends CrudDao<CommissionConfig> {
	
}