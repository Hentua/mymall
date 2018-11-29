package com.mall.modules.account.web;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.IdGen;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.account.entity.AccountFlow;
import com.mall.modules.account.service.AccountFlowService;
import com.mall.modules.account.service.AccountService;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.sys.entity.User;
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
	
	@RequiresPermissions("account:accountFlow:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccountFlow accountFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccountFlow> page = accountFlowService.findPage(new Page<AccountFlow>(request, response), accountFlow); 
		model.addAttribute("page", page);
		return "modules/account/accountFlowList";
	}

	@RequestMapping(value = {"merchantList", ""})
	public String merchantList(AccountFlow accountFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		accountFlow.setUserId(user.getId());
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
	@RequestMapping(value = "recharge")
	public String recharge(AccountFlow accountFlow, Model model) {


		return "modules/account/accountRecharge";
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
		if (!beanValidator(model, accountFlow)){
			return form(accountFlow, model);
		}
		User user = UserUtils.getUser();
		accountFlow.setFlowNo(String.valueOf(idWorker.getId()));
		accountFlow.setId(IdGen.uuid());
		accountFlow.setUserId(user.getId());
		accountFlow.setType("1");//收入
		accountFlow.setMode("1");//消费
		accountFlow.setCheckStatus("1");
		accountFlow.setCreateDate(new Date());
		accountFlowService.save(accountFlow);
		addMessage(redirectAttributes, "充值记录提交成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountFlow/?repage";
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