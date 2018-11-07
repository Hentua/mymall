package com.mall.modules.goods.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.goods.entity.GoodsRecommend;

/**
 * 商品推荐DAO接口
 * @author hub
 * @version 2018-11-07
 */
@MyBatisDao
public interface GoodsRecommendDao extends CrudDao<GoodsRecommend> {
	
}