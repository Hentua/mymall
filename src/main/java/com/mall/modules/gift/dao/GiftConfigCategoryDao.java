package com.mall.modules.gift.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.gift.entity.GiftConfigCategory;

/**
 * 礼包类别DAO接口
 * @author wankang
 * @version 2018-11-06
 */
@MyBatisDao
public interface GiftConfigCategoryDao extends CrudDao<GiftConfigCategory> {
	
}