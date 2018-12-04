package com.mall.modules.order.web;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.excel.ExportExcel;
import com.mall.common.web.BaseController;
import com.mall.modules.member.entity.MemberFeedback;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.order.entity.OrderLogistics;
import com.mall.modules.order.entity.TaskRequest;
import com.mall.modules.order.entity.TaskResponse;
import com.mall.modules.order.service.OrderInfoService;
import com.mall.modules.order.utils.HttpRequest;
import com.mall.modules.sys.utils.UserUtils;
import org.apache.poi.ss.usermodel.Row;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

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
	@Autowired
	private MemberInfoService memberInfoService;

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

	@RequestMapping(value = {"exportData"})
	public void exportData(OrderInfo orderInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<OrderInfo> orderInfos = orderInfoService.findList(orderInfo);
		ExportExcel exportExcel = new ExportExcel("订单信息", OrderInfo.class);
		try {
			exportExcel.setDataList(orderInfos);
			exportExcel.write(response, "订单信息.xlsx");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = {"exportMerchantData"})
	public void exportMerchantData(OrderInfo orderInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		orderInfo.setMerchantCode(UserUtils.getUser().getId());
		List<OrderInfo> orderInfos = orderInfoService.findList(orderInfo);
		ExportExcel exportExcel = new ExportExcel("订单信息", OrderInfo.class);
		try {
			exportExcel.setDataList(orderInfos);
			exportExcel.write(response, "订单信息.xlsx");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = {"exportPendingDeliver"})
	public void exportPendingDeliver(OrderInfo orderInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		orderInfo.setMerchantCode(UserUtils.getUser().getId());
		orderInfo.setOrderStatus("1");
		List<OrderInfo> orderInfos = orderInfoService.findList(orderInfo);
		List<String> headList = Lists.newArrayList();
		headList.add("收件人姓名");
		headList.add("收件人联系电话");
		headList.add("收件人公司");
		headList.add("省");
		headList.add("市");
		headList.add("区");
		headList.add("详细地址");
		headList.add("托寄物内容");
		headList.add("数量");
		headList.add("重量");
		headList.add("件数");
		headList.add("快递产品");
		headList.add("付款方式");
		headList.add("月结卡号");
		headList.add("是否保价");
		headList.add("声明价值");
		headList.add("是否自取");
		headList.add("行业型增值服务");
		headList.add("扩展字段1");
		headList.add("扩展字段2");
		headList.add("扩展字段3");
		ExportExcel exportExcel = new ExportExcel(null, headList);
		try {
			List<OrderLogistics> orderLogistics = Lists.newArrayList();
			for (OrderInfo o : orderInfos) {
				Row row = exportExcel.addRow();
				exportExcel.addCell(row, 1, o.getOrderLogistics().getConsigneeRealname());
				exportExcel.addCell(row, 2, o.getOrderLogistics().getConsigneeTelphone());
				exportExcel.addCell(row, 4, o.getOrderLogistics().getProvinceName());
				exportExcel.addCell(row, 5, o.getOrderLogistics().getCityName());
				exportExcel.addCell(row, 6, o.getOrderLogistics().getAreaName());
				exportExcel.addCell(row, 7, o.getOrderLogistics().getConsigneeAddress());
				exportExcel.addCell(row, 8, o.getOrderLogistics().getContent());
				exportExcel.addCell(row, 9, o.getOrderLogistics().getProduct());
			}
			exportExcel.write(response, "待发货物流信息.xlsx");
		}catch (Exception e) {
			e.printStackTrace();
		}
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
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
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
		MemberInfo queryCondition = new MemberInfo();
		queryCondition.setId(orderInfo.getMerchantCode());
		MemberInfo memberInfo = memberInfoService.get(queryCondition);
		TaskRequest req = new TaskRequest();
		req.setCompany(orderLogistics.getLogisticsType());
		req.setFrom(memberInfo.getShippingAddress());
		req.setTo(orderLogistics.getConsigneeAddress());
		req.setNumber(orderLogistics.getExpressNo());
		req.getParameters().put("callbackurl", Global.getConfig("server.baseUrl") + Global.getConfig("adminPath") + "/api/kuaidi100Callback");
		req.setKey(Global.getConfig("kuaidi.key"));
		HashMap<String, String> p = new HashMap<String, String>();
		p.put("schema", "json");
		p.put("param", req.toString());
		try {
			String ret = HttpRequest.postData("http://www.kuaidi100.com/poll", p, "UTF-8");
			TaskResponse resp = JSON.parseObject(ret, TaskResponse.class);
			if(resp.getResult()) {
				boolean result = orderInfoService.orderDeliverySave(orderInfo);
				if(!result) {
					model.addAttribute("message", "订单不存在");
					return orderDelivery(this.get(orderInfo.getId()), model);
				}
				addMessage(redirectAttributes, "发货成功");
				return "redirect:"+Global.getAdminPath()+"/order/orderInfo/merchantList/?repage";
			}else {
				model.addAttribute("message", "发货失败，物流信息有误");
				return orderDelivery(this.get(orderInfo.getId()), model);
			}
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "发货失败，物流信息有误");
			return orderDelivery(this.get(orderInfo.getId()), model);
		}
	}
	
	@RequiresPermissions("order:orderInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderInfo orderInfo, RedirectAttributes redirectAttributes) {
		orderInfoService.delete(orderInfo);
		addMessage(redirectAttributes, "删除订单信息成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderInfo/?repage";
	}

}