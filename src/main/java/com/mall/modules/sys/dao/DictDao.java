/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.mall.modules.sys.dao;

import java.util.List;

import com.mall.modules.sys.entity.Dict;
import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;

/**
 * 字典DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
}
