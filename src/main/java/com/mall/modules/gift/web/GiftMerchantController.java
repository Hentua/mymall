package com.mall.modules.gift.web;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.gift.entity.GiftMerchant;
import com.mall.modules.gift.service.GiftConfigCategoryService;
import com.mall.modules.gift.service.GiftMerchantService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
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
 * 礼包列表Controller
 * @author wankang
 * @version 2018-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/gift/giftMerchant")
public class GiftMerchantController extends BaseController {

	@Autowired
	private GiftMerchantService giftMerchantService;
	@Autowired
	private GiftConfigCategoryService giftConfigCategoryService;
	
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
		User currUser = UserUtils.getUser();
		giftMerchant.setMerchantCode(currUser.getId());
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

	@RequiresPermissions("gift:giftMerchant:view")
	@RequestMapping(value = "giftTransferForm")
	public String giftTransferForm(GiftMerchant giftMerchant, Model model) {
		giftMerchant = this.get(giftMerchant.getId());
		giftMerchant.setGiftConfigCategory(giftConfigCategoryService.get(giftMerchant.getGiftCategory()));
		model.addAttribute("giftMerchant", giftMerchant);
		return "modules/gift/giftMerchantTransfer";
	}

	@RequiresPermissions("gift:giftMerchant:view")
	@RequestMapping(value = "giftTransfer")
	public String giftTransfer(GiftMerchant giftMerchant, Model model, RedirectAttributes redirectAttributes) {
		String id = giftMerchant.getId();
		if(StringUtils.isBlank(id)) {
			addMessage(redirectAttributes, "未选择要赠送的礼包");
			return "redirect:" + Global.getAdminPath() + "modules/gift/giftMerchant?repage";
		}
		String customerMobile = giftMerchant.getCustomerMobile();
		giftMerchant = this.get(id);
		if(null == giftMerchant) {
			addMessage(redirectAttributes, "礼包不存在");
			return "redirect:" + Global.getAdminPath() + "modules/gift/giftMerchant?repage";
		}
		giftMerchant.setGiftConfigCategory(giftConfigCategoryService.get(giftMerchant.getGiftCategory()));
		giftMerchant.setCustomerMobile(customerMobile);
		if(StringUtils.isBlank(customerMobile)) {
			model.addAttribute("message", "请输入要赠送会员的手机号");
			return giftTransferForm(giftMerchant, model);
		}
		User customerUser = UserUtils.getByLoginName(customerMobile);
		if(null == customerUser) {
			model.addAttribute("message", "会员不存在");
			return giftTransferForm(giftMerchant, model);
		}
		try {
			giftMerchantService.giftTransfer(giftMerchant);
		}catch (Exception e) {
			e.printStackTrace();
			if(e instanceof ServiceException) {
				model.addAttribute("message", e.getMessage());
				return giftTransferForm(giftMerchant, model);
			}
		}
		addMessage(redirectAttributes, "赠送成功");
		return "redirect:" + Global.getAdminPath() + "modules/gift/giftMerchant?repage";
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