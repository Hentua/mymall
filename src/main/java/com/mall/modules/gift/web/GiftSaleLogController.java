package com.mall.modules.gift.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.modules.gift.entity.GiftMerchant;
import com.mall.modules.gift.service.GiftMerchantService;
import com.mall.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.web.BaseController;
import com.mall.common.utils.StringUtils;
import com.mall.modules.gift.entity.GiftSaleLog;
import com.mall.modules.gift.service.GiftSaleLogService;

/**
 * 礼包购买记录Controller
 * @author wankang
 * @version 2018-10-28
 */
@Controller
@RequestMapping(value = "${adminPath}/gift/giftSaleLog")
public class GiftSaleLogController extends BaseController {

	@Autowired
	private GiftSaleLogService giftSaleLogService;
	@Autowired
	private GiftMerchantService giftMerchantService;
	
	@ModelAttribute
	public GiftSaleLog get(@RequestParam(required=false) String id) {
		GiftSaleLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = giftSaleLogService.get(id);
		}
		if (entity == null){
			entity = new GiftSaleLog();
		}
		return entity;
	}
	
	@RequiresPermissions("gift:giftSaleLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(GiftSaleLog giftSaleLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GiftSaleLog> page = giftSaleLogService.findPage(new Page<GiftSaleLog>(request, response), giftSaleLog); 
		model.addAttribute("page", page);
		return "modules/gift/giftSaleLogList";
	}

	@RequiresPermissions("gift:giftSaleLog:view")
	@RequestMapping(value = {"buyList"})
	public String buyList(GiftSaleLog giftSaleLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		giftSaleLog.setMerchantCode(UserUtils.getUser().getId());
		Page<GiftSaleLog> page = giftSaleLogService.findPage(new Page<GiftSaleLog>(request, response), giftSaleLog);
		model.addAttribute("page", page);
		return "modules/gift/giftBuyLogList";
	}

	@RequiresPermissions("gift:giftSaleLog:edit")
	@RequestMapping(value = "checkPass")
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String checkPass(GiftSaleLog giftSaleLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, giftSaleLog)){
			return form(giftSaleLog, model);
		}
		giftSaleLog.setFlag("1");
		giftSaleLogService.save(giftSaleLog);
		GiftMerchant giftMerchant = new GiftMerchant();
		giftMerchant.setId(giftSaleLog.getGiftMerchantId());
		giftMerchant.setDelFlag("0");
		giftMerchantService.save(giftMerchant);
		addMessage(redirectAttributes, "审核成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftSaleLog/?repage";
	}

	@RequiresPermissions("gift:giftSaleLog:edit")
	@RequestMapping(value = "orderCancel")
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String orderCancel(GiftSaleLog giftSaleLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, giftSaleLog)){
			return form(giftSaleLog, model);
		}
		giftSaleLog.setFlag("2");
		giftSaleLogService.save(giftSaleLog);
		addMessage(redirectAttributes, "订单作废成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftSaleLog/?repage";
	}

	@RequiresPermissions("gift:giftSaleLog:view")
	@RequestMapping(value = "form")
	public String form(GiftSaleLog giftSaleLog, Model model) {
		model.addAttribute("giftSaleLog", giftSaleLog);
		return "modules/gift/giftSaleLogForm";
	}

	@RequiresPermissions("gift:giftSaleLog:edit")
	@RequestMapping(value = "save")
	public String save(GiftSaleLog giftSaleLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, giftSaleLog)){
			return form(giftSaleLog, model);
		}
		giftSaleLogService.save(giftSaleLog);
		addMessage(redirectAttributes, "保存礼包购买记录成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftSaleLog/?repage";
	}
	
	@RequiresPermissions("gift:giftSaleLog:edit")
	@RequestMapping(value = "delete")
	public String delete(GiftSaleLog giftSaleLog, RedirectAttributes redirectAttributes) {
		giftSaleLogService.delete(giftSaleLog);
		addMessage(redirectAttributes, "删除礼包购买记录成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftSaleLog/?repage";
	}

}