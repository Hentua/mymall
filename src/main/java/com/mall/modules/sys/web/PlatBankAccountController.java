package com.mall.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.web.BaseController;
import com.mall.common.utils.StringUtils;
import com.mall.modules.sys.entity.PlatBankAccount;
import com.mall.modules.sys.service.PlatBankAccountService;

/**
 * 平台银行账户管理Controller
 * @author hub
 * @version 2018-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/platBankAccount")
public class PlatBankAccountController extends BaseController {

	@Autowired
	private PlatBankAccountService platBankAccountService;
	
	@ModelAttribute
	public PlatBankAccount get(@RequestParam(required=false) String id) {
		PlatBankAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = platBankAccountService.get(id);
		}
		if (entity == null){
			entity = new PlatBankAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:platBankAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(PlatBankAccount platBankAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PlatBankAccount> page = platBankAccountService.findPage(new Page<PlatBankAccount>(request, response), platBankAccount); 
		model.addAttribute("page", page);
		return "modules/sys/platBankAccountList";
	}

	@RequiresPermissions("sys:platBankAccount:view")
	@RequestMapping(value = "form")
	public String form(PlatBankAccount platBankAccount, Model model) {
		model.addAttribute("platBankAccount", platBankAccount);
		return "modules/sys/platBankAccountForm";
	}

	@RequiresPermissions("sys:platBankAccount:edit")
	@RequestMapping(value = "save")
	public String save(PlatBankAccount platBankAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, platBankAccount)){
			return form(platBankAccount, model);
		}
		platBankAccountService.save(platBankAccount);
		addMessage(redirectAttributes, "保存平台银行账户管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/platBankAccount/?repage";
	}
	
	@RequiresPermissions("sys:platBankAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(PlatBankAccount platBankAccount, RedirectAttributes redirectAttributes) {
		platBankAccountService.delete(platBankAccount);
		addMessage(redirectAttributes, "删除平台银行账户管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/platBankAccount/?repage";
	}

}