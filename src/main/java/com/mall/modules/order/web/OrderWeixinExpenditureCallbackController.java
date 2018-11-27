package com.mall.modules.order.web;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.order.entity.OrderWeixinExpenditureCallback;
import com.mall.modules.order.service.OrderWeixinExpenditureCallbackService;
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

/**
 * 微信提现返回结果Controller
 * @author wankang
 * @version 2018-11-27
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderWeixinExpenditureCallback")
public class OrderWeixinExpenditureCallbackController extends BaseController {

	@Autowired
	private OrderWeixinExpenditureCallbackService orderWeixinExpenditureCallbackService;
	
	@ModelAttribute
	public OrderWeixinExpenditureCallback get(@RequestParam(required=false) String id) {
		OrderWeixinExpenditureCallback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderWeixinExpenditureCallbackService.get(id);
		}
		if (entity == null){
			entity = new OrderWeixinExpenditureCallback();
		}
		return entity;
	}
	
	@RequiresPermissions("order:orderWeixinExpenditureCallback:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderWeixinExpenditureCallback orderWeixinExpenditureCallback, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderWeixinExpenditureCallback> page = orderWeixinExpenditureCallbackService.findPage(new Page<OrderWeixinExpenditureCallback>(request, response), orderWeixinExpenditureCallback); 
		model.addAttribute("page", page);
		return "modules/order/orderWeixinExpenditureCallbackList";
	}

	@RequiresPermissions("order:orderWeixinExpenditureCallback:view")
	@RequestMapping(value = "form")
	public String form(OrderWeixinExpenditureCallback orderWeixinExpenditureCallback, Model model) {
		model.addAttribute("orderWeixinExpenditureCallback", orderWeixinExpenditureCallback);
		return "modules/order/orderWeixinExpenditureCallbackForm";
	}

	@RequiresPermissions("order:orderWeixinExpenditureCallback:edit")
	@RequestMapping(value = "save")
	public String save(OrderWeixinExpenditureCallback orderWeixinExpenditureCallback, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderWeixinExpenditureCallback)){
			return form(orderWeixinExpenditureCallback, model);
		}
		orderWeixinExpenditureCallbackService.save(orderWeixinExpenditureCallback);
		addMessage(redirectAttributes, "保存微信提现返回结果成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderWeixinExpenditureCallback/?repage";
	}
	
	@RequiresPermissions("order:orderWeixinExpenditureCallback:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderWeixinExpenditureCallback orderWeixinExpenditureCallback, RedirectAttributes redirectAttributes) {
		orderWeixinExpenditureCallbackService.delete(orderWeixinExpenditureCallback);
		addMessage(redirectAttributes, "删除微信提现返回结果成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderWeixinExpenditureCallback/?repage";
	}

}