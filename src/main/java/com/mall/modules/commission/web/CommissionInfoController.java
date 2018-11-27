package com.mall.modules.commission.web;

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
import com.mall.modules.commission.entity.CommissionInfo;
import com.mall.modules.commission.service.CommissionInfoService;

/**
 * 佣金明细Controller
 * @author hub
 * @version 2018-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/commission/commissionInfo")
public class CommissionInfoController extends BaseController {

	@Autowired
	private CommissionInfoService commissionInfoService;
	
	@ModelAttribute
	public CommissionInfo get(@RequestParam(required=false) String id) {
		CommissionInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = commissionInfoService.get(id);
		}
		if (entity == null){
			entity = new CommissionInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("commission:commissionInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CommissionInfo commissionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CommissionInfo> page = commissionInfoService.findPage(new Page<CommissionInfo>(request, response), commissionInfo); 
		model.addAttribute("page", page);
		return "modules/commission/commissionInfoList";
	}
	@RequestMapping(value = {"merchantList", ""})
	public String merchantList(CommissionInfo commissionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		commissionInfo.setUserId(user.getId());
		Page<CommissionInfo> page = commissionInfoService.findPage(new Page<CommissionInfo>(request, response), commissionInfo);
		model.addAttribute("page", page);
		model.addAttribute("commissionInfo",commissionInfo);
		return "modules/commission/merchantCommissionInfoList";
	}



	@RequiresPermissions("commission:commissionInfo:view")
	@RequestMapping(value = "form")
	public String form(CommissionInfo commissionInfo, Model model) {
		model.addAttribute("commissionInfo", commissionInfo);
		return "modules/commission/commissionInfoForm";
	}

	@RequiresPermissions("commission:commissionInfo:edit")
	@RequestMapping(value = "save")
	public String save(CommissionInfo commissionInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, commissionInfo)){
			return form(commissionInfo, model);
		}
		commissionInfoService.save(commissionInfo);
		addMessage(redirectAttributes, "保存佣金明细成功");
		return "redirect:"+Global.getAdminPath()+"/commission/commissionInfo/?repage";
	}
	
	@RequiresPermissions("commission:commissionInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CommissionInfo commissionInfo, RedirectAttributes redirectAttributes) {
		commissionInfoService.delete(commissionInfo);
		addMessage(redirectAttributes, "删除佣金明细成功");
		return "redirect:"+Global.getAdminPath()+"/commission/commissionInfo/?repage";
	}

}