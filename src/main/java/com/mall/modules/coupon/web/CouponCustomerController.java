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
import com.mall.modules.coupon.entity.CouponCustomer;
import com.mall.modules.coupon.service.CouponCustomerService;

/**
 * 用户优惠券Controller
 * @author wankang
 * @version 2018-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/coupon/couponCustomer")
public class CouponCustomerController extends BaseController {

	@Autowired
	private CouponCustomerService couponCustomerService;
	
	@ModelAttribute
	public CouponCustomer get(@RequestParam(required=false) String id) {
		CouponCustomer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = couponCustomerService.get(id);
		}
		if (entity == null){
			entity = new CouponCustomer();
		}
		return entity;
	}
	
	@RequiresPermissions("coupon:couponCustomer:view")
	@RequestMapping(value = {"list", ""})
	public String list(CouponCustomer couponCustomer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CouponCustomer> page = couponCustomerService.findPage(new Page<CouponCustomer>(request, response), couponCustomer); 
		model.addAttribute("page", page);
		return "modules/coupon/couponCustomerList";
	}

	@RequiresPermissions("coupon:couponCustomer:view")
	@RequestMapping(value = "form")
	public String form(CouponCustomer couponCustomer, Model model) {
		model.addAttribute("couponCustomer", couponCustomer);
		return "modules/coupon/couponCustomerForm";
	}

	@RequiresPermissions("coupon:couponCustomer:edit")
	@RequestMapping(value = "save")
	public String save(CouponCustomer couponCustomer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, couponCustomer)){
			return form(couponCustomer, model);
		}
		couponCustomerService.save(couponCustomer);
		addMessage(redirectAttributes, "保存用户优惠券成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/couponCustomer/?repage";
	}
	
	@RequiresPermissions("coupon:couponCustomer:edit")
	@RequestMapping(value = "delete")
	public String delete(CouponCustomer couponCustomer, RedirectAttributes redirectAttributes) {
		couponCustomerService.delete(couponCustomer);
		addMessage(redirectAttributes, "删除用户优惠券成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/couponCustomer/?repage";
	}

}