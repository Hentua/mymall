package com.mall.modules.sys.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.sys.entity.SysSmsLog;

/**
 * 短信发送记录DAO接口
 * @author wankang
 * @version 2018-10-11
 */
@MyBatisDao
public interface SysSmsLogDao extends CrudDao<SysSmsLog> {
	
}