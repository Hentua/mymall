package com.mall.modules.settlement.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.settlement.entity.SettlementInfo;

/**
 * 提现结算信息DAO接口
 * @author hub
 * @version 2018-10-21
 */
@MyBatisDao
public interface SettlementInfoDao extends CrudDao<SettlementInfo> {
	
}