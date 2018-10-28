package com.mall.modules.gift.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.gift.entity.GiftExchangeLog;
import com.mall.modules.gift.dao.GiftExchangeLogDao;

/**
 * 礼包兑换记录Service
 * @author wankang
 * @version 2018-10-28
 */
@Service
@Transactional(readOnly = true)
public class GiftExchangeLogService extends CrudService<GiftExchangeLogDao, GiftExchangeLog> {

	public GiftExchangeLog get(String id) {
		return super.get(id);
	}
	
	public List<GiftExchangeLog> findList(GiftExchangeLog giftExchangeLog) {
		return super.findList(giftExchangeLog);
	}
	
	public Page<GiftExchangeLog> findPage(Page<GiftExchangeLog> page, GiftExchangeLog giftExchangeLog) {
		return super.findPage(page, giftExchangeLog);
	}
	
	@Transactional(readOnly = false)
	public void save(GiftExchangeLog giftExchangeLog) {
		super.save(giftExchangeLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(GiftExchangeLog giftExchangeLog) {
		super.delete(giftExchangeLog);
	}
	
}