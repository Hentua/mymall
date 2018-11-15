package com.mall.modules.commission.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.commission.dao.CommissionInfoDao;
import com.mall.modules.commission.entity.CommissionInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
		dao.accumulation(commissionInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CommissionInfo commissionInfo) {
		super.delete(commissionInfo);
	}


	@Transactional(readOnly = false)
	public void editStatus(CommissionInfo commissionInfo) {
		//累加金额

		//修改状态
//		dao.editStatus(commissionInfo.getId());
	}



}