package com.mall.modules.gift.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.gift.entity.GiftMerchant;

/**
 * 礼包列表DAO接口
 * @author wankang
 * @version 2018-10-28
 */
@MyBatisDao
public interface GiftMerchantDao extends CrudDao<GiftMerchant> {

    /**
     * 礼包库存扣减
     *
     * @param giftMerchant 礼包实体
     */
    void stockModify(GiftMerchant giftMerchant);
}