package com.mall.modules.commission.web;

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
import com.mall.modules.commission.entity.CommissionConfig;
import com.mall.modules.commission.service.CommissionConfigService;

/**
 * 佣金比例配置信息Controller
 * @author hub
 * @version 2018-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/commission/commissionConfig")
public class CommissionConfigController extends BaseController {

	@Autowired
	private CommissionConfigService commissionConfigService;
	
	@ModelAttribute
	public CommissionConfig get(@RequestParam(required=false) String id) {
		CommissionConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = commissionConfigService.get(id);
		}
		if (entity == null){
			entity = new CommissionConfig();
		}
		return entity;
	}
	
	@RequiresPermissions("commission:commissionConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(CommissionConfig commissionConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CommissionConfig> page = commissionConfigService.findPage(new Page<CommissionConfig>(request, response), commissionConfig); 
		model.addAttribute("page", page);
		return "modules/commission/commissionConfigList";
	}

	@RequiresPermissions("commission:commissionConfig:view")
	@RequestMapping(value = "form")
	public String form(CommissionConfig commissionConfig, Model model) {
		model.addAttribute("commissionConfig", commissionConfig);
		return "modules/commission/commissionConfigForm";
	}

	@RequiresPermissions("commission:commissionConfig:edit")
	@RequestMapping(value = "save")
	public String save(CommissionConfig commissionConfig, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, commissionConfig)){
			return form(commissionConfig, model);
		}
		commissionConfigService.save(commissionConfig);
		addMessage(redirectAttributes, "保存佣金比例配置信息成功");
		return "redirect:"+Global.getAdminPath()+"/commission/commissionConfig/?repage";
	}
	
	@RequiresPermissions("commission:commissionConfig:edit")
	@RequestMapping(value = "delete")
	public String delete(CommissionConfig commissionConfig, RedirectAttributes redirectAttributes) {
		commissionConfigService.delete(commissionConfig);
		addMessage(redirectAttributes, "删除佣金比例配置信息成功");
		return "redirect:"+Global.getAdminPath()+"/commission/commissionConfig/?repage";
	}

}