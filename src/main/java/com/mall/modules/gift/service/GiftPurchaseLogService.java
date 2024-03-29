package com.mall.modules.gift.service;

import java.util.List;

import com.mall.modules.gift.dao.GiftConfigCategoryDao;
import com.mall.modules.gift.entity.GiftConfigCategory;
import com.mall.modules.gift.entity.GiftMerchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.gift.entity.GiftPurchaseLog;
import com.mall.modules.gift.dao.GiftPurchaseLogDao;

/**
 * 礼包购买记录Service
 * @author wankang
 * @version 2018-11-07
 */
@Service
@Transactional(readOnly = true)
public class GiftPurchaseLogService extends CrudService<GiftPurchaseLogDao, GiftPurchaseLog> {

	@Autowired
	private GiftConfigCategoryDao giftConfigCategoryDao;

	public GiftPurchaseLog get(String id) {
		return super.get(id);
	}
	
	public List<GiftPurchaseLog> findList(GiftPurchaseLog giftPurchaseLog) {
		return super.findList(giftPurchaseLog);
	}
	
	public Page<GiftPurchaseLog> findPage(Page<GiftPurchaseLog> page, GiftPurchaseLog giftPurchaseLog) {
		return super.findPage(page, giftPurchaseLog);
	}
	
	@Transactional(readOnly = false)
	public void save(GiftPurchaseLog giftPurchaseLog) {
		super.save(giftPurchaseLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(GiftPurchaseLog giftPurchaseLog) {
		super.delete(giftPurchaseLog);
	}

	public GiftMerchant genGiftMerchant(GiftPurchaseLog giftPurchaseLog) {
		GiftConfigCategory giftConfigCategory = giftConfigCategoryDao.get(giftPurchaseLog.getGiftCategory());
		GiftMerchant giftMerchant = new GiftMerchant();
		giftMerchant.setGiftCategory(giftPurchaseLog.getGiftCategory());
		giftMerchant.setGiftCount(giftPurchaseLog.getGiftCount());
		giftMerchant.setGivenCount(0);
		giftMerchant.setStock(giftPurchaseLog.getGiftCount());
		giftMerchant.setMerchantCode(giftPurchaseLog.getMerchantCode());
		giftMerchant.setCommission(giftConfigCategory.getCommission());
		return giftMerchant;
	}
	
}