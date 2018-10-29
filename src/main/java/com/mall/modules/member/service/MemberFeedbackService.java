package com.mall.modules.member.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.member.dao.MemberFeedbackDao;
import com.mall.modules.member.entity.MemberFeedback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会员反馈信息Service
 * @author wankang
 * @version 2018-10-29
 */
@Service
@Transactional(readOnly = true)
public class MemberFeedbackService extends CrudService<MemberFeedbackDao, MemberFeedback> {

	public MemberFeedback get(String id) {
		return super.get(id);
	}
	
	public List<MemberFeedback> findList(MemberFeedback memberFeedback) {
		return super.findList(memberFeedback);
	}
	
	public Page<MemberFeedback> findPage(Page<MemberFeedback> page, MemberFeedback memberFeedback) {
		return super.findPage(page, memberFeedback);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberFeedback memberFeedback) {
		super.save(memberFeedback);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberFeedback memberFeedback) {
		super.delete(memberFeedback);
	}
	
}