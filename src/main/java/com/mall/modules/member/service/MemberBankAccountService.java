package com.mall.modules.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.member.entity.MemberBankAccount;
import com.mall.modules.member.dao.MemberBankAccountDao;

/**
 * 用户银行卡Service
 * @author hub
 * @version 2018-11-29
 */
@Service
@Transactional(readOnly = true)
public class MemberBankAccountService extends CrudService<MemberBankAccountDao, MemberBankAccount> {

	public MemberBankAccount get(String id) {
		return super.get(id);
	}
	
	public List<MemberBankAccount> findList(MemberBankAccount memberBankAccount) {
		return super.findList(memberBankAccount);
	}
	
	public Page<MemberBankAccount> findPage(Page<MemberBankAccount> page, MemberBankAccount memberBankAccount) {
		return super.findPage(page, memberBankAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberBankAccount memberBankAccount) {
		super.save(memberBankAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberBankAccount memberBankAccount) {
		super.delete(memberBankAccount);
	}
	
}