package com.mall.modules.account.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.account.dao.AccountFlowDao;
import com.mall.modules.account.entity.AccountFlow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 账户流水Service
 * @author hub
 * @version 2018-11-10
 */
@Service
@Transactional(readOnly = true)
public class AccountFlowService extends CrudService<AccountFlowDao, AccountFlow> {

	public AccountFlow get(String id) {
		return super.get(id);
	}
	
	public List<AccountFlow> findList(AccountFlow accountFlow) {
		return super.findList(accountFlow);
	}
	
	public Page<AccountFlow> findPage(Page<AccountFlow> page, AccountFlow accountFlow) {
		return super.findPage(page, accountFlow);
	}

	public Page<AccountFlow> getAccountFlows(AccountFlow accountFlow, Page<AccountFlow> page){
		accountFlow.setPage(page);
		page.setList(findListByApi(accountFlow));
		return page;
	}

	public List<AccountFlow> findListByApi(AccountFlow accountFlow) {
		return super.findList(accountFlow);
	}


	@Transactional(readOnly = false)
	public void save(AccountFlow accountFlow) {
		super.save(accountFlow);
	}
	
	@Transactional(readOnly = false)
	public void delete(AccountFlow accountFlow) {
		super.delete(accountFlow);
	}


	public Map<String,Object> stsFlow(AccountFlow accountFlow){
		return dao.stsFlow(accountFlow);
	}
	
}