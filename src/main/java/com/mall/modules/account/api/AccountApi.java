package com.mall.modules.account.api;

import com.alibaba.fastjson.JSONObject;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.Result;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.web.BaseController;
import com.mall.modules.account.entity.AccountFlow;
import com.mall.modules.account.service.AccountFlowService;
import com.mall.modules.account.service.AccountService;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import com.sohu.idcenter.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 账户明细Controller
 * @author hub
 * @version 2018-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/api/account")
public class AccountApi extends BaseController {

	private static IdWorker idWorker = new IdWorker();

	@Autowired
	private AccountFlowService accountFlowService;

	@Autowired
	private MemberInfoService memberInfoService;

	@Autowired
	private AccountService accountService;

	/**
	 * 账户余额信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "info", method = RequestMethod.POST)
	public Result info(HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		MemberInfo m = new MemberInfo();
		m.setId(user.getId());
		MemberInfo memberInfo = memberInfoService.get(m);
		JSONObject result = new JSONObject();
		result.put("balance",memberInfo.getBalance());
		result.put("commission",memberInfo.getCommission());
		return ResultGenerator.genSuccessResult(result);
	}


	/**
	 * 充值
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "recharge", method = RequestMethod.POST)
	public Result recharge(HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		String amount = request.getParameter("amount");
		AccountFlow accountFlow = new AccountFlow();
		accountFlow.setFlowNo(String.valueOf(idWorker.getId()));
		accountFlow.setUserId(user.getId());
		accountFlow.setAmount(Double.valueOf(amount));
		accountFlow.setType("1");//收入
		accountFlow.setMode("1");//充值
		accountFlow.setIncomeExpenditureMode(request.getParameter("incomeExpenditureMode"));// 收支方式 1：微信 2：用户转账
		accountFlow.setBankAccount(request.getParameter("bankAccount"));//银行账户
		accountFlow.setBankAccountName(request.getParameter("bankAccountName"));//开户人名称
		accountFlow.setBankName(request.getParameter("bankName"));//开户行
		accountFlow.setCheckStatus("1");
		accountFlowService.save(accountFlow);
		return ResultGenerator.genSuccessResult();
	}


	/**
	 * 提现
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "expenditure", method = RequestMethod.POST)
	public Result expenditure(HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		String amountStr = request.getParameter("amount");
		Double amount = Double.valueOf(amountStr);
		MemberInfo m = new MemberInfo();
		m.setId(user.getId());
		MemberInfo memberInfo = memberInfoService.get(m);
		if(memberInfo.getBalance()<amount){
			throw new ServiceException("账户余额不足");
		}
		AccountFlow accountFlow = new AccountFlow();
		accountFlow.setFlowNo(String.valueOf(idWorker.getId()));
		accountFlow.setUserId(user.getId());
		accountFlow.setAmount(amount);
		accountFlow.setType("2");//支出
		accountFlow.setMode("3");//提现
		accountFlow.setIncomeExpenditureMode(request.getParameter("incomeExpenditureMode"));// 收支方式 1：微信 2：用户转账
		accountFlow.setBankAccount(request.getParameter("bankAccount"));//银行账户
		accountFlow.setBankAccountName(request.getParameter("bankAccountName"));//开户人名称
		accountFlow.setBankName(request.getParameter("bankName"));//开户行
		accountFlow.setCheckStatus("1");
		accountFlowService.save(accountFlow);
		return ResultGenerator.genSuccessResult();
	}


	/**
	 * 佣金转余额
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "CommissionTransferBalance", method = RequestMethod.POST)
	public Result CommissionTransferBalance(HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		MemberInfo m = new MemberInfo();
		m.setId(user.getId());
		MemberInfo memberInfo = memberInfoService.get(m);
		String amountStr = request.getParameter("amount");
		Double amount = Double.valueOf(amountStr);
		if(memberInfo.getCommission()<amount){
			throw new ServiceException("佣金余额不足");
		}
		AccountFlow accountFlow = new AccountFlow();
		accountFlow.setFlowNo(String.valueOf(idWorker.getId()));
		accountFlow.setUserId(user.getId());
		accountFlow.setAmount(amount);
		accountFlow.setType("1");//收入
		accountFlow.setMode("2");//佣金转余额
		accountFlow.setIncomeExpenditureMode(request.getParameter("incomeExpenditureMode"));// 收支方式 1：微信 2：用户转账
		accountFlow.setBankAccount(request.getParameter("bankAccount"));//银行账户
		accountFlow.setBankAccountName(request.getParameter("bankAccountName"));//开户人名称
		accountFlow.setBankName(request.getParameter("bankName"));//开户行
		accountFlow.setCheckStatus("1");
		accountFlowService.save(accountFlow);
		//操作余额
		accountService.editAccount(memberInfo.getBalance()+amount,memberInfo.getCommission()-amount,user.getId());
		return ResultGenerator.genSuccessResult();
	}
}