package com.mall.modules.gift.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.gift.entity.GiftCustomerGoods;

/**
 * 会员礼包DAO接口
 * @author wankang
 * @version 2018-10-28
 */
@MyBatisDao
public interface GiftCustomerGoodsDao extends CrudDao<GiftCustomerGoods> {
	
}