package com.mall.modules.account.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.account.entity.AccountFlow;

import java.util.Map;

/**
 * 账户流水DAO接口
 * @author hub
 * @version 2018-11-10
 */
@MyBatisDao
public interface AccountFlowDao extends CrudDao<AccountFlow> {


    public void editAccount(Map<String,Object> map );


    public Map<String,Object> stsFlow(AccountFlow accountFlow);
	
}