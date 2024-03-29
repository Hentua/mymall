/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.mall.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.mall.common.utils.CacheUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.service.CrudService;
import com.mall.modules.sys.dao.DictDao;
import com.mall.modules.sys.entity.Dict;
import com.mall.modules.sys.utils.DictUtils;

/**
 * 字典Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void editSysConfig(Map<String,String> map){
		dao.editSysConfig(map);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

}
