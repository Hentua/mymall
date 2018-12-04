package com.mall.modules.order.web;

import com.google.common.collect.Maps;
import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.excel.ExportExcel;
import com.mall.common.web.BaseController;
import com.mall.modules.order.entity.OrderSettlement;
import com.mall.modules.order.service.OrderSettlementService;
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
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

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

	private static DecimalFormat df = new DecimalFormat("#0.00");
	
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
//		Map<String, String> total = orderSettlementService.findTotal(orderSettlement);
		Map<String, String> total = Maps.newHashMap();
		Double settlementAmountTotal = 0.00;
		Double orderAmountTotal = 0.00;
		for (OrderSettlement o : page.getList()) {
			settlementAmountTotal += o.getSettlementAmount();
			orderAmountTotal += o.getOrderAmount();
		}
		total.put("orderAmountTotal", df.format(orderAmountTotal));
		total.put("settlementAmountTotal", df.format(settlementAmountTotal));
		model.addAttribute("total", total);
		model.addAttribute("page", page);
		return "modules/order/orderSettlementList";
	}

	@RequiresPermissions("order:orderSettlement:view")
	@RequestMapping(value = {"frozenList"})
	public String frozenList(OrderSettlement orderSettlement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderSettlement> page = orderSettlementService.findPage(new Page<OrderSettlement>(request, response), orderSettlement);
//		Map<String, String> total = orderSettlementService.findTotal(orderSettlement);
		Map<String, String> total = Maps.newHashMap();
		Double settlementAmountTotal = 0.00;
		Double orderAmountTotal = 0.00;
		for (OrderSettlement o : page.getList()) {
			settlementAmountTotal += o.getSettlementAmount();
			orderAmountTotal += o.getOrderAmount();
		}
		total.put("orderAmountTotal", df.format(orderAmountTotal));
		total.put("settlementAmountTotal", df.format(settlementAmountTotal));
		model.addAttribute("total", total);
		model.addAttribute("page", page);
		return "modules/order/orderSettlementFrozenList";
	}

	@RequestMapping(value = {"merchantList"})
	public String merchantList(OrderSettlement orderSettlement, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currUser = UserUtils.getUser();
		orderSettlement.setUserId(currUser.getId());
		Page<OrderSettlement> page = orderSettlementService.findListWithGoods(new Page<OrderSettlement>(request, response), orderSettlement);
//		Map<String, String> total = orderSettlementService.findTotal(orderSettlement);
		Map<String, String> total = Maps.newHashMap();
		Double settlementAmountTotal = 0.00;
		Double orderAmountTotal = 0.00;
		for (OrderSettlement o : page.getList()) {
			settlementAmountTotal += o.getGoodsSettlementAmount();
			orderAmountTotal += o.getSubtotal();
		}
		total.put("orderAmountTotal", df.format(orderAmountTotal));
		total.put("settlementAmountTotal", df.format(settlementAmountTotal));
		model.addAttribute("total", total);
		model.addAttribute("page", page);
		return "modules/order/orderSettlementListMerchant";
	}

	@RequestMapping(value = "exportOrderSettlement")
	public void exportSettlementList(OrderSettlement orderSettlement, HttpServletRequest request, HttpServletResponse response) {
		List<OrderSettlement> orderSettlementList = orderSettlementService.findList(orderSettlement);
		ExportExcel exportExcel = new ExportExcel("货款结算", OrderSettlement.class, 1, 0);
		try {
			exportExcel.setDataList(orderSettlementList);
			exportExcel.write(response, "货款结算.xlsx");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "merchantExportOrderSettlement")
	public void merchantExportOrderSettlement(OrderSettlement orderSettlement, HttpServletRequest request, HttpServletResponse response) {
		User currUser = UserUtils.getUser();
		orderSettlement.setUserId(currUser.getId());
		Page<OrderSettlement> page = orderSettlementService.findListWithGoods(new Page<>(request, response, -1), orderSettlement);
		List<OrderSettlement> orderSettlementList = page.getList();
		ExportExcel exportExcel = new ExportExcel("货款结算", OrderSettlement.class, 1, 0, 1);
		try {
			exportExcel.setDataList(orderSettlementList);
			exportExcel.write(response, "货款结算.xlsx");
		}catch (Exception e) {
			e.printStackTrace();
		}
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
		orderSettlement.setStatus("3");
		orderSettlementService.save(orderSettlement);
		addMessage(redirectAttributes, "保存订单结算成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderSettlement/list?repage";
	}
	
	@RequiresPermissions("order:orderSettlement:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderSettlement orderSettlement, RedirectAttributes redirectAttributes) {
		orderSettlementService.delete(orderSettlement);
		addMessage(redirectAttributes, "删除订单结算成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderSettlement/?repage";
	}

}