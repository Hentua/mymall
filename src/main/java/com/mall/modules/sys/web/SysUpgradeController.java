package com.mall.modules.sys.web;

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
import com.mall.modules.sys.entity.SysUpgrade;
import com.mall.modules.sys.service.SysUpgradeService;

/**
 * app升级配置Controller
 * @author hub
 * @version 2018-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysUpgrade")
public class SysUpgradeController extends BaseController {

	@Autowired
	private SysUpgradeService sysUpgradeService;
	
	@ModelAttribute
	public SysUpgrade get(@RequestParam(required=false) String id) {
		SysUpgrade entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysUpgradeService.get(id);
		}
		if (entity == null){
			entity = new SysUpgrade();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysUpgrade:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysUpgrade sysUpgrade, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUpgrade> page = sysUpgradeService.findPage(new Page<SysUpgrade>(request, response), sysUpgrade); 
		model.addAttribute("page", page);
		return "modules/sys/sysUpgradeList";
	}

	@RequiresPermissions("sys:sysUpgrade:view")
	@RequestMapping(value = "form")
	public String form(SysUpgrade sysUpgrade, Model model) {
		model.addAttribute("sysUpgrade", sysUpgrade);
		return "modules/sys/sysUpgradeForm";
	}

	@RequiresPermissions("sys:sysUpgrade:edit")
	@RequestMapping(value = "save")
	public String save(SysUpgrade sysUpgrade, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysUpgrade)){
			return form(sysUpgrade, model);
		}
		sysUpgradeService.save(sysUpgrade);
		addMessage(redirectAttributes, "保存app升级配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUpgrade/?repage";
	}
	
	@RequiresPermissions("sys:sysUpgrade:edit")
	@RequestMapping(value = "delete")
	public String delete(SysUpgrade sysUpgrade, RedirectAttributes redirectAttributes) {
		sysUpgradeService.delete(sysUpgrade);
		addMessage(redirectAttributes, "删除app升级配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUpgrade/?repage";
	}

}