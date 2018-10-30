package com.mall.modules.billboard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.billboard.entity.IndexBillboard;
import com.mall.modules.billboard.dao.IndexBillboardDao;

/**
 * 首页广告位Service
 * @author hub
 * @version 2018-10-14
 */
@Service
@Transactional(readOnly = true)
public class IndexBillboardService extends CrudService<IndexBillboardDao, IndexBillboard> {

	public IndexBillboard get(String id) {
		return super.get(id);
	}
	
	public List<IndexBillboard> findList(IndexBillboard indexBillboard) {
		return super.findList(indexBillboard);
	}
	
	public Page<IndexBillboard> findPage(Page<IndexBillboard> page, IndexBillboard indexBillboard) {
		return super.findPage(page, indexBillboard);
	}
	
	@Transactional(readOnly = false)
	public void save(IndexBillboard indexBillboard) {
		super.save(indexBillboard);
		if("2".equals(indexBillboard.getType())){
			dao.delIndexGoodsInfo(indexBillboard.getId());
			for (String goodsId: indexBillboard.getGoodsId()) {
				Map<String,Object> map = new HashMap<>();
				map.put("goodsId",goodsId);
				map.put("billboardId",indexBillboard.getId());
				dao.insertGoodsInfo(map);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(IndexBillboard indexBillboard) {
		super.delete(indexBillboard);
	}
	
}