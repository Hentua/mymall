package com.mall.modules.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.member.entity.MerchantInfoCheck;
import com.mall.modules.member.dao.MerchantInfoCheckDao;

/**
 * 店铺信息审核Service
 * @author hub
 * @version 2018-12-05
 */
@Service
@Transactional(readOnly = true)
public class MerchantInfoCheckService extends CrudService<MerchantInfoCheckDao, MerchantInfoCheck> {

	public MerchantInfoCheck get(String id) {
		return super.get(id);
	}
	
	public List<MerchantInfoCheck> findList(MerchantInfoCheck merchantInfoCheck) {
		return super.findList(merchantInfoCheck);
	}
	
	public Page<MerchantInfoCheck> findPage(Page<MerchantInfoCheck> page, MerchantInfoCheck merchantInfoCheck) {
		return super.findPage(page, merchantInfoCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantInfoCheck merchantInfoCheck) {
		super.save(merchantInfoCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantInfoCheck merchantInfoCheck) {
		super.delete(merchantInfoCheck);
	}
	
}