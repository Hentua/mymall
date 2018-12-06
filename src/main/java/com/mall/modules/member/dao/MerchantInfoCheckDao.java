package com.mall.modules.member.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.member.entity.MerchantInfoCheck;

/**
 * 店铺信息审核DAO接口
 * @author hub
 * @version 2018-12-05
 */
@MyBatisDao
public interface MerchantInfoCheckDao extends CrudDao<MerchantInfoCheck> {
	
}