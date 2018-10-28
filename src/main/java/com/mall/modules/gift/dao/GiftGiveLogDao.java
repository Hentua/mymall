package com.mall.modules.gift.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.gift.entity.GiftGiveLog;

/**
 * 礼包赠送记录DAO接口
 * @author wankang
 * @version 2018-10-28
 */
@MyBatisDao
public interface GiftGiveLogDao extends CrudDao<GiftGiveLog> {
	
}