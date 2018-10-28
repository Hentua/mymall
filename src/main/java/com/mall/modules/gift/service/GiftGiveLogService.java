package com.mall.modules.gift.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.gift.entity.GiftGiveLog;
import com.mall.modules.gift.dao.GiftGiveLogDao;

/**
 * 礼包赠送记录Service
 * @author wankang
 * @version 2018-10-28
 */
@Service
@Transactional(readOnly = true)
public class GiftGiveLogService extends CrudService<GiftGiveLogDao, GiftGiveLog> {

	public GiftGiveLog get(String id) {
		return super.get(id);
	}
	
	public List<GiftGiveLog> findList(GiftGiveLog giftGiveLog) {
		return super.findList(giftGiveLog);
	}
	
	public Page<GiftGiveLog> findPage(Page<GiftGiveLog> page, GiftGiveLog giftGiveLog) {
		return super.findPage(page, giftGiveLog);
	}
	
	@Transactional(readOnly = false)
	public void save(GiftGiveLog giftGiveLog) {
		super.save(giftGiveLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(GiftGiveLog giftGiveLog) {
		super.delete(giftGiveLog);
	}
	
}