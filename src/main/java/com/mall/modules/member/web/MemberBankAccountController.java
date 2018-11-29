package com.mall.modules.member.web;

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
import com.mall.modules.member.entity.MemberBankAccount;
import com.mall.modules.member.service.MemberBankAccountService;

/**
 * 用户银行卡Controller
 * @author hub
 * @version 2018-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/member/memberBankAccount")
public class MemberBankAccountController extends BaseController {

	@Autowired
	private MemberBankAccountService memberBankAccountService;
	
	@ModelAttribute
	public MemberBankAccount get(@RequestParam(required=false) String id) {
		MemberBankAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberBankAccountService.get(id);
		}
		if (entity == null){
			entity = new MemberBankAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("member:memberBankAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(MemberBankAccount memberBankAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberBankAccount> page = memberBankAccountService.findPage(new Page<MemberBankAccount>(request, response), memberBankAccount); 
		model.addAttribute("page", page);
		return "modules/member/memberBankAccountList";
	}

	@RequiresPermissions("member:memberBankAccount:view")
	@RequestMapping(value = "form")
	public String form(MemberBankAccount memberBankAccount, Model model) {
		model.addAttribute("memberBankAccount", memberBankAccount);
		return "modules/member/memberBankAccountForm";
	}

	@RequiresPermissions("member:memberBankAccount:edit")
	@RequestMapping(value = "save")
	public String save(MemberBankAccount memberBankAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, memberBankAccount)){
			return form(memberBankAccount, model);
		}
		memberBankAccountService.save(memberBankAccount);
		addMessage(redirectAttributes, "保存用户银行卡成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberBankAccount/?repage";
	}
	
	@RequiresPermissions("member:memberBankAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(MemberBankAccount memberBankAccount, RedirectAttributes redirectAttributes) {
		memberBankAccountService.delete(memberBankAccount);
		addMessage(redirectAttributes, "删除用户银行卡成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberBankAccount/?repage";
	}

}