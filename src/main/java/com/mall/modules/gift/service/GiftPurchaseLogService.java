package com.mall.modules.gift.service;

import java.util.List;

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
	
}