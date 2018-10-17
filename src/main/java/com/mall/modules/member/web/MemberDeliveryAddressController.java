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
import com.mall.modules.member.entity.MemberDeliveryAddress;
import com.mall.modules.member.service.MemberDeliveryAddressService;

/**
 * 收货地址Controller
 * @author wankang
 * @version 2018-10-17
 */
@Controller
@RequestMapping(value = "${adminPath}/member/memberDeliveryAddress")
public class MemberDeliveryAddressController extends BaseController {

	@Autowired
	private MemberDeliveryAddressService memberDeliveryAddressService;
	
	@ModelAttribute
	public MemberDeliveryAddress get(@RequestParam(required=false) String id) {
		MemberDeliveryAddress entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberDeliveryAddressService.get(id);
		}
		if (entity == null){
			entity = new MemberDeliveryAddress();
		}
		return entity;
	}
	
	@RequiresPermissions("member:memberDeliveryAddress:view")
	@RequestMapping(value = {"list", ""})
	public String list(MemberDeliveryAddress memberDeliveryAddress, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberDeliveryAddress> page = memberDeliveryAddressService.findPage(new Page<MemberDeliveryAddress>(request, response), memberDeliveryAddress); 
		model.addAttribute("page", page);
		return "modules/member/memberDeliveryAddressList";
	}

	@RequiresPermissions("member:memberDeliveryAddress:view")
	@RequestMapping(value = "form")
	public String form(MemberDeliveryAddress memberDeliveryAddress, Model model) {
		model.addAttribute("memberDeliveryAddress", memberDeliveryAddress);
		return "modules/member/memberDeliveryAddressForm";
	}

	@RequiresPermissions("member:memberDeliveryAddress:edit")
	@RequestMapping(value = "save")
	public String save(MemberDeliveryAddress memberDeliveryAddress, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, memberDeliveryAddress)){
			return form(memberDeliveryAddress, model);
		}
		memberDeliveryAddressService.save(memberDeliveryAddress);
		addMessage(redirectAttributes, "保存收货地址成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberDeliveryAddress/?repage";
	}
	
	@RequiresPermissions("member:memberDeliveryAddress:edit")
	@RequestMapping(value = "delete")
	public String delete(MemberDeliveryAddress memberDeliveryAddress, RedirectAttributes redirectAttributes) {
		memberDeliveryAddressService.delete(memberDeliveryAddress);
		addMessage(redirectAttributes, "删除收货地址成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberDeliveryAddress/?repage";
	}

}