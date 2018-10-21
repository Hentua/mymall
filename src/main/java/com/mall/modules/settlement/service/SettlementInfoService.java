package com.mall.modules.settlement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.settlement.entity.SettlementInfo;
import com.mall.modules.settlement.dao.SettlementInfoDao;

/**
 * 提现结算信息Service
 * @author hub
 * @version 2018-10-21
 */
@Service
@Transactional(readOnly = true)
public class SettlementInfoService extends CrudService<SettlementInfoDao, SettlementInfo> {

	public SettlementInfo get(String id) {
		return super.get(id);
	}
	
	public List<SettlementInfo> findList(SettlementInfo settlementInfo) {
		return super.findList(settlementInfo);
	}
	
	public Page<SettlementInfo> findPage(Page<SettlementInfo> page, SettlementInfo settlementInfo) {
		return super.findPage(page, settlementInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SettlementInfo settlementInfo) {
		super.save(settlementInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SettlementInfo settlementInfo) {
		super.delete(settlementInfo);
	}
	
}