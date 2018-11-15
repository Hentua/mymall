package com.mall.modules.commission.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.modules.account.service.AccountService;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
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
import com.mall.modules.commission.entity.CommissionTakeOut;
import com.mall.modules.commission.service.CommissionTakeOutService;

/**
 * 佣金提现Controller
 * @author hub
 * @version 2018-11-15
 */
@Controller
@RequestMapping(value = "${adminPath}/commission/commissionTakeOut")
public class CommissionTakeOutController extends BaseController {

	@Autowired
	private CommissionTakeOutService commissionTakeOutService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private MemberInfoService memberInfoService;
	
	@ModelAttribute
	public CommissionTakeOut get(@RequestParam(required=false) String id) {
		CommissionTakeOut entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = commissionTakeOutService.get(id);
		}
		if (entity == null){
			entity = new CommissionTakeOut();
		}
		return entity;
	}
	
	@RequiresPermissions("commission:commissionTakeOut:view")
	@RequestMapping(value = {"list", ""})
	public String list(CommissionTakeOut commissionTakeOut, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CommissionTakeOut> page = commissionTakeOutService.findPage(new Page<CommissionTakeOut>(request, response), commissionTakeOut); 
		model.addAttribute("page", page);
		return "modules/commission/commissionTakeOutList";
	}

	@RequiresPermissions("commission:commissionTakeOut:view")
	@RequestMapping(value = "form")
	public String form(CommissionTakeOut commissionTakeOut, Model model) {
		model.addAttribute("commissionTakeOut", commissionTakeOut);
		return "modules/commission/commissionTakeOutForm";
	}

	@RequiresPermissions("commission:commissionTakeOut:edit")
	@RequestMapping(value = "save")
	public String save(CommissionTakeOut commissionTakeOut, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, commissionTakeOut)){
			return form(commissionTakeOut, model);
		}
		commissionTakeOutService.save(commissionTakeOut);
		addMessage(redirectAttributes, "保存佣金提现成功");
		return "redirect:"+Global.getAdminPath()+"/commission/commissionTakeOut/?repage";
	}
	
	@RequiresPermissions("commission:commissionTakeOut:edit")
	@RequestMapping(value = "delete")
	public String delete(CommissionTakeOut commissionTakeOut, RedirectAttributes redirectAttributes) {
		commissionTakeOutService.delete(commissionTakeOut);
		addMessage(redirectAttributes, "删除佣金提现成功");
		return "redirect:"+Global.getAdminPath()+"/commission/commissionTakeOut/?repage";
	}


	@RequiresPermissions("commission:commissionTakeOut:edit")
	@RequestMapping(value = "updateStatus")
	public String updateStatus(CommissionTakeOut commissionTakeOut, RedirectAttributes redirectAttributes) {
		commissionTakeOut = commissionTakeOutService.get(commissionTakeOut.getId());
		commissionTakeOut.setCheckStatus("2");

		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setId(commissionTakeOut.getUserId());
		memberInfo = memberInfoService.get(memberInfo);

		//审核成功后
		accountService.editAccount(memberInfo.getBalance(),memberInfo.getCommission()-commissionTakeOut.getAmount(),memberInfo.getId());
		commissionTakeOutService.save(commissionTakeOut);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/commission/commissionTakeOut/?repage";
	}



}