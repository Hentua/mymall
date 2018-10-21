package com.mall.modules.settlement.web;

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
import com.mall.modules.settlement.entity.SettlementInfo;
import com.mall.modules.settlement.service.SettlementInfoService;

/**
 * 提现结算信息Controller
 * @author hub
 * @version 2018-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/settlement/settlementInfo")
public class SettlementInfoController extends BaseController {

	@Autowired
	private SettlementInfoService settlementInfoService;
	
	@ModelAttribute
	public SettlementInfo get(@RequestParam(required=false) String id) {
		SettlementInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = settlementInfoService.get(id);
		}
		if (entity == null){
			entity = new SettlementInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("settlement:settlementInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(SettlementInfo settlementInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SettlementInfo> page = settlementInfoService.findPage(new Page<SettlementInfo>(request, response), settlementInfo); 
		model.addAttribute("page", page);
		return "modules/settlement/settlementInfoList";
	}

	@RequiresPermissions("settlement:settlementInfo:view")
	@RequestMapping(value = "form")
	public String form(SettlementInfo settlementInfo, Model model) {
		model.addAttribute("settlementInfo", settlementInfo);
		return "modules/settlement/settlementInfoForm";
	}

	@RequiresPermissions("settlement:settlementInfo:edit")
	@RequestMapping(value = "save")
	public String save(SettlementInfo settlementInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, settlementInfo)){
			return form(settlementInfo, model);
		}
		settlementInfoService.save(settlementInfo);
		addMessage(redirectAttributes, "保存提现结算信息成功");
		return "redirect:"+Global.getAdminPath()+"/settlement/settlementInfo/?repage";
	}
	
	@RequiresPermissions("settlement:settlementInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(SettlementInfo settlementInfo, RedirectAttributes redirectAttributes) {
		settlementInfoService.delete(settlementInfo);
		addMessage(redirectAttributes, "删除提现结算信息成功");
		return "redirect:"+Global.getAdminPath()+"/settlement/settlementInfo/?repage";
	}

}