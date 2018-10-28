package com.mall.modules.gift.web;

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
import com.mall.modules.gift.entity.GiftCustomer;
import com.mall.modules.gift.service.GiftCustomerService;

/**
 * 会员礼包Controller
 * @author wankang
 * @version 2018-10-28
 */
@Controller
@RequestMapping(value = "${adminPath}/gift/giftCustomer")
public class GiftCustomerController extends BaseController {

	@Autowired
	private GiftCustomerService giftCustomerService;
	
	@ModelAttribute
	public GiftCustomer get(@RequestParam(required=false) String id) {
		GiftCustomer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = giftCustomerService.get(id);
		}
		if (entity == null){
			entity = new GiftCustomer();
		}
		return entity;
	}
	
	@RequiresPermissions("gift:giftCustomer:view")
	@RequestMapping(value = {"list", ""})
	public String list(GiftCustomer giftCustomer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GiftCustomer> page = giftCustomerService.findPage(new Page<GiftCustomer>(request, response), giftCustomer); 
		model.addAttribute("page", page);
		return "modules/gift/giftCustomerList";
	}

	@RequiresPermissions("gift:giftCustomer:view")
	@RequestMapping(value = "form")
	public String form(GiftCustomer giftCustomer, Model model) {
		model.addAttribute("giftCustomer", giftCustomer);
		return "modules/gift/giftCustomerForm";
	}

	@RequiresPermissions("gift:giftCustomer:edit")
	@RequestMapping(value = "save")
	public String save(GiftCustomer giftCustomer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, giftCustomer)){
			return form(giftCustomer, model);
		}
		giftCustomerService.save(giftCustomer);
		addMessage(redirectAttributes, "保存会员礼包成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftCustomer/?repage";
	}
	
	@RequiresPermissions("gift:giftCustomer:edit")
	@RequestMapping(value = "delete")
	public String delete(GiftCustomer giftCustomer, RedirectAttributes redirectAttributes) {
		giftCustomerService.delete(giftCustomer);
		addMessage(redirectAttributes, "删除会员礼包成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftCustomer/?repage";
	}

}