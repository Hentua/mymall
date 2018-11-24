package com.mall.modules.member.web;

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
import com.mall.modules.member.entity.MerchantCollectionInfo;
import com.mall.modules.member.service.MerchantCollectionInfoService;

/**
 * 店铺收藏Controller
 * @author hub
 * @version 2018-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/member/merchantCollectionInfo")
public class MerchantCollectionInfoController extends BaseController {

	@Autowired
	private MerchantCollectionInfoService merchantCollectionInfoService;
	
	@ModelAttribute
	public MerchantCollectionInfo get(@RequestParam(required=false) String id) {
		MerchantCollectionInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantCollectionInfoService.get(id);
		}
		if (entity == null){
			entity = new MerchantCollectionInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("member:merchantCollectionInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantCollectionInfo merchantCollectionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MerchantCollectionInfo> page = merchantCollectionInfoService.findPage(new Page<MerchantCollectionInfo>(request, response), merchantCollectionInfo); 
		model.addAttribute("page", page);
		return "modules/member/merchantCollectionInfoList";
	}

	@RequiresPermissions("member:merchantCollectionInfo:view")
	@RequestMapping(value = "form")
	public String form(MerchantCollectionInfo merchantCollectionInfo, Model model) {
		model.addAttribute("merchantCollectionInfo", merchantCollectionInfo);
		return "modules/member/merchantCollectionInfoForm";
	}

	@RequiresPermissions("member:merchantCollectionInfo:edit")
	@RequestMapping(value = "save")
	public String save(MerchantCollectionInfo merchantCollectionInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantCollectionInfo)){
			return form(merchantCollectionInfo, model);
		}
		merchantCollectionInfoService.save(merchantCollectionInfo);
		addMessage(redirectAttributes, "保存店铺收藏成功");
		return "redirect:"+Global.getAdminPath()+"/member/merchantCollectionInfo/?repage";
	}
	
	@RequiresPermissions("member:merchantCollectionInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(MerchantCollectionInfo merchantCollectionInfo, RedirectAttributes redirectAttributes) {
		merchantCollectionInfoService.delete(merchantCollectionInfo);
		addMessage(redirectAttributes, "删除店铺收藏成功");
		return "redirect:"+Global.getAdminPath()+"/member/merchantCollectionInfo/?repage";
	}

}