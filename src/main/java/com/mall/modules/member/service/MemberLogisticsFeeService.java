package com.mall.modules.member.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.member.dao.MemberLogisticsFeeDao;
import com.mall.modules.member.entity.MemberLogisticsFee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 运费定义Service
 * @author wankang
 * @version 2018-10-19
 */
@Service
@Transactional(readOnly = true)
public class MemberLogisticsFeeService extends CrudService<MemberLogisticsFeeDao, MemberLogisticsFee> {

	public MemberLogisticsFee get(String id) {
		return super.get(id);
	}
	
	public List<MemberLogisticsFee> findList(MemberLogisticsFee memberLogisticsFee) {
		return super.findList(memberLogisticsFee);
	}
	
	public Page<MemberLogisticsFee> findPage(Page<MemberLogisticsFee> page, MemberLogisticsFee memberLogisticsFee) {
		return super.findPage(page, memberLogisticsFee);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberLogisticsFee memberLogisticsFee) {
		super.save(memberLogisticsFee);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberLogisticsFee memberLogisticsFee) {
		super.delete(memberLogisticsFee);
	}
	
}