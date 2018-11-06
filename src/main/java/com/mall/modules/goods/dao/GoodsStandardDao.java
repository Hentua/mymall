package com.mall.modules.goods.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.goods.entity.GoodsStandard;
import org.apache.ibatis.annotations.Param;

/**
 * 商品规格DAO接口
 * @author hub
 * @version 2018-11-06
 */
@MyBatisDao
public interface GoodsStandardDao extends CrudDao<GoodsStandard> {
    public void deleteByGoodsId(@Param("goodsId") String goodsId);
}