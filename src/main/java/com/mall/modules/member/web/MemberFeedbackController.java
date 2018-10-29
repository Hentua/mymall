package com.mall.modules.member.web;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.member.entity.MemberFeedback;
import com.mall.modules.member.service.MemberFeedbackService;
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
 * 会员反馈信息Controller
 * @author wankang
 * @version 2018-10-29
 */
@Controller
@RequestMapping(value = "${adminPath}/member/memberFeedback")
public class MemberFeedbackController extends BaseController {

	@Autowired
	private MemberFeedbackService memberFeedbackService;
	
	@ModelAttribute
	public MemberFeedback get(@RequestParam(required=false) String id) {
		MemberFeedback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberFeedbackService.get(id);
		}
		if (entity == null){
			entity = new MemberFeedback();
		}
		return entity;
	}
	
	@RequiresPermissions("member:memberFeedback:view")
	@RequestMapping(value = {"list", ""})
	public String list(MemberFeedback memberFeedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberFeedback> page = memberFeedbackService.findPage(new Page<MemberFeedback>(request, response), memberFeedback); 
		model.addAttribute("page", page);
		return "modules/member/memberFeedbackList";
	}

	@RequiresPermissions("member:memberFeedback:view")
	@RequestMapping(value = "form")
	public String form(MemberFeedback memberFeedback, Model model) {
		model.addAttribute("memberFeedback", memberFeedback);
		return "modules/member/memberFeedbackForm";
	}

	@RequiresPermissions("member:memberFeedback:edit")
	@RequestMapping(value = "save")
	public String save(MemberFeedback memberFeedback, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, memberFeedback)){
			return form(memberFeedback, model);
		}
		memberFeedbackService.save(memberFeedback);
		addMessage(redirectAttributes, "保存会员反馈信息成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberFeedback/?repage";
	}
	
	@RequiresPermissions("member:memberFeedback:edit")
	@RequestMapping(value = "delete")
	public String delete(MemberFeedback memberFeedback, RedirectAttributes redirectAttributes) {
		memberFeedbackService.delete(memberFeedback);
		addMessage(redirectAttributes, "删除会员反馈信息成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberFeedback/?repage";
	}

}