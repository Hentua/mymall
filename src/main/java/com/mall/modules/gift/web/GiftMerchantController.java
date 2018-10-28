package com.mall.modules.gift.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.modules.sys.utils.UserUtils;
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
import com.mall.modules.gift.entity.GiftMerchant;
import com.mall.modules.gift.service.GiftMerchantService;

/**
 * 礼包列表Controller
 * @author wankang
 * @version 2018-10-28
 */
@Controller
@RequestMapping(value = "${adminPath}/gift/giftMerchant")
public class GiftMerchantController extends BaseController {

	@Autowired
	private GiftMerchantService giftMerchantService;
	
	@ModelAttribute
	public GiftMerchant get(@RequestParam(required=false) String id) {
		GiftMerchant entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = giftMerchantService.get(id);
		}
		if (entity == null){
			entity = new GiftMerchant();
		}
		return entity;
	}
	
	@RequiresPermissions("gift:giftMerchant:view")
	@RequestMapping(value = {"list", ""})
	public String list(GiftMerchant giftMerchant, HttpServletRequest request, HttpServletResponse response, Model model) {
		giftMerchant.setMerchantCode(UserUtils.getUser().getId());
		Page<GiftMerchant> page = giftMerchantService.findPage(new Page<GiftMerchant>(request, response), giftMerchant); 
		model.addAttribute("page", page);
		return "modules/gift/giftMerchantList";
	}

	@RequiresPermissions("gift:giftMerchant:view")
	@RequestMapping(value = "form")
	public String form(GiftMerchant giftMerchant, Model model) {
		model.addAttribute("giftMerchant", giftMerchant);
		return "modules/gift/giftMerchantForm";
	}

	@RequiresPermissions("gift:giftMerchant:edit")
	@RequestMapping(value = "save")
	public String save(GiftMerchant giftMerchant, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, giftMerchant)){
			return form(giftMerchant, model);
		}
		giftMerchantService.save(giftMerchant);
		addMessage(redirectAttributes, "保存礼包列表成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftMerchant/?repage";
	}
	
	@RequiresPermissions("gift:giftMerchant:edit")
	@RequestMapping(value = "delete")
	public String delete(GiftMerchant giftMerchant, RedirectAttributes redirectAttributes) {
		giftMerchantService.delete(giftMerchant);
		addMessage(redirectAttributes, "删除礼包列表成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftMerchant/?repage";
	}

}