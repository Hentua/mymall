package com.mall.modules.gift.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.gift.entity.GiftMerchant;
import com.mall.modules.gift.dao.GiftMerchantDao;

/**
 * 礼包列表Service
 * @author wankang
 * @version 2018-11-07
 */
@Service
@Transactional(readOnly = true)
public class GiftMerchantService extends CrudService<GiftMerchantDao, GiftMerchant> {

	public GiftMerchant get(String id) {
		return super.get(id);
	}
	
	public List<GiftMerchant> findList(GiftMerchant giftMerchant) {
		return super.findList(giftMerchant);
	}
	
	public Page<GiftMerchant> findPage(Page<GiftMerchant> page, GiftMerchant giftMerchant) {
		return super.findPage(page, giftMerchant);
	}
	
	@Transactional(readOnly = false)
	public void save(GiftMerchant giftMerchant) {
		super.save(giftMerchant);
	}
	
	@Transactional(readOnly = false)
	public void delete(GiftMerchant giftMerchant) {
		super.delete(giftMerchant);
	}
	
}