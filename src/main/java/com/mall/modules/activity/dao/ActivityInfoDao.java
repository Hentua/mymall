package com.mall.modules.activity.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.activity.entity.ActivityInfo;

/**
 * 活动配置DAO接口
 * @author wankang
 * @version 2018-11-14
 */
@MyBatisDao
public interface ActivityInfoDao extends CrudDao<ActivityInfo> {
	
}