package com.mall.modules.sys.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.sys.entity.SysUpgrade;

/**
 * app升级配置DAO接口
 * @author hub
 * @version 2018-12-04
 */
@MyBatisDao
public interface SysUpgradeDao extends CrudDao<SysUpgrade> {
	
}