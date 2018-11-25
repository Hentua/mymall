package com.mall.modules.order.web;

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
import com.mall.modules.order.entity.OrderWeixinReturnResult;
import com.mall.modules.order.service.OrderWeixinReturnResultService;

/**
 * 微信支付退款结果Controller
 * @author wankang
 * @version 2018-11-25
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderWeixinReturnResult")
public class OrderWeixinReturnResultController extends BaseController {

	@Autowired
	private OrderWeixinReturnResultService orderWeixinReturnResultService;
	
	@ModelAttribute
	public OrderWeixinReturnResult get(@RequestParam(required=false) String id) {
		OrderWeixinReturnResult entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderWeixinReturnResultService.get(id);
		}
		if (entity == null){
			entity = new OrderWeixinReturnResult();
		}
		return entity;
	}
	
	@RequiresPermissions("order:orderWeixinReturnResult:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderWeixinReturnResult orderWeixinReturnResult, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderWeixinReturnResult> page = orderWeixinReturnResultService.findPage(new Page<OrderWeixinReturnResult>(request, response), orderWeixinReturnResult); 
		model.addAttribute("page", page);
		return "modules/order/orderWeixinReturnResultList";
	}

	@RequiresPermissions("order:orderWeixinReturnResult:view")
	@RequestMapping(value = "form")
	public String form(OrderWeixinReturnResult orderWeixinReturnResult, Model model) {
		model.addAttribute("orderWeixinReturnResult", orderWeixinReturnResult);
		return "modules/order/orderWeixinReturnResultForm";
	}

	@RequiresPermissions("order:orderWeixinReturnResult:edit")
	@RequestMapping(value = "save")
	public String save(OrderWeixinReturnResult orderWeixinReturnResult, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderWeixinReturnResult)){
			return form(orderWeixinReturnResult, model);
		}
		orderWeixinReturnResultService.save(orderWeixinReturnResult);
		addMessage(redirectAttributes, "保存微信支付退款结果成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderWeixinReturnResult/?repage";
	}
	
	@RequiresPermissions("order:orderWeixinReturnResult:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderWeixinReturnResult orderWeixinReturnResult, RedirectAttributes redirectAttributes) {
		orderWeixinReturnResultService.delete(orderWeixinReturnResult);
		addMessage(redirectAttributes, "删除微信支付退款结果成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderWeixinReturnResult/?repage";
	}

}