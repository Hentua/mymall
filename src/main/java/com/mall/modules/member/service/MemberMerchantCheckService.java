package com.mall.modules.member.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.member.dao.MemberMerchantCheckDao;
import com.mall.modules.member.entity.MemberMerchantCheck;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商户审核Service
 * @author wankang
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class MemberMerchantCheckService extends CrudService<MemberMerchantCheckDao, MemberMerchantCheck> {

	public MemberMerchantCheck get(String id) {
		return super.get(id);
	}
	
	public List<MemberMerchantCheck> findList(MemberMerchantCheck memberMerchantCheck) {
		return super.findList(memberMerchantCheck);
	}
	
	public Page<MemberMerchantCheck> findPage(Page<MemberMerchantCheck> page, MemberMerchantCheck memberMerchantCheck) {
		return super.findPage(page, memberMerchantCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberMerchantCheck memberMerchantCheck) {
		super.save(memberMerchantCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberMerchantCheck memberMerchantCheck) {
		super.delete(memberMerchantCheck);
	}
	
}