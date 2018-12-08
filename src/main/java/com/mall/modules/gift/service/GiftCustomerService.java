package com.mall.modules.gift.service;

import java.util.List;

import com.mall.common.service.ServiceException;
import com.mall.modules.gift.entity.GiftMerchant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.gift.entity.GiftCustomer;
import com.mall.modules.gift.dao.GiftCustomerDao;

/**
 * 会员礼包Service
 * @author wankang
 * @version 2018-11-07
 */
@Service
@Transactional(readOnly = true)
public class GiftCustomerService extends CrudService<GiftCustomerDao, GiftCustomer> {

	public GiftCustomer get(String id) {
		return super.get(id);
	}
	
	public List<GiftCustomer> findList(GiftCustomer giftCustomer) {
		return super.findList(giftCustomer);
	}
	
	public Page<GiftCustomer> findPage(Page<GiftCustomer> page, GiftCustomer giftCustomer) {
		return super.findPage(page, giftCustomer);
	}
	
	@Transactional(readOnly = false, rollbackFor = {Exception.class, ServiceException.class}, propagation = Propagation.REQUIRED)
	public void save(GiftCustomer giftCustomer) {
		super.save(giftCustomer);
	}
	
	@Transactional(readOnly = false)
	public void delete(GiftCustomer giftCustomer) {
		super.delete(giftCustomer);
	}

	public GiftCustomer genGiftCustomer(GiftMerchant giftMerchant) {
		GiftCustomer giftCustomer = new GiftCustomer();
		giftCustomer.setGiftCount(1);
		giftCustomer.setGiftCategory(giftMerchant.getGiftCategory());
		giftCustomer.setGiftMerchantId(giftMerchant.getId());
		giftCustomer.setTransferMerchantCode(giftMerchant.getMerchantCode());
		giftCustomer.setCommission(giftMerchant.getCommission());
		return giftCustomer;
	}
	
}