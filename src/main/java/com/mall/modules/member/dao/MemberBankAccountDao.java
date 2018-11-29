package com.mall.modules.member.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.member.entity.MemberBankAccount;

/**
 * 用户银行卡DAO接口
 * @author hub
 * @version 2018-11-29
 */
@MyBatisDao
public interface MemberBankAccountDao extends CrudDao<MemberBankAccount> {
	
}