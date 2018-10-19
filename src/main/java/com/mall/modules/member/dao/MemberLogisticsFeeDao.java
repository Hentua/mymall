package com.mall.modules.member.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.member.entity.MemberLogisticsFee;

/**
 * 运费定义DAO接口
 * @author wankang
 * @version 2018-10-19
 */
@MyBatisDao
public interface MemberLogisticsFeeDao extends CrudDao<MemberLogisticsFee> {
	
}