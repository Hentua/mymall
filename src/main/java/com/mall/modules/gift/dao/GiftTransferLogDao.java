package com.mall.modules.gift.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.gift.entity.GiftTransferLog;

/**
 * 礼包赠送记录DAO接口
 * @author wankang
 * @version 2018-11-07
 */
@MyBatisDao
public interface GiftTransferLogDao extends CrudDao<GiftTransferLog> {
	
}