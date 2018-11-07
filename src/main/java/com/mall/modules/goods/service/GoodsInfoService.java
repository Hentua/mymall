package com.mall.modules.goods.service;

import java.util.List;

import com.mall.modules.billboard.entity.IndexBillboard;
import com.mall.modules.goods.entity.BillboardGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.goods.dao.GoodsInfoDao;

/**
 * 商品信息Service
 * @author hub
 * @version 2018-10-12
 */
@Service
@Transactional(readOnly = true)
public class GoodsInfoService extends CrudService<GoodsInfoDao, GoodsInfo> {

	@Autowired
	private GoodsInfoDao goodsInfoDao;

	public GoodsInfo get(String id) {
		return super.get(id);
	}
	
	public List<GoodsInfo> findList(GoodsInfo goodsInfo) {
		return super.findList(goodsInfo);
	}

	public Page<GoodsInfo> findPageByApi(Page<GoodsInfo> page, GoodsInfo goodsInfo) {
		goodsInfo.setPage(page);
		page.setList(dao.findListByApi(goodsInfo));
		return page;
	}

	/**
	 * 按广告 商品信息查询商品
	 * @param billboardGoods
	 * @return
	 */
	public List<GoodsInfo> findListByBillboard(BillboardGoods billboardGoods){
		System.out.println(billboardGoods.getBillboard().getId());
		List<GoodsInfo> list = dao.findListByBillboard(billboardGoods);
		System.out.println(billboardGoods.getBillboard().getId());
		return list;
	}


	public Page<GoodsInfo> findPage(Page<GoodsInfo> page, GoodsInfo goodsInfo) {
		return super.findPage(page, goodsInfo);
	}

	public Page<GoodsInfo> findSelectList(Page<GoodsInfo> page, GoodsInfo goodsInfo) {
		goodsInfo.setPage(page);
		page.setList(goodsInfoDao.findSelectList(goodsInfo));
		return page;
	}

	@Transactional(readOnly = false)
	public void save(GoodsInfo goodsInfo) {
		super.save(goodsInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsInfo goodsInfo) {
		super.delete(goodsInfo);
	}
	
}