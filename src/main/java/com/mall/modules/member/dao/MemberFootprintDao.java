package com.mall.modules.member.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.member.entity.MemberFootprint;

/**
 * 会员足迹DAO接口
 * @author wankang
 * @version 2018-10-29
 */
@MyBatisDao
public interface MemberFootprintDao extends CrudDao<MemberFootprint> {
	
}