/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.mall.modules.cms.dao;

import com.mall.modules.cms.entity.Guestbook;
import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;

/**
 * 留言DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface GuestbookDao extends CrudDao<Guestbook> {

}
