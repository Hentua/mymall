package com.mall.modules.account.service;

import com.mall.common.service.CrudService;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.IdGen;
import com.mall.modules.account.dao.AccountFlowDao;
import com.mall.modules.account.entity.AccountFlow;
import com.mall.modules.member.dao.MemberInfoDao;
import com.mall.modules.member.entity.MemberInfo;
import com.sohu.idcenter.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 账户Service
 * @author hub
 * @version 2018-11-10
 */
@Service
@Transactional(readOnly = true)
public class AccountService extends CrudService<AccountFlowDao, AccountFlow> {

	private static IdWorker idWorker = new IdWorker();

	@Autowired
	private AccountFlowDao accountFlowDao;

	@Autowired
	private MemberInfoDao memberInfoDao;

	/**
	 * 修改账户余额
	 * @param balance
	 * @param commission
	 * @param userId
	 */
	@Transactional(readOnly = false)
	public void editAccount(Double balance,Double commission,String userId){
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("balance",balance);
		paramMap.put("commission",commission);
		paramMap.put("userId",userId);
		dao.editAccount(paramMap);
	}


	/**
	 * 余额消费
	 * @param amount
	 * @param paymentNo
	 * @param userId
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void consumption(Double amount,String paymentNo,String userId){

		MemberInfo memberInfo = memberInfoDao.get(userId);
		if(null == memberInfo){
			throw new ServiceException("账户信息不存在");
		}
		if(amount > memberInfo.getBalance()){
			throw new ServiceException("账户余额不足");
		}

		//创建消费流水
		AccountFlow accountFlow = new AccountFlow();
		accountFlow.setFlowNo(String.valueOf(idWorker.getId()));
		accountFlow.setId(IdGen.uuid());
		accountFlow.setUserId(userId);
		accountFlow.setAmount(amount);
		accountFlow.setType("2");//支出
		accountFlow.setMode("4");//消费
		accountFlow.setOrderId(paymentNo);
		accountFlow.setCheckStatus("2");
		accountFlow.setCreateDate(new Date());
		accountFlowDao.insert(accountFlow);

		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("balance",memberInfo.getBalance() - amount);
		paramMap.put("commission",null);
		paramMap.put("userId",userId);
		dao.editAccount(paramMap);
	}
}