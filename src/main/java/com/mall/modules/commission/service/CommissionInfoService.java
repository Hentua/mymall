package com.mall.modules.commission.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.commission.entity.CommissionInfo;
import com.mall.modules.commission.dao.CommissionInfoDao;

/**
 * 佣金明细Service
 * @author hub
 * @version 2018-10-21
 */
@Service
@Transactional(readOnly = true)
public class CommissionInfoService extends CrudService<CommissionInfoDao, CommissionInfo> {

	public CommissionInfo get(String id) {
		return super.get(id);
	}
	
	public List<CommissionInfo> findList(CommissionInfo commissionInfo) {
		return super.findList(commissionInfo);
	}
	
	public Page<CommissionInfo> findPage(Page<CommissionInfo> page, CommissionInfo commissionInfo) {
		return super.findPage(page, commissionInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CommissionInfo commissionInfo) {
		super.save(commissionInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CommissionInfo commissionInfo) {
		super.delete(commissionInfo);
	}
	
}