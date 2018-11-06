package com.mall.modules.goods.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.goods.entity.GoodsEvaluate;

import java.util.List;
import java.util.Map;

/**
 * 商品评价DAO接口
 * @author hub
 * @version 2018-11-06
 */
@MyBatisDao
public interface GoodsEvaluateDao extends CrudDao<GoodsEvaluate> {
	public Map<String,Object> findCount(GoodsEvaluate goodsEvaluate);

    public List<GoodsEvaluate> findListBy2(GoodsEvaluate goodsEvaluate);
}