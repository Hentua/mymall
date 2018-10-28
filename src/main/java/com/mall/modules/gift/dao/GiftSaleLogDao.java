package com.mall.modules.gift.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.gift.entity.GiftSaleLog;

/**
 * 礼包购买记录DAO接口
 * @author wankang
 * @version 2018-10-28
 */
@MyBatisDao
public interface GiftSaleLogDao extends CrudDao<GiftSaleLog> {
	
}