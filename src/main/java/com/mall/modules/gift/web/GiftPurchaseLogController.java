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
import com.mall.modules.gift.entity.GiftPurchaseLog;
import com.mall.modules.gift.service.GiftPurchaseLogService;

/**
 * 礼包购买记录Controller
 * @author wankang
 * @version 2018-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/gift/giftPurchaseLog")
public class GiftPurchaseLogController extends BaseController {

	@Autowired
	private GiftPurchaseLogService giftPurchaseLogService;
	
	@ModelAttribute
	public GiftPurchaseLog get(@RequestParam(required=false) String id) {
		GiftPurchaseLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = giftPurchaseLogService.get(id);
		}
		if (entity == null){
			entity = new GiftPurchaseLog();
		}
		return entity;
	}
	
	@RequiresPermissions("gift:giftPurchaseLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(GiftPurchaseLog giftPurchaseLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GiftPurchaseLog> page = giftPurchaseLogService.findPage(new Page<GiftPurchaseLog>(request, response), giftPurchaseLog); 
		model.addAttribute("page", page);
		return "modules/gift/giftPurchaseLogList";
	}

	@RequiresPermissions("gift:giftPurchaseLog:view")
	@RequestMapping(value = "form")
	public String form(GiftPurchaseLog giftPurchaseLog, Model model) {
		model.addAttribute("giftPurchaseLog", giftPurchaseLog);
		return "modules/gift/giftPurchaseLogForm";
	}

	@RequiresPermissions("gift:giftPurchaseLog:edit")
	@RequestMapping(value = "save")
	public String save(GiftPurchaseLog giftPurchaseLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, giftPurchaseLog)){
			return form(giftPurchaseLog, model);
		}
		giftPurchaseLogService.save(giftPurchaseLog);
		addMessage(redirectAttributes, "保存礼包购买记录成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftPurchaseLog/?repage";
	}
	
	@RequiresPermissions("gift:giftPurchaseLog:edit")
	@RequestMapping(value = "delete")
	public String delete(GiftPurchaseLog giftPurchaseLog, RedirectAttributes redirectAttributes) {
		giftPurchaseLogService.delete(giftPurchaseLog);
		addMessage(redirectAttributes, "删除礼包购买记录成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftPurchaseLog/?repage";
	}

}