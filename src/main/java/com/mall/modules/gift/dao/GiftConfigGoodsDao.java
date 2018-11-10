package com.mall.modules.gift.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.gift.entity.GiftConfigGoods;

import java.util.List;

/**
 * 礼包配置DAO接口
 *
 * @author wankang
 * @version 2018-11-07
 */
@MyBatisDao
public interface GiftConfigGoodsDao extends CrudDao<GiftConfigGoods> {

    /**
     * 查询API礼包商品详情
     *
     * @param giftConfigGoods 查询条件
     * @return 商品详情
     */
    List<GiftConfigGoods> findApiList(GiftConfigGoods giftConfigGoods);

}