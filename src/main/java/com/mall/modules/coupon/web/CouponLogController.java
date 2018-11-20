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
import com.mall.modules.coupon.entity.CouponLog;
import com.mall.modules.coupon.service.CouponLogService;

/**
 * 优惠券记录Controller
 * @author wankang
 * @version 2018-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/coupon/couponLog")
public class CouponLogController extends BaseController {

	@Autowired
	private CouponLogService couponLogService;
	
	@ModelAttribute
	public CouponLog get(@RequestParam(required=false) String id) {
		CouponLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = couponLogService.get(id);
		}
		if (entity == null){
			entity = new CouponLog();
		}
		return entity;
	}
	
	@RequiresPermissions("coupon:couponLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(CouponLog couponLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CouponLog> page = couponLogService.findPage(new Page<CouponLog>(request, response), couponLog); 
		model.addAttribute("page", page);
		return "modules/coupon/couponLogList";
	}

	@RequiresPermissions("coupon:couponLog:view")
	@RequestMapping(value = "form")
	public String form(CouponLog couponLog, Model model) {
		model.addAttribute("couponLog", couponLog);
		return "modules/coupon/couponLogForm";
	}

	@RequiresPermissions("coupon:couponLog:edit")
	@RequestMapping(value = "save")
	public String save(CouponLog couponLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, couponLog)){
			return form(couponLog, model);
		}
		couponLogService.save(couponLog);
		addMessage(redirectAttributes, "保存优惠券记录成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/couponLog/?repage";
	}
	
	@RequiresPermissions("coupon:couponLog:edit")
	@RequestMapping(value = "delete")
	public String delete(CouponLog couponLog, RedirectAttributes redirectAttributes) {
		couponLogService.delete(couponLog);
		addMessage(redirectAttributes, "删除优惠券记录成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/couponLog/?repage";
	}

}