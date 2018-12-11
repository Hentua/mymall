package com.mall.modules.gift.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.gift.entity.GiftConfigCategory;

import java.util.List;

/**
 * 礼包类别DAO接口
 *
 * @author wankang
 * @version 2018-11-06
 */
@MyBatisDao
public interface GiftConfigCategoryDao extends CrudDao<GiftConfigCategory> {

    /**
     * 根据权限查询下拉框选择礼包类别
     *
     * @param giftConfigCategory 查询条件
     * @return 礼包类别列表
     */
    List<GiftConfigCategory> findSelectListByPower(GiftConfigCategory giftConfigCategory);

}