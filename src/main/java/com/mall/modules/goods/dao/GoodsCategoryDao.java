package com.mall.modules.goods.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.goods.entity.GoodsCategory;

import java.util.List;

/**
 * 商品分类DAO接口
 * @author hub
 * @version 2018-10-12
 */
@MyBatisDao
public interface GoodsCategoryDao extends CrudDao<GoodsCategory> {

    public List<GoodsCategory> findListByApi(GoodsCategory goodsCategory);
}