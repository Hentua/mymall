package com.mall.modules.order.web;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.order.entity.OrderSettlement;
import com.mall.modules.order.service.OrderSettlementService;
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
 * 订单结算Controller
 * @author hub
 * @version 2018-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderSettlement")
public class OrderSettlementController extends BaseController {

	@Autowired
	private OrderSettlementService orderSettlementService;
	
	@ModelAttribute
	public OrderSettlement get(@RequestParam(required=false) String id) {
		OrderSettlement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderSettlementService.get(id);
		}
		if (entity == null){
			entity = new OrderSettlement();
		}
		return entity;
	}
	
	@RequiresPermissions("order:orderSettlement:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderSettlement orderSettlement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderSettlement> page = orderSettlementService.findPage(new Page<OrderSettlement>(request, response), orderSettlement); 
		model.addAttribute("page", page);
		return "modules/order/orderSettlementList";
	}

	@RequiresPermissions("order:orderSettlement:view")
	@RequestMapping(value = "form")
	public String form(OrderSettlement orderSettlement, Model model) {
		model.addAttribute("orderSettlement", orderSettlement);
		return "modules/order/orderSettlementForm";
	}

	@RequiresPermissions("order:orderSettlement:edit")
	@RequestMapping(value = "save")
	public String save(OrderSettlement orderSettlement, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderSettlement)){
			return form(orderSettlement, model);
		}
		orderSettlementService.save(orderSettlement);
		addMessage(redirectAttributes, "保存订单结算成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderSettlement/?repage";
	}

	@RequiresPermissions("order:orderSettlement:edit")
	@RequestMapping(value = "updateStatus")
	public String updateStatus(OrderSettlement orderSettlement, Model model, RedirectAttributes redirectAttributes) {
		orderSettlement = orderSettlementService.get(orderSettlement.getId());
		orderSettlement.setStatus("2");
		orderSettlementService.save(orderSettlement);
		addMessage(redirectAttributes, "保存订单结算成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderSettlement/?repage";
	}
	
	@RequiresPermissions("order:orderSettlement:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderSettlement orderSettlement, RedirectAttributes redirectAttributes) {
		orderSettlementService.delete(orderSettlement);
		addMessage(redirectAttributes, "删除订单结算成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderSettlement/?repage";
	}

}