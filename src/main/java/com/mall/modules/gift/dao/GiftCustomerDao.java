package com.mall.modules.gift.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.gift.entity.GiftCustomer;

/**
 * 会员礼包DAO接口
 * @author wankang
 * @version 2018-11-07
 */
@MyBatisDao
public interface GiftCustomerDao extends CrudDao<GiftCustomer> {
	
}