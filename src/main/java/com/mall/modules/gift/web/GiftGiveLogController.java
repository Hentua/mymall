package com.mall.modules.gift.web;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.gift.entity.GiftGiveLog;
import com.mall.modules.gift.service.GiftGiveLogService;
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
 * 礼包赠送记录Controller
 * @author wankang
 * @version 2018-10-28
 */
@Controller
@RequestMapping(value = "${adminPath}/gift/giftGiveLog")
public class GiftGiveLogController extends BaseController {

	@Autowired
	private GiftGiveLogService giftGiveLogService;
	
	@ModelAttribute
	public GiftGiveLog get(@RequestParam(required=false) String id) {
		GiftGiveLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = giftGiveLogService.get(id);
		}
		if (entity == null){
			entity = new GiftGiveLog();
		}
		return entity;
	}
	
	@RequiresPermissions("gift:giftGiveLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(GiftGiveLog giftGiveLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		giftGiveLog.setMerchantCode(UserUtils.getUser().getId());
		Page<GiftGiveLog> page = giftGiveLogService.findPage(new Page<GiftGiveLog>(request, response), giftGiveLog); 
		model.addAttribute("page", page);
		return "modules/gift/giftGiveLogList";
	}

	@RequiresPermissions("gift:giftGiveLog:view")
	@RequestMapping(value = "form")
	public String form(GiftGiveLog giftGiveLog, Model model) {
		model.addAttribute("giftGiveLog", giftGiveLog);
		return "modules/gift/giftGiveLogForm";
	}

	@RequiresPermissions("gift:giftGiveLog:edit")
	@RequestMapping(value = "save")
	public String save(GiftGiveLog giftGiveLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, giftGiveLog)){
			return form(giftGiveLog, model);
		}
		giftGiveLogService.save(giftGiveLog);
		addMessage(redirectAttributes, "保存礼包赠送记录成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftGiveLog/?repage";
	}
	
	@RequiresPermissions("gift:giftGiveLog:edit")
	@RequestMapping(value = "delete")
	public String delete(GiftGiveLog giftGiveLog, RedirectAttributes redirectAttributes) {
		giftGiveLogService.delete(giftGiveLog);
		addMessage(redirectAttributes, "删除礼包赠送记录成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftGiveLog/?repage";
	}

}