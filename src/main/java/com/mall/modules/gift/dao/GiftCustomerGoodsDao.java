package com.mall.modules.gift.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.gift.entity.GiftCustomerGoods;
import com.mall.modules.gift.entity.GiftCustomerGoodsVo;

import java.util.List;

/**
 * 会员礼包DAO接口
 *
 * @author wankang
 * @version 2018-10-28
 */
@MyBatisDao
public interface GiftCustomerGoodsDao extends CrudDao<GiftCustomerGoods> {

    /**
     * 根据主表ID查询礼包商品表详情
     *
     * @param giftCustomerId 主表ID
     * @return list
     */
    List<GiftCustomerGoodsVo> findGiftCustomerGoodsDetail(String giftCustomerId);

}