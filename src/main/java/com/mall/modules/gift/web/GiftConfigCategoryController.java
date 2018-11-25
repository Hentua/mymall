package com.mall.modules.gift.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.modules.gift.entity.GiftPurchaseLog;
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

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.web.BaseController;
import com.mall.common.utils.StringUtils;
import com.mall.modules.gift.entity.GiftConfigCategory;
import com.mall.modules.gift.service.GiftConfigCategoryService;

/**
 * 礼包类别Controller
 * @author wankang
 * @version 2018-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/gift/giftConfigCategory")
public class GiftConfigCategoryController extends BaseController {

	@Autowired
	private GiftConfigCategoryService giftConfigCategoryService;
	
	@ModelAttribute
	public GiftConfigCategory get(@RequestParam(required=false) String id) {
		GiftConfigCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = giftConfigCategoryService.get(id);
		}
		if (entity == null){
			entity = new GiftConfigCategory();
		}
		return entity;
	}
	
	@RequiresPermissions("gift:giftConfigCategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(GiftConfigCategory giftConfigCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GiftConfigCategory> page = giftConfigCategoryService.findPage(new Page<GiftConfigCategory>(request, response), giftConfigCategory); 
		model.addAttribute("page", page);
		return "modules/gift/giftConfigCategoryList";
	}

	@RequiresPermissions("gift:giftConfigCategory:view")
	@RequestMapping(value = {"buyList"})
	public String buyList(GiftConfigCategory giftConfigCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		giftConfigCategory.setStatus("1");
		Page<GiftConfigCategory> page = giftConfigCategoryService.findPage(new Page<GiftConfigCategory>(request, response), giftConfigCategory);
		model.addAttribute("page", page);
		return "modules/gift/giftConfigCategoryBuyList";
	}

	@RequiresPermissions("gift:giftConfigCategory:view")
	@RequestMapping(value = "form")
	public String form(GiftConfigCategory giftConfigCategory, Model model) {
		model.addAttribute("giftConfigCategory", giftConfigCategory);
		return "modules/gift/giftConfigCategoryForm";
	}

	@RequiresPermissions("gift:giftConfigCategory:view")
	@RequestMapping(value = "giftConfigCategoryDetail")
	public String giftConfigCategoryDetail(GiftConfigCategory giftConfigCategory, Model model) {
		model.addAttribute("giftConfigCategory", giftConfigCategory);
		return "modules/gift/giftConfigCategoryDetail";
	}

	@RequiresPermissions("gift:giftConfigCategory:view")
	@RequestMapping(value = "giftConfigCategoryBuyDetail")
	public String giftConfigCategoryBuyDetail(GiftConfigCategory giftConfigCategory, Model model) {
		model.addAttribute("giftConfigCategory", giftConfigCategory);
		return "modules/gift/giftConfigCategoryBuyDetail";
	}

	@RequestMapping(value = "giftConfigCategoryBuy")
	public String giftConfigCategoryBuy(GiftConfigCategory giftConfigCategory, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String id = giftConfigCategory.getId();
		Integer buyCount = giftConfigCategory.getBuyCount();
		String payChannel = giftConfigCategory.getPayChannel();
		if (StringUtils.isBlank(id)) {
			addMessage(redirectAttributes, "未选择要购买的礼包");
			return "redirect:"+Global.getAdminPath()+"/gift/giftConfigCategory/?repage";
		}
		giftConfigCategory = this.get(id);
		if (StringUtils.isBlank(payChannel) || (!"0".equals(payChannel) && !"3".equals(payChannel) && !"2".equals(payChannel))) {
			model.addAttribute("message", "未选择有效的支付渠道");
			return giftConfigCategoryBuyDetail(giftConfigCategory, model);
		}
		if (null == buyCount || buyCount <= 0) {
			model.addAttribute("message", "购买礼包数量不合法");
			return giftConfigCategoryBuyDetail(giftConfigCategory, model);
		}
		giftConfigCategory.setBuyCount(buyCount);
		giftConfigCategory.setPayChannel(payChannel);
		GiftPurchaseLog giftPurchaseLog = giftConfigCategoryService.giftPurchase(giftConfigCategory);
		// 微信支付
		if("0".equals(payChannel)) {
			return "redirect:" + Global.getAdminPath() + "/payment/weixinPay?paymentNo=" + giftPurchaseLog.getPaymentNo() + "&callbackUrl=" + Global.getAdminPath() + "/gift/giftPurchaseLog";
		}
		// 余额支付
		else if("3".equals(payChannel)) {
			return "redirect:" + Global.getAdminPath() + "/payment/balancePayForm?paymentNo=" + giftPurchaseLog.getPaymentNo() + "&callbackUrl=" + Global.getAdminPath() + "/gift/giftPurchaseLog";
		}
		// 打款到财务
		else {
			model.addAttribute("message", "提交成功，打款到公司财务账户，并备注订单号："+giftPurchaseLog.getPaymentNo()+"，审核后礼包将会到库");
			return buyList(new GiftConfigCategory(), request, response, model);
		}
	}

	@RequiresPermissions("gift:giftConfigCategory:edit")
	@RequestMapping(value = "save")
	public String save(GiftConfigCategory giftConfigCategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, giftConfigCategory)){
			return form(giftConfigCategory, model);
		}
		if (StringUtils.isNotBlank(giftConfigCategory.getMerchantMobile())) {
			User merchantUser = UserUtils.getByLoginName(giftConfigCategory.getMerchantMobile());
			if (null == merchantUser) {
				model.addAttribute("message", "商家不存在");
				return form(giftConfigCategory, model);
			}
		}
		if (StringUtils.isNotBlank(giftConfigCategory.getId())) {
			String remarks = giftConfigCategory.getRemarks();
			giftConfigCategory = this.get(giftConfigCategory.getId());
			giftConfigCategory.setRemarks(remarks);
		}
		giftConfigCategoryService.save(giftConfigCategory);
		addMessage(redirectAttributes, "保存礼包类别成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftConfigCategory/?repage";
	}

	@RequiresPermissions("gift:giftConfigCategory:edit")
	@RequestMapping(value = "enableGiftCategory")
	public String enableGiftCategory(GiftConfigCategory giftConfigCategory, RedirectAttributes redirectAttributes) {
		if(null == giftConfigCategory || StringUtils.isBlank(giftConfigCategory.getId())) {
			addMessage(redirectAttributes, "未选择要上架的礼包类别");
			return "redirect:"+Global.getAdminPath()+"/gift/giftConfigCategory/?repage";
		}
		giftConfigCategory = this.get(giftConfigCategory.getId());
		if("1".equals(giftConfigCategory.getStatus())) {
			addMessage(redirectAttributes, "上架失败，礼包类别已上架");
			return "redirect:"+Global.getAdminPath()+"/gift/giftConfigCategory/?repage";
		}
		giftConfigCategory.setStatus("1");
		giftConfigCategoryService.save(giftConfigCategory);
		addMessage(redirectAttributes, "上架礼包类别成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftConfigCategory/?repage";
	}

	@RequiresPermissions("gift:giftConfigCategory:edit")
	@RequestMapping(value = "disableGiftCategory")
	public String disableGiftCategory(GiftConfigCategory giftConfigCategory, RedirectAttributes redirectAttributes) {
		if(null == giftConfigCategory || StringUtils.isBlank(giftConfigCategory.getId())) {
			addMessage(redirectAttributes, "未选择要下架的礼包类别");
			return "redirect:"+Global.getAdminPath()+"/gift/giftConfigCategory/?repage";
		}
		giftConfigCategory = this.get(giftConfigCategory.getId());
		if("0".equals(giftConfigCategory.getStatus())) {
			addMessage(redirectAttributes, "下架失败，礼包类别已下架");
			return "redirect:"+Global.getAdminPath()+"/gift/giftConfigCategory/?repage";
		}
		giftConfigCategory.setStatus("0");
		giftConfigCategoryService.save(giftConfigCategory);
		addMessage(redirectAttributes, "下架礼包类别成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftConfigCategory/?repage";
	}
	
	@RequiresPermissions("gift:giftConfigCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(GiftConfigCategory giftConfigCategory, RedirectAttributes redirectAttributes) {
		giftConfigCategoryService.delete(giftConfigCategory);
		addMessage(redirectAttributes, "删除礼包类别成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftConfigCategory/?repage";
	}

}