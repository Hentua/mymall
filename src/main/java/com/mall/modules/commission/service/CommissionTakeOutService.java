package com.mall.modules.commission.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.commission.entity.CommissionTakeOut;
import com.mall.modules.commission.dao.CommissionTakeOutDao;

/**
 * 佣金提现Service
 * @author hub
 * @version 2018-11-15
 */
@Service
@Transactional(readOnly = true)
public class CommissionTakeOutService extends CrudService<CommissionTakeOutDao, CommissionTakeOut> {

	public CommissionTakeOut get(String id) {
		return super.get(id);
	}
	
	public List<CommissionTakeOut> findList(CommissionTakeOut commissionTakeOut) {
		return super.findList(commissionTakeOut);
	}
	
	public Page<CommissionTakeOut> findPage(Page<CommissionTakeOut> page, CommissionTakeOut commissionTakeOut) {
		return super.findPage(page, commissionTakeOut);
	}
	
	@Transactional(readOnly = false)
	public void save(CommissionTakeOut commissionTakeOut) {
		super.save(commissionTakeOut);
	}
	
	@Transactional(readOnly = false)
	public void delete(CommissionTakeOut commissionTakeOut) {
		super.delete(commissionTakeOut);
	}
	
}