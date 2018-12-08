package com.mall.modules.order.web;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.google.common.collect.Lists;
import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.excel.ExportExcel;
import com.mall.common.web.BaseController;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.order.entity.OrderReturns;
import com.mall.modules.order.entity.OrderSettlement;
import com.mall.modules.order.entity.TaskRequest;
import com.mall.modules.order.entity.TaskResponse;
import com.mall.modules.order.service.OrderReturnsService;
import com.mall.modules.order.utils.HttpRequest;
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
import java.util.HashMap;
import java.util.List;

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
	@Autowired
	private MemberInfoService memberInfoService;
	
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
		User currUser = UserUtils.getUser();
		orderReturns.setMerchantCode(currUser.getId());
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

	@RequiresPermissions("order:orderReturns:view")
	@RequestMapping(value = {"operatorList"})
	public String operatorList(OrderReturns orderReturns, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderReturns> page = orderReturnsService.findPage(new Page<OrderReturns>(request, response), orderReturns);
		model.addAttribute("page", page);
		return "modules/order/orderReturnsOperatorList";
	}

	@RequestMapping(value = "exportOperatorList")
	public void exportOperatorList(OrderReturns orderReturns, HttpServletRequest request, HttpServletResponse response) {
		String [] itemIds = request.getParameterValues("itemIds");
		List<OrderReturns> orderReturnsList;
		if(null != itemIds && itemIds.length > 0) {
			orderReturnsList = Lists.newArrayList();
			for (String itemId : itemIds) {
				orderReturnsList.add(orderReturnsService.get(itemId));
			}
		}else {
			orderReturnsList = orderReturnsService.findList(orderReturns);
		}
		ExportExcel exportExcel = new ExportExcel("售后列表", OrderReturns.class);
		try {
			exportExcel.setDataList(orderReturnsList);
			exportExcel.write(response, "售后列表.xlsx");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequiresPermissions("order:orderReturns:view")
	@RequestMapping(value = "operatorForm")
	public String operatorForm(OrderReturns orderReturns, Model model) {
		model.addAttribute("orderReturns", orderReturns);
		return "modules/order/orderReturnsOperatorForm";
	}

	@RequiresPermissions("order:orderReturns:view")
	@RequestMapping(value = "operatorSave")
	public String operatorSave(OrderReturns orderReturns, Model model, RedirectAttributes redirectAttributes) {
		String platformReply = orderReturns.getPlatformReply();
		orderReturns = this.get(orderReturns.getId());
		if(StringUtils.isBlank(platformReply)) {
		    model.addAttribute("message", "回复不能为空");
		    return form(orderReturns, model);
        }
		orderReturns.setPlatformReply(platformReply);
		orderReturnsService.save(orderReturns);
        addMessage(redirectAttributes, "保存售后申请成功");
        return "redirect:"+Global.getAdminPath()+"/order/orderReturns/operatorList?repage";
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
		orderReturns.setStatus("2");
		MemberInfo queryCondition = new MemberInfo();
		queryCondition.setId(orderReturns.getMerchantCode());
		MemberInfo memberInfo = memberInfoService.get(queryCondition);
		TaskRequest req = new TaskRequest();
		req.setCompany(orderReturns.getLogisticsType());
		req.setFrom(memberInfo.getShippingAddress());
		req.setTo(orderReturns.getConsigneeAddress());
		req.setNumber(orderReturns.getExpressNo());
		req.getParameters().put("callbackurl", Global.getConfig("server.baseUrl") + Global.getConfig("adminPath") + "/api/kuaidi100Callback");
		req.setKey(Global.getConfig("kuaidi.key"));
		HashMap<String, String> p = new HashMap<String, String>();
		p.put("schema", "json");
		p.put("param", req.toString());
		try {
			String ret = HttpRequest.postData("http://www.kuaidi100.com/poll", p, "UTF-8");
			TaskResponse resp = JSON.parseObject(ret, TaskResponse.class);
			if(resp.getResult()) {
				orderReturnsService.handle(orderReturns);
				addMessage(redirectAttributes, "发货成功");
				return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
			}else {
				model.addAttribute("message", "发货失败，物流信息有误");
				return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
			}
		}catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "发货失败");
			return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
		}
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
		try {
			orderReturns.setStatus("3");
			orderReturnsService.handle(orderReturns);
			addMessage(redirectAttributes, "退款成功");
			return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
		}catch (WxPayException e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "退款失败：" + e.getMessage());
			return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
		}catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "退款失败，请稍后重试");
			return "redirect:"+Global.getAdminPath()+"/order/orderReturns/?repage";
		}
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