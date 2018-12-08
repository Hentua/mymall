package com.mall.modules.account.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.common.collect.Lists;
import com.mall.common.config.Global;
import com.mall.common.mapper.JsonMapper;
import com.mall.common.persistence.Page;
import com.mall.common.utils.IdGen;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.excel.ExportExcel;
import com.mall.common.web.BaseController;
import com.mall.modules.account.entity.AccountFlow;
import com.mall.modules.account.service.AccountFlowService;
import com.mall.modules.account.service.AccountService;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.sys.entity.PlatBankAccount;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.service.PlatBankAccountService;
import com.mall.modules.sys.utils.DictUtils;
import com.mall.modules.sys.utils.UserUtils;
import com.sohu.idcenter.IdWorker;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 账户流水Controller
 * @author hub
 * @version 2018-11-10
 */
@Controller
@RequestMapping(value = "${adminPath}/account/accountFlow")
public class AccountFlowController extends BaseController {

	@Autowired
	private AccountFlowService accountFlowService;

	@Autowired
	private MemberInfoService memberInfoService;

	@Autowired
	private AccountService accountService;
	
	@ModelAttribute
	public AccountFlow get(@RequestParam(required=false) String id) {
		AccountFlow entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accountFlowService.get(id);
		}
		if (entity == null){
			entity = new AccountFlow();
		}
		return entity;
	}

	/**
	 * 营运端-充值审核流水
	 * @param accountFlow
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:accountFlow:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccountFlow accountFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
		accountFlow.setType("1");
		accountFlow.setMode("1");
		accountFlow.setIncomeExpenditureMode("2");
		Page<AccountFlow> page = accountFlowService.findPage(new Page<AccountFlow>(request, response), accountFlow); 
		model.addAttribute("page", page);
		return "modules/account/accountFlowList";
	}


	@RequestMapping(value = {"listexportData"})
	public void exportData(AccountFlow accountFlow,  HttpServletRequest request, HttpServletResponse response, Model model) {
		String [] itemIds = request.getParameterValues("itemIds");
		List<AccountFlow> accountFlows;
		if(null != itemIds && itemIds.length > 0) {
			accountFlows = Lists.newArrayList();
			for (String itemId : itemIds) {
				accountFlows.add(this.get(itemId));
			}
		}else {
			accountFlow.setType("1");
			accountFlow.setMode("1");
			accountFlow.setIncomeExpenditureMode("2");
			accountFlows = accountFlowService.findList(accountFlow);
		}

		ExportExcel exportExcel = new ExportExcel("余额充值审核明细", AccountFlow.class);
		try {
			exportExcel.setDataList(accountFlows);
			exportExcel.write(response, "余额充值审核明细.xlsx");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = {"memberList", ""})
	public String memberList(AccountFlow accountFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
//		accountFlow.setType("1");
//		accountFlow.setMode("1");
//		accountFlow.setIncomeExpenditureMode("2");
		accountFlow.setCheckStatus("2");
		Page<AccountFlow> page = accountFlowService.findPage(new Page<AccountFlow>(request, response), accountFlow);
		model.addAttribute("page", page);
		return "modules/account/memberAccountFlowList";
	}

	/**
	 * 商家端流水
	 * @param accountFlow
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"merchantList", ""})
	public String merchantList(AccountFlow accountFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		accountFlow.setUserId(user.getId());
		accountFlow.setCheckStatus("2");
		Page<AccountFlow> page = accountFlowService.findPage(new Page<AccountFlow>(request, response), accountFlow);
		model.addAttribute("page", page);
		model.addAttribute("accountFlow",accountFlow);
		return "modules/account/merchantAccountFlowList";
	}

	@RequiresPermissions("account:accountFlow:view")
	@RequestMapping(value = "form")
	public String form(AccountFlow accountFlow, Model model) {
		model.addAttribute("accountFlow", accountFlow);
		return "modules/account/accountFlowForm";
	}

	@Autowired
	private PlatBankAccountService platBankAccountService;

	/**
	 * 充值页面
	 * @param accountFlow
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "recharge")
	public String recharge(AccountFlow accountFlow, Model model) {
		List<PlatBankAccount> platBankAccountList =platBankAccountService.findList(new PlatBankAccount());
		model.addAttribute("platBankAccountList",platBankAccountList);
		model.addAttribute("platBankAccountListJson", JsonMapper.toJsonString(platBankAccountList));
		return "modules/account/accountRecharge";
	}


	/**
	 * 余额充值流水-商家
	 * @param accountFlow
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "merchantRechargeFlow")
	public String merchantRechargeFlow(AccountFlow accountFlow,HttpServletRequest request, HttpServletResponse response,  Model model) {
		User user = UserUtils.getUser();
		accountFlow.setUserId(user.getId());
		accountFlow.setType("1");
		accountFlow.setMode("1");
		accountFlow.setIncomeExpenditureMode("2");
		Page<AccountFlow> page = accountFlowService.findPage(new Page<AccountFlow>(request, response), accountFlow);
		model.addAttribute("page", page);
		model.addAttribute("accountFlow",accountFlow);
		return "modules/account/merchantRechargeFlow";
	}



	@RequiresPermissions("account:accountFlow:edit")
	@RequestMapping(value = "save")
	public String save(AccountFlow accountFlow, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accountFlow)){
			return form(accountFlow, model);
		}
		accountFlowService.save(accountFlow);
		addMessage(redirectAttributes, "保存账户流水成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountFlow/?repage";
	}
	private static IdWorker idWorker = new IdWorker();

	@RequestMapping(value = "rechargeSave")
	public String rechargeSave(AccountFlow accountFlow, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		accountFlow.setFlowNo(String.valueOf(idWorker.getId()));
//		accountFlow.setId(IdGen.uuid());
		accountFlow.setUserId(user.getId());
		accountFlow.setType("1");//收入
		accountFlow.setMode("1");//充值
		accountFlow.setIncomeExpenditureMode("2");//打款转账
		accountFlow.setCheckStatus("1");
		accountFlow.setCreateDate(new Date());
		accountFlowService.save(accountFlow);
		addMessage(redirectAttributes, "充值记录提交成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountFlow/merchantRechargeFlow?repage";
	}

	
	@RequiresPermissions("account:accountFlow:edit")
	@RequestMapping(value = "delete")
	public String delete(AccountFlow accountFlow, RedirectAttributes redirectAttributes) {
		accountFlowService.delete(accountFlow);
		addMessage(redirectAttributes, "删除账户流水成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountFlow/?repage";
	}

	@RequiresPermissions("account:accountFlow:edit")
	@RequestMapping(value = "check")
	public String check(AccountFlow accountFlow, RedirectAttributes redirectAttributes) {
		AccountFlow ac =  accountFlowService.get(accountFlow);
		ac.setCheckStatus("2");
		accountFlowService.save(ac);
		//新增余额
		MemberInfo m = new  MemberInfo();
		m.setId(ac.getUserId());
		m = memberInfoService.get(m);
		Double balance = null;
		//1：充值，2：佣金转余额）支出（3：提现，4：消费）
		if("1".equals(ac.getMode())){
			balance = m.getBalance()+ac.getAmount();
		}
		if("3".equals(ac.getMode())){
			if(m.getBalance()<ac.getAmount()){
				addMessage(redirectAttributes, "审核失败：提现余额不足");
				return "redirect:"+Global.getAdminPath()+"/account/accountFlow/list?repage";
			}
			balance = m.getBalance()-ac.getAmount();
		}

		accountService.editAccount(balance,null,m.getId());
		addMessage(redirectAttributes, "审核成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountFlow/list?repage";
	}





}