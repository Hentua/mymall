package com.mall.modules.sys.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.sys.entity.PlatBankAccount;

/**
 * 平台银行账户管理DAO接口
 * @author hub
 * @version 2018-11-30
 */
@MyBatisDao
public interface PlatBankAccountDao extends CrudDao<PlatBankAccount> {
	
}