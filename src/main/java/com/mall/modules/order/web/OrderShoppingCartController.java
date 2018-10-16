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
import com.mall.modules.order.entity.OrderShoppingCart;
import com.mall.modules.order.service.OrderShoppingCartService;

/**
 * 购物车Controller
 * @author wankang
 * @version 2018-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderShoppingCart")
public class OrderShoppingCartController extends BaseController {

	@Autowired
	private OrderShoppingCartService orderShoppingCartService;
	
	@ModelAttribute
	public OrderShoppingCart get(@RequestParam(required=false) String id) {
		OrderShoppingCart entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderShoppingCartService.get(id);
		}
		if (entity == null){
			entity = new OrderShoppingCart();
		}
		return entity;
	}
	
	@RequiresPermissions("order:orderShoppingCart:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderShoppingCart orderShoppingCart, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderShoppingCart> page = orderShoppingCartService.findPage(new Page<OrderShoppingCart>(request, response), orderShoppingCart); 
		model.addAttribute("page", page);
		return "modules/order/orderShoppingCartList";
	}

	@RequiresPermissions("order:orderShoppingCart:view")
	@RequestMapping(value = "form")
	public String form(OrderShoppingCart orderShoppingCart, Model model) {
		model.addAttribute("orderShoppingCart", orderShoppingCart);
		return "modules/order/orderShoppingCartForm";
	}

	@RequiresPermissions("order:orderShoppingCart:edit")
	@RequestMapping(value = "save")
	public String save(OrderShoppingCart orderShoppingCart, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderShoppingCart)){
			return form(orderShoppingCart, model);
		}
		orderShoppingCartService.save(orderShoppingCart);
		addMessage(redirectAttributes, "保存购物车成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderShoppingCart/?repage";
	}
	
	@RequiresPermissions("order:orderShoppingCart:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderShoppingCart orderShoppingCart, RedirectAttributes redirectAttributes) {
		orderShoppingCartService.delete(orderShoppingCart);
		addMessage(redirectAttributes, "删除购物车成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderShoppingCart/?repage";
	}

}