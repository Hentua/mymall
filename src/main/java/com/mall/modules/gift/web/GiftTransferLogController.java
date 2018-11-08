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
import com.mall.modules.gift.entity.GiftTransferLog;
import com.mall.modules.gift.service.GiftTransferLogService;

/**
 * 礼包赠送记录Controller
 * @author wankang
 * @version 2018-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/gift/giftTransferLog")
public class GiftTransferLogController extends BaseController {

	@Autowired
	private GiftTransferLogService giftTransferLogService;
	
	@ModelAttribute
	public GiftTransferLog get(@RequestParam(required=false) String id) {
		GiftTransferLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = giftTransferLogService.get(id);
		}
		if (entity == null){
			entity = new GiftTransferLog();
		}
		return entity;
	}
	
	@RequiresPermissions("gift:giftTransferLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(GiftTransferLog giftTransferLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GiftTransferLog> page = giftTransferLogService.findPage(new Page<GiftTransferLog>(request, response), giftTransferLog); 
		model.addAttribute("page", page);
		return "modules/gift/giftTransferLogList";
	}

	@RequiresPermissions("gift:giftTransferLog:view")
	@RequestMapping(value = "form")
	public String form(GiftTransferLog giftTransferLog, Model model) {
		model.addAttribute("giftTransferLog", giftTransferLog);
		return "modules/gift/giftTransferLogForm";
	}

	@RequiresPermissions("gift:giftTransferLog:edit")
	@RequestMapping(value = "save")
	public String save(GiftTransferLog giftTransferLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, giftTransferLog)){
			return form(giftTransferLog, model);
		}
		giftTransferLogService.save(giftTransferLog);
		addMessage(redirectAttributes, "保存礼包赠送记录成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftTransferLog/?repage";
	}
	
	@RequiresPermissions("gift:giftTransferLog:edit")
	@RequestMapping(value = "delete")
	public String delete(GiftTransferLog giftTransferLog, RedirectAttributes redirectAttributes) {
		giftTransferLogService.delete(giftTransferLog);
		addMessage(redirectAttributes, "删除礼包赠送记录成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftTransferLog/?repage";
	}

}