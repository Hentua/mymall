package com.mall.modules.member.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.member.entity.MemberRefereeIdMax;

/**
 * 最大推荐ID编号DAO接口
 * @author wankang
 * @version 2018-11-25
 */
@MyBatisDao
public interface MemberRefereeIdMaxDao extends CrudDao<MemberRefereeIdMax> {
	
}