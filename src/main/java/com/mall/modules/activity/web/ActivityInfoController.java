package com.mall.modules.activity.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.common.utils.DateUtils;
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
import com.mall.modules.activity.entity.ActivityInfo;
import com.mall.modules.activity.service.ActivityInfoService;

/**
 * 活动配置Controller
 * @author wankang
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/activity/activityInfo")
public class ActivityInfoController extends BaseController {

	@Autowired
	private ActivityInfoService activityInfoService;
	
	@ModelAttribute
	public ActivityInfo get(@RequestParam(required=false) String id) {
		ActivityInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = activityInfoService.get(id);
		}
		if (entity == null){
			entity = new ActivityInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("activity:activityInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ActivityInfo activityInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ActivityInfo> page = activityInfoService.findPage(new Page<ActivityInfo>(request, response), activityInfo); 
		model.addAttribute("page", page);
		return "modules/activity/activityInfoList";
	}

	@RequiresPermissions("activity:activityInfo:view")
	@RequestMapping(value = "form")
	public String form(ActivityInfo activityInfo, Model model) {
		model.addAttribute("activityInfo", activityInfo);
		return "modules/activity/activityInfoForm";
	}

	@RequiresPermissions("activity:activityInfo:edit")
	@RequestMapping(value = "save")
	public String save(ActivityInfo activityInfo, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, activityInfo)){
			return form(activityInfo, model);
		}
		activityInfo.setEndDate(DateUtils.getEndOfDay(activityInfo.getEndDate()));
		activityInfo.setStartDate(DateUtils.getStartOfDay(activityInfo.getStartDate()));
		if(activityInfo.getEndDate().getTime() <= activityInfo.getStartDate().getTime()) {
			model.addAttribute("message", "结束时间不能小于开始时间");
			return form(activityInfo, model);
		}
		activityInfoService.save(activityInfo);
		addMessage(redirectAttributes, "保存活动配置成功");
		return "redirect:"+Global.getAdminPath()+"/activity/activityInfo/?repage";
	}
	
	@RequiresPermissions("activity:activityInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ActivityInfo activityInfo, RedirectAttributes redirectAttributes) {
		activityInfo = this.get(activityInfo.getId());
		if(!"0".equals(activityInfo.getStatus())) {
			addMessage(redirectAttributes, "删除活动配置失败，请先下线活动后删除");
			return "redirect:"+Global.getAdminPath()+"/activity/activityInfo/?repage";
		}
		activityInfoService.delete(activityInfo);
		addMessage(redirectAttributes, "删除活动配置成功");
		return "redirect:"+Global.getAdminPath()+"/activity/activityInfo/?repage";
	}

}