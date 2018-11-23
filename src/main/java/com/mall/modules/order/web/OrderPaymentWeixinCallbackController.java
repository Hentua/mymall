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
import com.mall.modules.order.entity.OrderPaymentWeixinCallback;
import com.mall.modules.order.service.OrderPaymentWeixinCallbackService;

/**
 * 微信支付回调结果Controller
 * @author wankang
 * @version 2018-11-23
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderPaymentWeixinCallback")
public class OrderPaymentWeixinCallbackController extends BaseController {

	@Autowired
	private OrderPaymentWeixinCallbackService orderPaymentWeixinCallbackService;
	
	@ModelAttribute
	public OrderPaymentWeixinCallback get(@RequestParam(required=false) String id) {
		OrderPaymentWeixinCallback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderPaymentWeixinCallbackService.get(id);
		}
		if (entity == null){
			entity = new OrderPaymentWeixinCallback();
		}
		return entity;
	}
	
	@RequiresPermissions("order:orderPaymentWeixinCallback:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderPaymentWeixinCallback orderPaymentWeixinCallback, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderPaymentWeixinCallback> page = orderPaymentWeixinCallbackService.findPage(new Page<OrderPaymentWeixinCallback>(request, response), orderPaymentWeixinCallback); 
		model.addAttribute("page", page);
		return "modules/order/orderPaymentWeixinCallbackList";
	}

	@RequiresPermissions("order:orderPaymentWeixinCallback:view")
	@RequestMapping(value = "form")
	public String form(OrderPaymentWeixinCallback orderPaymentWeixinCallback, Model model) {
		model.addAttribute("orderPaymentWeixinCallback", orderPaymentWeixinCallback);
		return "modules/order/orderPaymentWeixinCallbackForm";
	}

	@RequiresPermissions("order:orderPaymentWeixinCallback:edit")
	@RequestMapping(value = "save")
	public String save(OrderPaymentWeixinCallback orderPaymentWeixinCallback, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderPaymentWeixinCallback)){
			return form(orderPaymentWeixinCallback, model);
		}
		orderPaymentWeixinCallbackService.save(orderPaymentWeixinCallback);
		addMessage(redirectAttributes, "保存微信支付回调结果成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderPaymentWeixinCallback/?repage";
	}
	
	@RequiresPermissions("order:orderPaymentWeixinCallback:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderPaymentWeixinCallback orderPaymentWeixinCallback, RedirectAttributes redirectAttributes) {
		orderPaymentWeixinCallbackService.delete(orderPaymentWeixinCallback);
		addMessage(redirectAttributes, "删除微信支付回调结果成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderPaymentWeixinCallback/?repage";
	}

}