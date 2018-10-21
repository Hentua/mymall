package com.mall.modules.account.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.account.entity.AccountInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 账户明细DAO接口
 * @author hub
 * @version 2018-10-21
 */
@MyBatisDao
public interface AccountInfoDao extends CrudDao<AccountInfo> {

    public List<AccountInfo> findListByApi(AccountInfo accountInfo);

    public AccountInfo getByApi(AccountInfo accountInfo);

    public Map<String,Object> getStsInfo(AccountInfo accountInfo);

    public void toAccount(@Param("orderId") String orderId);
	
}