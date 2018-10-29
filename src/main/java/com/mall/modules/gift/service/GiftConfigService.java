package com.mall.modules.gift.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.utils.StringUtils;
import com.mall.modules.gift.entity.GiftConfig;
import com.mall.modules.gift.dao.GiftConfigDao;
import com.mall.modules.gift.entity.GiftConfigGoods;
import com.mall.modules.gift.dao.GiftConfigGoodsDao;

/**
 * 礼包配置Service
 * @author wankang
 * @version 2018-10-28
 */
@Service
@Transactional(readOnly = true)
public class GiftConfigService extends CrudService<GiftConfigDao, GiftConfig> {

	@Autowired
	private GiftConfigGoodsDao giftConfigGoodsDao;
	
	public GiftConfig get(String id) {
		GiftConfig giftConfig = super.get(id);
		giftConfig.setGiftConfigGoodsList(giftConfigGoodsDao.findList(new GiftConfigGoods(giftConfig)));
		return giftConfig;
	}
	
	public List<GiftConfig> findList(GiftConfig giftConfig) {
		return super.findList(giftConfig);
	}
	
	public Page<GiftConfig> findPage(Page<GiftConfig> page, GiftConfig giftConfig) {
		return super.findPage(page, giftConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(GiftConfig giftConfig) {
		super.save(giftConfig);
		for (GiftConfigGoods giftConfigGoods : giftConfig.getGiftConfigGoodsList()){
			if (giftConfigGoods.getId() == null){
				continue;
			}
			if (GiftConfigGoods.DEL_FLAG_NORMAL.equals(giftConfigGoods.getDelFlag())){
				if (StringUtils.isBlank(giftConfigGoods.getId())){
					giftConfigGoods.setGiftConfigId(giftConfig.getId());
					giftConfigGoods.preInsert();
					giftConfigGoodsDao.insert(giftConfigGoods);
				}else{
					giftConfigGoods.preUpdate();
					giftConfigGoodsDao.update(giftConfigGoods);
				}
			}else{
				giftConfigGoodsDao.delete(giftConfigGoods);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(GiftConfig giftConfig) {
		super.delete(giftConfig);
		giftConfigGoodsDao.delete(new GiftConfigGoods(giftConfig));
	}
	
}