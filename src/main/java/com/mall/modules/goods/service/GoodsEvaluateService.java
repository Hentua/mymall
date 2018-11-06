package com.mall.modules.goods.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.goods.entity.GoodsEvaluate;
import com.mall.modules.goods.dao.GoodsEvaluateDao;

/**
 * 商品评价Service
 * @author hub
 * @version 2018-11-06
 */
@Service
@Transactional(readOnly = true)
public class GoodsEvaluateService extends CrudService<GoodsEvaluateDao, GoodsEvaluate> {

	public GoodsEvaluate get(String id) {
		return super.get(id);
	}
	
	public List<GoodsEvaluate> findList(GoodsEvaluate goodsEvaluate) {
		return super.findList(goodsEvaluate);
	}
	
	public Page<GoodsEvaluate> findPage(Page<GoodsEvaluate> page, GoodsEvaluate goodsEvaluate) {
		return super.findPage(page, goodsEvaluate);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsEvaluate goodsEvaluate) {
		super.save(goodsEvaluate);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsEvaluate goodsEvaluate) {
		super.delete(goodsEvaluate);
	}

	public Map<String,Object> findCount(GoodsEvaluate goodsEvaluate) {
		return dao.findCount(goodsEvaluate);
	}

	public List<GoodsEvaluate> findListBy2(GoodsEvaluate goodsEvaluate) {
		return dao.findListBy2(goodsEvaluate);
	}
	
}