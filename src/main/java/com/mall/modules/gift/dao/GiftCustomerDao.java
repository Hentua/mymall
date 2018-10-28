package com.mall.modules.gift.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.gift.entity.GiftCustomer;

import java.util.Map;

/**
 * 会员礼包DAO接口
 * @author wankang
 * @version 2018-10-28
 */
@MyBatisDao
public interface GiftCustomerDao extends CrudDao<GiftCustomer> {

    /**
     * 查询用户可用礼包数量
     *
     * @param customerCode 会员ID
     * @return map
     */
    Map<String, String> enabledGiftCount(String customerCode);
	
}