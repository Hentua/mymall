package com.mall.modules.coupon.web;

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
import com.mall.modules.coupon.entity.CouponConfig;
import com.mall.modules.coupon.service.CouponConfigService;

/**
 * 优惠券规则配置Controller
 * @author wankang
 * @version 2018-10-28
 */
@Controller
@RequestMapping(value = "${adminPath}/coupon/couponConfig")
public class CouponConfigController extends BaseController {

	@Autowired
	private CouponConfigService couponConfigService;
	
	@ModelAttribute
	public CouponConfig get(@RequestParam(required=false) String id) {
		CouponConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = couponConfigService.get(id);
		}
		if (entity == null){
			entity = new CouponConfig();
		}
		return entity;
	}
	
	@RequiresPermissions("coupon:couponConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(CouponConfig couponConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CouponConfig> page = couponConfigService.findPage(new Page<CouponConfig>(request, response), couponConfig); 
		model.addAttribute("page", page);
		return "modules/coupon/couponConfigList";
	}

	@RequiresPermissions("coupon:couponConfig:view")
	@RequestMapping(value = "form")
	public String form(CouponConfig couponConfig, Model model) {
		model.addAttribute("couponConfig", couponConfig);
		return "modules/coupon/couponConfigForm";
	}

	@RequiresPermissions("coupon:couponConfig:edit")
	@RequestMapping(value = "save")
	public String save(CouponConfig couponConfig, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, couponConfig)){
			return form(couponConfig, model);
		}
		couponConfigService.save(couponConfig);
		addMessage(redirectAttributes, "保存优惠券规则成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/couponConfig/?repage";
	}
	
	@RequiresPermissions("coupon:couponConfig:edit")
	@RequestMapping(value = "delete")
	public String delete(CouponConfig couponConfig, RedirectAttributes redirectAttributes) {
		couponConfigService.delete(couponConfig);
		addMessage(redirectAttributes, "删除优惠券规则成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/couponConfig/?repage";
	}

}