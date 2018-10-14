package com.mall.modules.sys.web;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.sys.entity.SysSmsLog;
import com.mall.modules.sys.service.SysSmsLogService;
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
 * 短信发送记录Controller
 * @author wankang
 * @version 2018-10-11
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysSmsLog")
public class SysSmsLogController extends BaseController {

	@Autowired
	private SysSmsLogService sysSmsLogService;
	
	@ModelAttribute
	public SysSmsLog get(@RequestParam(required=false) String id) {
		SysSmsLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysSmsLogService.get(id);
		}
		if (entity == null){
			entity = new SysSmsLog();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysSmsLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysSmsLog sysSmsLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysSmsLog> page = sysSmsLogService.findPage(new Page<SysSmsLog>(request, response), sysSmsLog); 
		model.addAttribute("page", page);
		return "modules/sys/sysSmsLogList";
	}

	@RequiresPermissions("sys:sysSmsLog:view")
	@RequestMapping(value = "form")
	public String form(SysSmsLog sysSmsLog, Model model) {
		model.addAttribute("sysSmsLog", sysSmsLog);
		return "modules/sys/sysSmsLogForm";
	}

	@RequiresPermissions("sys:sysSmsLog:edit")
	@RequestMapping(value = "save")
	public String save(SysSmsLog sysSmsLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysSmsLog)){
			return form(sysSmsLog, model);
		}
		sysSmsLogService.save(sysSmsLog);
		addMessage(redirectAttributes, "保存短信发送记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysSmsLog/?repage";
	}
	
	@RequiresPermissions("sys:sysSmsLog:edit")
	@RequestMapping(value = "delete")
	public String delete(SysSmsLog sysSmsLog, RedirectAttributes redirectAttributes) {
		sysSmsLogService.delete(sysSmsLog);
		addMessage(redirectAttributes, "删除短信发送记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysSmsLog/?repage";
	}

}