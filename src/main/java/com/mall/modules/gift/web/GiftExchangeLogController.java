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
import com.mall.modules.gift.entity.GiftExchangeLog;
import com.mall.modules.gift.service.GiftExchangeLogService;

/**
 * 礼包兑换记录Controller
 * @author wankang
 * @version 2018-10-28
 */
@Controller
@RequestMapping(value = "${adminPath}/gift/giftExchangeLog")
public class GiftExchangeLogController extends BaseController {

	@Autowired
	private GiftExchangeLogService giftExchangeLogService;
	
	@ModelAttribute
	public GiftExchangeLog get(@RequestParam(required=false) String id) {
		GiftExchangeLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = giftExchangeLogService.get(id);
		}
		if (entity == null){
			entity = new GiftExchangeLog();
		}
		return entity;
	}
	
	@RequiresPermissions("gift:giftExchangeLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(GiftExchangeLog giftExchangeLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GiftExchangeLog> page = giftExchangeLogService.findPage(new Page<GiftExchangeLog>(request, response), giftExchangeLog); 
		model.addAttribute("page", page);
		return "modules/gift/giftExchangeLogList";
	}

	@RequiresPermissions("gift:giftExchangeLog:view")
	@RequestMapping(value = "form")
	public String form(GiftExchangeLog giftExchangeLog, Model model) {
		model.addAttribute("giftExchangeLog", giftExchangeLog);
		return "modules/gift/giftExchangeLogForm";
	}

	@RequiresPermissions("gift:giftExchangeLog:edit")
	@RequestMapping(value = "save")
	public String save(GiftExchangeLog giftExchangeLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, giftExchangeLog)){
			return form(giftExchangeLog, model);
		}
		giftExchangeLogService.save(giftExchangeLog);
		addMessage(redirectAttributes, "保存礼包兑换记录成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftExchangeLog/?repage";
	}
	
	@RequiresPermissions("gift:giftExchangeLog:edit")
	@RequestMapping(value = "delete")
	public String delete(GiftExchangeLog giftExchangeLog, RedirectAttributes redirectAttributes) {
		giftExchangeLogService.delete(giftExchangeLog);
		addMessage(redirectAttributes, "删除礼包兑换记录成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftExchangeLog/?repage";
	}

}