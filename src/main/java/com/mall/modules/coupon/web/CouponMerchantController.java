package com.mall.modules.coupon.web;

import com.mall.common.config.Global;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.coupon.entity.CouponMerchant;
import com.mall.modules.coupon.service.CouponMerchantService;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商家优惠券Controller
 * @author wankang
 * @version 2018-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/coupon/couponMerchant")
public class CouponMerchantController extends BaseController {

	@Autowired
	private CouponMerchantService couponMerchantService;
	@Autowired
	private MemberInfoService memberInfoService;
	
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
		User currUser = UserUtils.getUser();
		String merchantCode = currUser.getId();
		CouponMerchant queryCondition = new CouponMerchant();
		queryCondition.setMerchantCode(merchantCode);
		List<CouponMerchant> merchantList = couponMerchantService.findList(queryCondition);
		model.addAttribute("list", merchantList);
		return "modules/coupon/couponMerchantList";
	}

	@RequiresPermissions("coupon:couponMerchant:view")
	@RequestMapping(value = "form")
	public String form(CouponMerchant couponMerchant, Model model) {
		model.addAttribute("couponMerchant", couponMerchant);
		return "modules/coupon/couponMerchantForm";
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

	@RequestMapping(value = "transfer")
	public String transfer(CouponMerchant couponMerchant, Model model, RedirectAttributes redirectAttributes) {
		String id = couponMerchant.getId();
		if(StringUtils.isBlank(id)) {
			addMessage(redirectAttributes, "赠送失败，未选择要赠送的优惠券");
			return "redirect:"+Global.getAdminPath()+"/coupon/couponMerchant/?repage";
		}
		Double transferAmount = couponMerchant.getTransferAmount();
		CouponMerchant merchant = this.get(id);
		String customerReferee = couponMerchant.getCustomerReferee();
		if(null == transferAmount || transferAmount <= 0) {
			model.addAttribute("message", "赠送失败，赠送金额不得为空或小于1");
			return form(merchant, model);
		}
		if(StringUtils.isBlank(customerReferee)) {
			model.addAttribute("message", "赠送失败，未选择要赠送的会员");
			return form(merchant, model);
		}
		MemberInfo queryCondition = new MemberInfo();
		queryCondition.setReferee(customerReferee);
		MemberInfo memberInfo = memberInfoService.get(queryCondition);
		if(null == memberInfo) {
			model.addAttribute("message", "请填写正确的会员手机号或ID号");
			return form(merchant, model);
		}
		Double balance = merchant.getBalance();
		if(transferAmount > balance) {
			model.addAttribute("message", "赠送失败，余额不足");
			return form(merchant, model);
		}
		String customerCode = memberInfo.getId();
		merchant.setBalance(transferAmount);
		couponMerchantService.couponTransfer(merchant, customerCode);
		addMessage(redirectAttributes, "赠送成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/couponMerchant/?repage";
	}

}