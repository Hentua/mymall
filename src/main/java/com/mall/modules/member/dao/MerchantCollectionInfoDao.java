package com.mall.modules.member.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.member.entity.MerchantCollectionInfo;

/**
 * 店铺收藏DAO接口
 * @author hub
 * @version 2018-11-24
 */
@MyBatisDao
public interface MerchantCollectionInfoDao extends CrudDao<MerchantCollectionInfo> {
	
}