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
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;

/**
 * 用户信息Controller
 * @author wankang
 * @version 2018-10-10
 */
@Controller
@RequestMapping(value = "${adminPath}/member/memberInfo")
public class MemberInfoController extends BaseController {

	@Autowired
	private MemberInfoService memberInfoService;
	
	@ModelAttribute
	public MemberInfo get(@RequestParam(required=false) String id) {
		MemberInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberInfoService.get(id);
		}
		if (entity == null){
			entity = new MemberInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("member:memberInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberInfo> page = memberInfoService.findPage(new Page<MemberInfo>(request, response), memberInfo); 
		model.addAttribute("page", page);
		return "modules/member/memberInfoList";
	}

	@RequiresPermissions("member:memberInfo:view")
	@RequestMapping(value = "form")
	public String form(MemberInfo memberInfo, Model model) {
		model.addAttribute("memberInfo", memberInfo);
		return "modules/member/memberInfoForm";
	}

	@RequiresPermissions("member:memberInfo:edit")
	@RequestMapping(value = "save")
	public String save(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, memberInfo)){
			return form(memberInfo, model);
		}
		memberInfoService.save(memberInfo);
		addMessage(redirectAttributes, "保存用户信息成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberInfo/?repage";
	}
	
	@RequiresPermissions("member:memberInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(MemberInfo memberInfo, RedirectAttributes redirectAttributes) {
		memberInfoService.delete(memberInfo);
		addMessage(redirectAttributes, "删除用户信息成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberInfo/?repage";
	}

}