package com.mall.modules.order.web;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.order.entity.OrderReturns;
import com.mall.modules.order.service.OrderReturnsService;
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
 * 订单售后申请Controller
 * @author wankang
 * @version 2018-11-15
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderReturns")
public class OrderReturnsController extends BaseController {

	@Autowired
	private OrderReturnsService orderReturnsService;
	
	@ModelAttribute
	public OrderReturns get(@RequestParam(required=false) String id) {
		OrderReturns entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderReturnsService.get(id);
		}
		if (entity == null){
			entity = new OrderReturns();
		}
		return entity;
	}
	
	@RequiresPermissions("order:orderReturns:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderReturns orderReturns, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderReturns> page = orderReturnsService.findPage(new Page<OrderReturns>(request, response), orderReturns); 
		model.addAttribute("page", page);
		return "modules/order/orderReturnsList";
	}

	@RequiresPermissions("order:orderReturns:view")
	@RequestMapping(value = "form")
	public String form(OrderReturns orderReturns, Model model) {
		model.addAttribute("orderReturns", orderReturns);
		return "modules/order/orderReturnsForm";
	}

	@RequiresPermissions("order:orderReturns:edit")
	@RequestMapping(value = "save")
	public String save(OrderReturns orderReturns, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderReturns)){
			return form(orderReturns, model);
		}
		orderReturnsService.save(orderReturns);
		addMessage(redirectAttributes, "保存售后申请成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
	}
	
	@RequiresPermissions("order:orderReturns:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderReturns orderReturns, RedirectAttributes redirectAttributes) {
		orderReturnsService.delete(orderReturns);
		addMessage(redirectAttributes, "删除售后申请成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
	}

	@RequestMapping(value = "checkPass")
	public String checkPass(OrderReturns orderReturns, RedirectAttributes redirectAttributes, Model model) {
		orderReturns = this.verifyData(orderReturns, redirectAttributes);
		if(null == orderReturns) {
			return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
		}
		if(!"0".equals(orderReturns.getStatus())) {
			addMessage(redirectAttributes, "请求不合法");
			return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
		}
		orderReturns.setStatus("1");
		orderReturnsService.check(orderReturns);
		addMessage(redirectAttributes, "审核成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
	}

	@RequestMapping(value = "checkNotPass")
	public String checkNotPass(OrderReturns orderReturns, RedirectAttributes redirectAttributes, Model model) {
		orderReturns = this.verifyData(orderReturns, redirectAttributes);
		if(null == orderReturns) {
			return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
		}
		if(!"0".equals(orderReturns.getStatus())) {
			addMessage(redirectAttributes, "请求不合法");
			return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
		}
		orderReturns.setStatus("4");
		orderReturnsService.check(orderReturns);
		addMessage(redirectAttributes, "审核成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
	}

	@RequestMapping(value = "delivery")
	public String delivery(OrderReturns orderReturns, RedirectAttributes redirectAttributes, Model model) {
		String logistics = orderReturns.getLogisticsType();
		String expressNo = orderReturns.getExpressNo();
		orderReturns = this.verifyData(orderReturns, redirectAttributes);
		if(null == orderReturns) {
			return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
		}
		if(!"1".equals(orderReturns.getStatus())) {
			addMessage(redirectAttributes, "请求不合法");
			return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
		}
		if(StringUtils.isBlank(logistics) || StringUtils.isBlank(expressNo)) {
			addMessage(redirectAttributes, "物流信息有误");
			return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
		}
		orderReturns.setExpressNo(expressNo);
		orderReturns.setLogisticsType(logistics);
		orderReturnsService.handle(orderReturns);
		addMessage(redirectAttributes, "发货成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
	}

	@RequestMapping(value = "refund")
	public String refund(OrderReturns orderReturns, RedirectAttributes redirectAttributes, Model model) {
		orderReturns = this.verifyData(orderReturns, redirectAttributes);
		if(null == orderReturns) {
			return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
		}
		if(!"1".equals(orderReturns.getStatus())) {
			addMessage(redirectAttributes, "请求不合法");
			return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
		}
		// todo refund
		orderReturnsService.handle(orderReturns);
		addMessage(redirectAttributes, "退款成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
	}

	private OrderReturns verifyData(OrderReturns orderReturns, RedirectAttributes redirectAttributes) {
		String id = orderReturns.getId();
		String reply = orderReturns.getReply();
		String remarks = orderReturns.getRemarks();
		if(StringUtils.isBlank(id)) {
			addMessage(redirectAttributes, "未选择要审核的售后申请");
			return null;
		}
		if(StringUtils.isBlank(reply)) {
			addMessage(redirectAttributes, "未填写回复");
			return null;
		}
		if(reply.length() > 500) {
			addMessage(redirectAttributes, "回复超过最大允许长度，最大500字");
			return null;
		}
		orderReturns = this.get(id);
		orderReturns.setReply(reply);
		orderReturns.setRemarks(remarks);
		return orderReturns;
	}

}