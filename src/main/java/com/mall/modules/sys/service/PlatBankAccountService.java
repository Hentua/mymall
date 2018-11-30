package com.mall.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.sys.entity.PlatBankAccount;
import com.mall.modules.sys.dao.PlatBankAccountDao;

/**
 * 平台银行账户管理Service
 * @author hub
 * @version 2018-11-30
 */
@Service
@Transactional(readOnly = true)
public class PlatBankAccountService extends CrudService<PlatBankAccountDao, PlatBankAccount> {

	public PlatBankAccount get(String id) {
		return super.get(id);
	}
	
	public List<PlatBankAccount> findList(PlatBankAccount platBankAccount) {
		return super.findList(platBankAccount);
	}
	
	public Page<PlatBankAccount> findPage(Page<PlatBankAccount> page, PlatBankAccount platBankAccount) {
		return super.findPage(page, platBankAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(PlatBankAccount platBankAccount) {
		super.save(platBankAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(PlatBankAccount platBankAccount) {
		super.delete(platBankAccount);
	}
	
}