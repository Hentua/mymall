package com.mall.modules.gift.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.gift.entity.GiftSaleLog;
import com.mall.modules.gift.dao.GiftSaleLogDao;

/**
 * 礼包购买记录Service
 * @author wankang
 * @version 2018-10-28
 */
@Service
@Transactional(readOnly = true)
public class GiftSaleLogService extends CrudService<GiftSaleLogDao, GiftSaleLog> {

	public GiftSaleLog get(String id) {
		return super.get(id);
	}
	
	public List<GiftSaleLog> findList(GiftSaleLog giftSaleLog) {
		return super.findList(giftSaleLog);
	}
	
	public Page<GiftSaleLog> findPage(Page<GiftSaleLog> page, GiftSaleLog giftSaleLog) {
		return super.findPage(page, giftSaleLog);
	}
	
	@Transactional(readOnly = false)
	public void save(GiftSaleLog giftSaleLog) {
		super.save(giftSaleLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(GiftSaleLog giftSaleLog) {
		super.delete(giftSaleLog);
	}
	
}