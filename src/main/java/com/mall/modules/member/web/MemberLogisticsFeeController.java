package com.mall.modules.member.web;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.member.entity.MemberLogisticsFee;
import com.mall.modules.member.service.MemberLogisticsFeeService;
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
 * 运费定义Controller
 * @author wankang
 * @version 2018-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/member/memberLogisticsFee")
public class MemberLogisticsFeeController extends BaseController {

	@Autowired
	private MemberLogisticsFeeService memberLogisticsFeeService;
	
	@ModelAttribute
	public MemberLogisticsFee get(@RequestParam(required=false) String id) {
		MemberLogisticsFee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberLogisticsFeeService.get(id);
		}
		if (entity == null){
			entity = new MemberLogisticsFee();
		}
		return entity;
	}
	
	@RequiresPermissions("member:memberLogisticsFee:view")
	@RequestMapping(value = {"list", ""})
	public String list(MemberLogisticsFee memberLogisticsFee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberLogisticsFee> page = memberLogisticsFeeService.findPage(new Page<MemberLogisticsFee>(request, response), memberLogisticsFee); 
		model.addAttribute("page", page);
		return "modules/member/memberLogisticsFeeList";
	}

	@RequiresPermissions("member:memberLogisticsFee:view")
	@RequestMapping(value = "form")
	public String form(MemberLogisticsFee memberLogisticsFee, Model model) {
		model.addAttribute("memberLogisticsFee", memberLogisticsFee);
		return "modules/member/memberLogisticsFeeForm";
	}

	@RequiresPermissions("member:memberLogisticsFee:edit")
	@RequestMapping(value = "save")
	public String save(MemberLogisticsFee memberLogisticsFee, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, memberLogisticsFee)){
			return form(memberLogisticsFee, model);
		}
		memberLogisticsFeeService.save(memberLogisticsFee);
		addMessage(redirectAttributes, "保存运费定义成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberLogisticsFee/?repage";
	}
	
	@RequiresPermissions("member:memberLogisticsFee:edit")
	@RequestMapping(value = "delete")
	public String delete(MemberLogisticsFee memberLogisticsFee, RedirectAttributes redirectAttributes) {
		memberLogisticsFeeService.delete(memberLogisticsFee);
		addMessage(redirectAttributes, "删除运费定义成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberLogisticsFee/?repage";
	}

}