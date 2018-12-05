package com.mall.modules.commission.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.common.utils.ResultGenerator;
import com.mall.modules.account.entity.AccountFlow;
import com.mall.modules.account.service.AccountFlowService;
import com.mall.modules.account.service.AccountService;
import com.mall.modules.coupon.service.CouponCustomerService;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import com.sohu.idcenter.IdWorker;
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
import com.mall.modules.commission.entity.CommissionInfo;
import com.mall.modules.commission.service.CommissionInfoService;

/**
 * 佣金明细Controller
 * @author hub
 * @version 2018-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/commission/commissionInfo")
public class CommissionInfoController extends BaseController {

	@Autowired
	private CommissionInfoService commissionInfoService;

	@Autowired
	private MemberInfoService memberInfoService;

	@Autowired
	private AccountFlowService accountFlowService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private CouponCustomerService couponCustomerService;
	
	@ModelAttribute
	public CommissionInfo get(@RequestParam(required=false) String id) {
		CommissionInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = commissionInfoService.get(id);
		}
		if (entity == null){
			entity = new CommissionInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("commission:commissionInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CommissionInfo commissionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CommissionInfo> page = commissionInfoService.findPage(new Page<CommissionInfo>(request, response), commissionInfo); 
		model.addAttribute("page", page);
		return "modules/commission/commissionInfoList";
	}
	@RequestMapping(value = {"merchantList", ""})
	public String merchantList(CommissionInfo commissionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		commissionInfo.setUserId(user.getId());
		Page<CommissionInfo> page = commissionInfoService.findPage(new Page<CommissionInfo>(request, response), commissionInfo);
		model.addAttribute("page", page);
		model.addAttribute("commissionInfo",commissionInfo);
		return "modules/commission/merchantCommissionInfoList";
	}

	/**
	 * 佣金提现
	 * @param commissionInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"commissionInfoTakeOut", ""})
	public String commissionInfoTakeOut(CommissionInfo commissionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setId(user.getId());
		memberInfo = memberInfoService.get(memberInfo);
		model.addAttribute("memberInfo", memberInfo);
		return "modules/commission/commissionInfoTakeOut";
	}

	/**
	 * 佣金转余额
	 * @param commissionInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"commissionInfoTurnAccount", ""})
	public String commissionInfoTurnAccount(CommissionInfo commissionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setId(user.getId());
		memberInfo = memberInfoService.get(memberInfo);
		model.addAttribute("memberInfo", memberInfo);
		return "modules/commission/commissionInfoTurnAccount";
	}

	private static IdWorker idWorker = new IdWorker();


	/**
	 * 佣金转余额
	 * @param commissionInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"commissionInfoTurnAccountSave", ""})
	public String commissionInfoTurnAccountSave(CommissionInfo commissionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		MemberInfo m = new MemberInfo();
		m.setId(user.getId());
		MemberInfo memberInfo = memberInfoService.get(m);
		String amountStr = request.getParameter("amount");
		Double amount = Double.valueOf(amountStr);
		if (memberInfo.getCommission() < amount) {
			renderString(response, ResultGenerator.genFailResult("佣金余额不足"));
		}
		AccountFlow accountFlow = new AccountFlow();
		accountFlow.setFlowNo(String.valueOf(idWorker.getId()));
		accountFlow.setUserId(user.getId());
		accountFlow.setAmount(amount);
		accountFlow.setType("1");//收入
		accountFlow.setMode("2");//佣金转余额
//		accountFlow.setIncomeExpenditureMode(request.getParameter("incomeExpenditureMode"));// 收支方式 1：微信 2：用户转账
//		accountFlow.setBankAccount(request.getParameter("bankAccount"));//银行账户
//		accountFlow.setBankAccountName(request.getParameter("bankAccountName"));//开户人名称
//		accountFlow.setBankName(request.getParameter("bankName"));//开户行
		accountFlow.setCheckStatus("2");
		accountFlowService.save(accountFlow);
		//送优惠券函数
		couponCustomerService.saveCouponCustomerByPlatform(amount, "0", user.getId(), "佣金转余额优惠券", "3");


		//新增转余额记录
		CommissionInfo c = new CommissionInfo();
		c.setType("7");
		c.setAmount(amount);
		c.setUserId(user.getId());
//        commissionInfo.setBankAccount(request.getParameter("bankAccount"));
//        commissionInfo.setBankAccountName(request.getParameter("bankAccountName"));
//        commissionInfo.setBankName(request.getParameter("bankName"));
		c.setCheckStatus("2");
		c.setStatus("1");
		commissionInfoService.save(c);
		//操作余额
		accountService.editAccount(memberInfo.getBalance() + amount, memberInfo.getCommission() - amount, user.getId());
		return "modules/commission/commissionInfoTurnAccountSave";
	}






	@RequiresPermissions("commission:commissionInfo:view")
	@RequestMapping(value = "form")
	public String form(CommissionInfo commissionInfo, Model model) {
		model.addAttribute("commissionInfo", commissionInfo);
		return "modules/commission/commissionInfoForm";
	}

	@RequiresPermissions("commission:commissionInfo:edit")
	@RequestMapping(value = "save")
	public String save(CommissionInfo commissionInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, commissionInfo)){
			return form(commissionInfo, model);
		}
		commissionInfoService.save(commissionInfo);
		addMessage(redirectAttributes, "保存佣金明细成功");
		return "redirect:"+Global.getAdminPath()+"/commission/commissionInfo/?repage";
	}
	
	@RequiresPermissions("commission:commissionInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CommissionInfo commissionInfo, RedirectAttributes redirectAttributes) {
		commissionInfoService.delete(commissionInfo);
		addMessage(redirectAttributes, "删除佣金明细成功");
		return "redirect:"+Global.getAdminPath()+"/commission/commissionInfo/?repage";
	}

}