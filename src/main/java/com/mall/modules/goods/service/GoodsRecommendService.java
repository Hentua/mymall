package com.mall.modules.goods.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.goods.entity.GoodsRecommend;
import com.mall.modules.goods.dao.GoodsRecommendDao;

/**
 * 商品推荐Service
 * @author hub
 * @version 2018-11-07
 */
@Service
@Transactional(readOnly = true)
public class GoodsRecommendService extends CrudService<GoodsRecommendDao, GoodsRecommend> {

	public GoodsRecommend get(String id) {
		return super.get(id);
	}
	
	public List<GoodsRecommend> findList(GoodsRecommend goodsRecommend) {
		return super.findList(goodsRecommend);
	}
	
	public Page<GoodsRecommend> findPage(Page<GoodsRecommend> page, GoodsRecommend goodsRecommend) {
		return super.findPage(page, goodsRecommend);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsRecommend goodsRecommend) {
		super.save(goodsRecommend);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsRecommend goodsRecommend) {
		super.delete(goodsRecommend);
	}
	
}