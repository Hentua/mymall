package com.mall.modules.coupon.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
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
import com.mall.modules.coupon.entity.CouponMerchant;
import com.mall.modules.coupon.service.CouponMerchantService;

import java.util.Date;

/**
 * 商家优惠券Controller
 * @author wankang
 * @version 2018-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/coupon/couponMerchant")
public class CouponMerchantController extends BaseController {

	@Autowired
	private CouponMerchantService couponMerchantService;
	
	@ModelAttribute
	public CouponMerchant get(@RequestParam(required=false) String id) {
		CouponMerchant entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = couponMerchantService.get(id);
		}
		if (entity == null){
			entity = new CouponMerchant();
		}
		return entity;
	}
	
	@RequiresPermissions("coupon:couponMerchant:view")
	@RequestMapping(value = {"list", ""})
	public String list(CouponMerchant couponMerchant, HttpServletRequest request, HttpServletResponse response, Model model) {
		couponMerchant.setCouponStatus("0");
		Page<CouponMerchant> page = couponMerchantService.findPage(new Page<CouponMerchant>(request, response), couponMerchant); 
		model.addAttribute("page", page);
		return "modules/coupon/couponMerchantList";
	}

	@RequiresPermissions("coupon:couponMerchant:view")
	@RequestMapping(value = "form")
	public String form(CouponMerchant couponMerchant, Model model) {
		model.addAttribute("couponMerchant", couponMerchant);
		return "modules/coupon/couponMerchantForm";
	}

	@RequestMapping(value = "giftTransfer")
	public String giftTransfer(CouponMerchant couponMerchant, Model model, RedirectAttributes redirectAttributes) throws Exception {
		String id = couponMerchant.getId();
		String transferCustomerMobile = couponMerchant.getTransferCustomerMobile();
		User currUser = UserUtils.getUser();
		if(StringUtils.isBlank(id)) {
			addMessage(redirectAttributes, "未选择要赠送的优惠券");
			return "redirect:"+Global.getAdminPath()+"/coupon/couponMerchant/?repage";
		}
		if(StringUtils.isBlank(transferCustomerMobile)) {
			model.addAttribute("message", "未选择要赠送的会员");
			return form(couponMerchant, model);
		}
		User customerUser = UserUtils.getByLoginName(transferCustomerMobile);
		if(null == customerUser) {
			model.addAttribute("message", "会员不存在");
			return form(couponMerchant, model);
		}
		couponMerchant = this.get(id);
		couponMerchant.setTransferCustomerCode(customerUser.getId());
		if(!couponMerchant.getMerchantCode().equals(currUser.getId())) {
			addMessage(redirectAttributes, "所选优惠券不合法");
			return "redirect:"+Global.getAdminPath()+"/coupon/couponMerchant/?repage";
		}
		if((new Date()).getTime() > couponMerchant.getEndDate().getTime()) {
			addMessage(redirectAttributes, "优惠券已过期");
			return "redirect:"+Global.getAdminPath()+"/coupon/couponMerchant/?repage";
		}
		couponMerchantService.transferCoupon(couponMerchant);
		addMessage(redirectAttributes, "优惠券赠送成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/couponMerchant/?repage";
	}

	@RequiresPermissions("coupon:couponMerchant:edit")
	@RequestMapping(value = "save")
	public String save(CouponMerchant couponMerchant, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, couponMerchant)){
			return form(couponMerchant, model);
		}
		couponMerchantService.save(couponMerchant);
		addMessage(redirectAttributes, "保存商家优惠券成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/couponMerchant/?repage";
	}
	
	@RequiresPermissions("coupon:couponMerchant:edit")
	@RequestMapping(value = "delete")
	public String delete(CouponMerchant couponMerchant, RedirectAttributes redirectAttributes) {
		couponMerchantService.delete(couponMerchant);
		addMessage(redirectAttributes, "删除商家优惠券成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/couponMerchant/?repage";
	}

}