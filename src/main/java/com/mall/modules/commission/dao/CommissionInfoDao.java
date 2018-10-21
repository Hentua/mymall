package com.mall.modules.commission.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.commission.entity.CommissionInfo;

/**
 * 佣金明细DAO接口
 * @author hub
 * @version 2018-10-21
 */
@MyBatisDao
public interface CommissionInfoDao extends CrudDao<CommissionInfo> {
	
}