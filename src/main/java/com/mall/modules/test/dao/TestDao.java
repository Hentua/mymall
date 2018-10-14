/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.mall.modules.test.dao;

import com.mall.modules.test.entity.Test;
import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;

/**
 * 测试DAO接口
 * @author ThinkGem
 * @version 2013-10-17
 */
@MyBatisDao
public interface TestDao extends CrudDao<Test> {
	
}
