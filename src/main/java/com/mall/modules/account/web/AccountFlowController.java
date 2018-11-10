package com.mall.modules.account.web;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.account.entity.AccountFlow;
import com.mall.modules.account.service.AccountFlowService;
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

	@RequiresPermissions("account:accountFlow:view")
	@RequestMapping(value = "form")
	public String form(AccountFlow accountFlow, Model model) {
		model.addAttribute("accountFlow", accountFlow);
		return "modules/account/accountFlowForm";
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

		addMessage(redirectAttributes, "审核成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountFlow/?repage";
	}

}