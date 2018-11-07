package com.mall.modules.gift.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.web.BaseController;
import com.mall.common.utils.StringUtils;
import com.mall.modules.gift.entity.GiftConfigCategory;
import com.mall.modules.gift.service.GiftConfigCategoryService;

/**
 * 礼包类别Controller
 * @author wankang
 * @version 2018-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/gift/giftConfigCategory")
public class GiftConfigCategoryController extends BaseController {

	@Autowired
	private GiftConfigCategoryService giftConfigCategoryService;
	
	@ModelAttribute
	public GiftConfigCategory get(@RequestParam(required=false) String id) {
		GiftConfigCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = giftConfigCategoryService.get(id);
		}
		if (entity == null){
			entity = new GiftConfigCategory();
		}
		return entity;
	}
	
	@RequiresPermissions("gift:giftConfigCategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(GiftConfigCategory giftConfigCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GiftConfigCategory> page = giftConfigCategoryService.findPage(new Page<GiftConfigCategory>(request, response), giftConfigCategory); 
		model.addAttribute("page", page);
		return "modules/gift/giftConfigCategoryList";
	}

	@RequiresPermissions("gift:giftConfigCategory:view")
	@RequestMapping(value = "form")
	public String form(GiftConfigCategory giftConfigCategory, Model model) {
		model.addAttribute("giftConfigCategory", giftConfigCategory);
		return "modules/gift/giftConfigCategoryForm";
	}

	@RequiresPermissions("gift:giftConfigCategory:edit")
	@RequestMapping(value = "save")
	public String save(GiftConfigCategory giftConfigCategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, giftConfigCategory)){
			return form(giftConfigCategory, model);
		}
		if (StringUtils.isNotBlank(giftConfigCategory.getMerchantMobile())) {
			User merchantUser = UserUtils.getByLoginName(giftConfigCategory.getMerchantMobile());
			if (null == merchantUser) {
				model.addAttribute("message", "商家不存在");
				return form(giftConfigCategory, model);
			}
		}
		if (StringUtils.isNotBlank(giftConfigCategory.getId())) {
			String remarks = giftConfigCategory.getRemarks();
			giftConfigCategory = this.get(giftConfigCategory.getId());
			giftConfigCategory.setRemarks(remarks);
		}
		giftConfigCategoryService.save(giftConfigCategory);
		addMessage(redirectAttributes, "保存礼包类别成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftConfigCategory/?repage";
	}
	
	@RequiresPermissions("gift:giftConfigCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(GiftConfigCategory giftConfigCategory, RedirectAttributes redirectAttributes) {
		giftConfigCategoryService.delete(giftConfigCategory);
		addMessage(redirectAttributes, "删除礼包类别成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftConfigCategory/?repage";
	}

}