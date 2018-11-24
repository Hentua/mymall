package com.mall.modules.goods.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.goods.entity.GoodsCategory;
import com.mall.modules.goods.dao.GoodsCategoryDao;

/**
 * 商品分类Service
 * @author hub
 * @version 2018-10-12
 */
@Service
@Transactional(readOnly = true)
public class GoodsCategoryService extends CrudService<GoodsCategoryDao, GoodsCategory> {

	public GoodsCategory get(String id) {
		return super.get(id);
	}
	
	public List<GoodsCategory> findList(GoodsCategory goodsCategory) {
		return super.findList(goodsCategory);
	}
	
	public Page<GoodsCategory> findPage(Page<GoodsCategory> page, GoodsCategory goodsCategory) {
		return super.findPage(page, goodsCategory);
	}

	public List<GoodsCategory> findListByApi(GoodsCategory goodsCategory){
		return dao.findListByApi(goodsCategory);
	}

	public List<GoodsCategory> findMerchantList(String merchantId){
		return dao.findMerchantList(merchantId);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsCategory goodsCategory) {
		super.save(goodsCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsCategory goodsCategory) {
		super.delete(goodsCategory);
	}
	
	
	public void getTree(){
		
	}
	
}