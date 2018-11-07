package com.mall.modules.goods.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.goods.entity.BillboardGoods;
import com.mall.modules.goods.entity.GoodsInfo;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 商品信息DAO接口
 * @author hub
 * @version 2018-10-12
 */
@MyBatisDao
public interface GoodsInfoDao extends CrudDao<GoodsInfo> {

    public List<GoodsInfo> findListByApi(GoodsInfo entity);

    public List<GoodsInfo> findListByBillboard(BillboardGoods billboardGoods);

    public List<GoodsInfo> findSelectList(GoodsInfo goodsInfo);
}