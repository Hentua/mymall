package com.mall.modules.account.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.modules.settlement.entity.SettlementInfo;
import com.mall.modules.settlement.service.SettlementInfoService;
import com.mall.modules.sys.utils.UserUtils;
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
import com.mall.modules.account.entity.AccountInfo;
import com.mall.modules.account.service.AccountInfoService;

import java.util.Date;

/**
 * 账户明细Controller
 * @author hub
 * @version 2018-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/account/accountInfo")
public class AccountInfoController extends BaseController {

	@Autowired
	private AccountInfoService accountInfoService;

	@Autowired
	private SettlementInfoService settlementInfoService;
	
	@ModelAttribute
	public AccountInfo get(@RequestParam(required=false) String id) {
		AccountInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accountInfoService.get(id);
		}
		if (entity == null){
			entity = new AccountInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("account:accountInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccountInfo accountInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccountInfo> page = accountInfoService.findPage(new Page<AccountInfo>(request, response), accountInfo); 
		model.addAttribute("page", page);
		return "modules/account/accountInfoList";
	}

	@RequiresPermissions("account:accountInfo:view")
	@RequestMapping(value = "form")
	public String form(AccountInfo accountInfo, Model model) {
		model.addAttribute("accountInfo", accountInfo);
		return "modules/account/accountInfoForm";
	}

	@RequiresPermissions("account:accountInfo:edit")
	@RequestMapping(value = "save")
	public String save(AccountInfo accountInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accountInfo)){
			return form(accountInfo, model);
		}
		accountInfoService.save(accountInfo);
		addMessage(redirectAttributes, "保存账户明细成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountInfo/?repage";
	}

	@RequiresPermissions("account:accountInfo:edit")
	@RequestMapping(value = "updateStatus")
	public String updateStatus(AccountInfo accountInfo, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, accountInfo)){
//			return form(accountInfo, model);
//		}
//		accountInfoService.save(accountInfo);
		AccountInfo a = accountInfoService.get(accountInfo.getId());
		//提现
		if("11".equals(accountInfo.getStatus())){
			SettlementInfo settlementInfo = new SettlementInfo();
			// 提现结算类型 （1：佣金提现 2：订单交易结算）
			settlementInfo.setType("1");
			settlementInfo.setSubDate(new Date());
			settlementInfo.setSubUserId(UserUtils.getUser().getId());
			settlementInfo.setUnionId(accountInfo.getId());
			settlementInfo.setUnionId(a.getUnionId());
			settlementInfo.setAmount(a.getAmount());
			settlementInfo.setStatus("2");
			settlementInfoService.save(settlementInfo);
		}
		addMessage(redirectAttributes, "保存账户明细成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountInfo/list?repage";
	}


	
	@RequiresPermissions("account:accountInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AccountInfo accountInfo, RedirectAttributes redirectAttributes) {
		accountInfoService.delete(accountInfo);
		addMessage(redirectAttributes, "删除账户明细成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountInfo/?repage";
	}

}