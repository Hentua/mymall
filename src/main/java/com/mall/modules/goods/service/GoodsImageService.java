package com.mall.modules.goods.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.goods.entity.GoodsImage;
import com.mall.modules.goods.dao.GoodsImageDao;

/**
 * 商品图片Service
 * @author hub
 * @version 2018-10-15
 */
@Service
@Transactional(readOnly = true)
public class GoodsImageService extends CrudService<GoodsImageDao, GoodsImage> {

	public GoodsImage get(String id) {
		return super.get(id);
	}
	
	public List<GoodsImage> findList(GoodsImage goodsImage) {
		return super.findList(goodsImage);
	}
	public List<GoodsImage> findListByGoodsId(String goodsId) {
		GoodsImage goodsImage = new GoodsImage();
		goodsImage.setGoodsId(goodsId);
		return super.findList(goodsImage);
	}
	
	public Page<GoodsImage> findPage(Page<GoodsImage> page, GoodsImage goodsImage) {
		return super.findPage(page, goodsImage);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsImage goodsImage) {
		super.save(goodsImage);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsImage goodsImage) {
		super.delete(goodsImage);
	}
	
}