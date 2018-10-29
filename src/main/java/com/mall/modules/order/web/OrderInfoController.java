package com.mall.modules.order.web;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.order.entity.OrderLogistics;
import com.mall.modules.order.service.OrderInfoService;
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

/**
 * 订单信息Controller
 * @author wankang
 * @version 2018-10-12
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderInfo")
public class OrderInfoController extends BaseController {

	@Autowired
	private OrderInfoService orderInfoService;

	@ModelAttribute
	public OrderInfo get(@RequestParam(required=false) String id) {
		OrderInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderInfoService.get(id);
		}
		if (entity == null){
			entity = new OrderInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("order:orderInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderInfo orderInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderInfo> page = orderInfoService.findPage(new Page<OrderInfo>(request, response), orderInfo); 
		model.addAttribute("page", page);
		return "modules/order/orderInfoList";
	}

	@RequiresPermissions("order:orderInfo:view")
	@RequestMapping(value = {"merchantList"})
	public String merchantList(OrderInfo orderInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		orderInfo.setMerchantCode(UserUtils.getUser().getId());
		Page<OrderInfo> page = orderInfoService.findPage(new Page<OrderInfo>(request, response), orderInfo);
		model.addAttribute("page", page);
		return "modules/order/merchantOrderInfoList";
	}

	@RequiresPermissions("order:orderInfo:view")
	@RequestMapping(value = "form")
	public String form(OrderInfo orderInfo, Model model) {
		model.addAttribute("orderInfo", orderInfo);
		return "modules/order/orderInfoForm";
	}

	@RequiresPermissions("order:orderInfo:view")
	@RequestMapping(value = "orderDelivery")
	public String orderDelivery(OrderInfo orderInfo, Model model) {
		model.addAttribute("orderInfo", orderInfo);
		return "modules/order/orderDelivery";
	}

	@RequiresPermissions("order:orderInfo:edit")
	@RequestMapping(value = "save")
	public String save(OrderInfo orderInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderInfo)){
			return form(orderInfo, model);
		}
		orderInfoService.save(orderInfo);
		addMessage(redirectAttributes, "保存订单信息成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderInfo/?repage";
	}

	@RequiresPermissions("order:orderInfo:edit")
	@RequestMapping(value = "orderDeliverySave")
	public String orderDeliverySave(OrderInfo orderInfo, Model model, RedirectAttributes redirectAttributes) {
		String orderNo = orderInfo.getOrderNo();
		String message = "";
		boolean flag = true;
		if(StringUtils.isBlank(orderNo)) {
			flag = false;
			message = "非法操作";
		}
		OrderLogistics orderLogistics = orderInfo.getOrderLogistics();
		if(null == orderLogistics) {
			flag = false;
			message = "非法操作";
		}else if (StringUtils.isBlank(orderLogistics.getLogisticsType()) || StringUtils.isBlank(orderLogistics.getExpressNo())) {
			message = "快递信息有误";
			flag = false;
		}
		if(!flag) {
			model.addAttribute("message", message);
			return orderDelivery(this.get(orderInfo.getId()), model);
		}
		boolean result = orderInfoService.orderDeliverySave(orderInfo);
		if(!result) {
			model.addAttribute("message", "订单不存在");
			return orderDelivery(this.get(orderInfo.getId()), model);
		}
		addMessage(redirectAttributes, "发货成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderInfo/orderDelivery/?repage";
	}
	
	@RequiresPermissions("order:orderInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderInfo orderInfo, RedirectAttributes redirectAttributes) {
		orderInfoService.delete(orderInfo);
		addMessage(redirectAttributes, "删除订单信息成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderInfo/?repage";
	}

}