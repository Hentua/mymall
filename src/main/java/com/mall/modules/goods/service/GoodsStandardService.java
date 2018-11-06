package com.mall.modules.goods.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.goods.entity.GoodsStandard;
import com.mall.modules.goods.dao.GoodsStandardDao;

/**
 * 商品规格Service
 * @author hub
 * @version 2018-11-06
 */
@Service
@Transactional(readOnly = true)
public class GoodsStandardService extends CrudService<GoodsStandardDao, GoodsStandard> {

	public GoodsStandard get(String id) {
		return super.get(id);
	}
	
	public List<GoodsStandard> findList(GoodsStandard goodsStandard) {
		return super.findList(goodsStandard);
	}
	
	public Page<GoodsStandard> findPage(Page<GoodsStandard> page, GoodsStandard goodsStandard) {
		return super.findPage(page, goodsStandard);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsStandard goodsStandard) {
		super.save(goodsStandard);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsStandard goodsStandard) {
		super.delete(goodsStandard);
	}

	@Transactional(readOnly = false)
	public void deleteByGoodsId(String goodsId) {
		dao.deleteByGoodsId(goodsId);
	}
}