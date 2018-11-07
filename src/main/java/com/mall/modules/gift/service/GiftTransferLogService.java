package com.mall.modules.gift.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.gift.entity.GiftTransferLog;
import com.mall.modules.gift.dao.GiftTransferLogDao;

/**
 * 礼包赠送记录Service
 * @author wankang
 * @version 2018-11-07
 */
@Service
@Transactional(readOnly = true)
public class GiftTransferLogService extends CrudService<GiftTransferLogDao, GiftTransferLog> {

	public GiftTransferLog get(String id) {
		return super.get(id);
	}
	
	public List<GiftTransferLog> findList(GiftTransferLog giftTransferLog) {
		return super.findList(giftTransferLog);
	}
	
	public Page<GiftTransferLog> findPage(Page<GiftTransferLog> page, GiftTransferLog giftTransferLog) {
		return super.findPage(page, giftTransferLog);
	}
	
	@Transactional(readOnly = false)
	public void save(GiftTransferLog giftTransferLog) {
		super.save(giftTransferLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(GiftTransferLog giftTransferLog) {
		super.delete(giftTransferLog);
	}
	
}