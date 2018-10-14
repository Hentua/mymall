package com.mall.modules.billboard.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.billboard.entity.IndexBillboard;

/**
 * 首页广告位DAO接口
 * @author hub
 * @version 2018-10-14
 */
@MyBatisDao
public interface IndexBillboardDao extends CrudDao<IndexBillboard> {
	
}