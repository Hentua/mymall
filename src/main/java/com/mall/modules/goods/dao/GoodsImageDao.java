package com.mall.modules.goods.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.goods.entity.GoodsImage;

/**
 * 商品图片DAO接口
 * @author hub
 * @version 2018-10-15
 */
@MyBatisDao
public interface GoodsImageDao extends CrudDao<GoodsImage> {
	
}