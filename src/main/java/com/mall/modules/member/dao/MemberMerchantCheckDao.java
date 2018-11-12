package com.mall.modules.member.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.member.entity.MemberMerchantCheck;

/**
 * 商户审核DAO接口
 * @author wankang
 * @version 2018-11-12
 */
@MyBatisDao
public interface MemberMerchantCheckDao extends CrudDao<MemberMerchantCheck> {
	
}