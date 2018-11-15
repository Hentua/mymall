package com.mall.modules.commission.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.commission.entity.CommissionTakeOut;

/**
 * 佣金提现DAO接口
 * @author hub
 * @version 2018-11-15
 */
@MyBatisDao
public interface CommissionTakeOutDao extends CrudDao<CommissionTakeOut> {
	
}