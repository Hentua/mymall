package com.mall.modules.notice.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.notice.entity.IndexNotice;

/**
 * 首页公告DAO接口
 * @author hub
 * @version 2018-11-25
 */
@MyBatisDao
public interface IndexNoticeDao extends CrudDao<IndexNotice> {
	
}