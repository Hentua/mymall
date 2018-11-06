package com.mall.modules.gift.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.gift.entity.GiftConfigCategory;
import com.mall.modules.gift.dao.GiftConfigCategoryDao;

/**
 * 礼包类别Service
 * @author wankang
 * @version 2018-11-06
 */
@Service
@Transactional(readOnly = true)
public class GiftConfigCategoryService extends CrudService<GiftConfigCategoryDao, GiftConfigCategory> {

	public GiftConfigCategory get(String id) {
		return super.get(id);
	}
	
	public List<GiftConfigCategory> findList(GiftConfigCategory giftConfigCategory) {
		return super.findList(giftConfigCategory);
	}
	
	public Page<GiftConfigCategory> findPage(Page<GiftConfigCategory> page, GiftConfigCategory giftConfigCategory) {
		return super.findPage(page, giftConfigCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(GiftConfigCategory giftConfigCategory) {
		super.save(giftConfigCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(GiftConfigCategory giftConfigCategory) {
		super.delete(giftConfigCategory);
	}
	
}