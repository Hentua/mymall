package com.mall.modules.settlement.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.modules.account.entity.AccountInfo;
import com.mall.modules.account.service.AccountInfoService;
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
import com.mall.modules.settlement.entity.SettlementInfo;
import com.mall.modules.settlement.service.SettlementInfoService;

import java.util.Date;

/**
 * 提现结算信息Controller
 * @author hub
 * @version 2018-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/settlement/settlementInfo")
public class SettlementInfoController extends BaseController {

	@Autowired
	private SettlementInfoService settlementInfoService;

	@Autowired
	private AccountInfoService accountInfoService;
	
	@ModelAttribute
	public SettlementInfo get(@RequestParam(required=false) String id) {
		SettlementInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = settlementInfoService.get(id);
		}
		if (entity == null){
			entity = new SettlementInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("settlement:settlementInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(SettlementInfo settlementInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SettlementInfo> page = settlementInfoService.findPage(new Page<SettlementInfo>(request, response), settlementInfo); 
		model.addAttribute("page", page);
		return "modules/settlement/settlementInfoList";
	}

	@RequiresPermissions("settlement:settlementInfo:view")
	@RequestMapping(value = "form")
	public String form(SettlementInfo settlementInfo, Model model) {
		model.addAttribute("settlementInfo", settlementInfo);
		return "modules/settlement/settlementInfoForm";
	}

	@RequiresPermissions("settlement:settlementInfo:edit")
	@RequestMapping(value = "save")
	public String save(SettlementInfo settlementInfo, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, settlementInfo)){
//			return form(settlementInfo, model);
//		}
		SettlementInfo s = settlementInfoService.get(settlementInfo.getId()) ;
		s.setSettlementUserId(UserUtils.getUser().getId());
		s.setSettlementDate(new Date());
		s.setSettlementRemarks(settlementInfo.getSettlementRemarks());
		s.setStatus("3");
		settlementInfoService.save(s);

		//新增账单流水记录
		//卖家账单流水
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setUserId(s.getSubUserId());
		accountInfo.setType("2"); //收支类型 1：收入 2：支出
		//1：佣金提现 2：订单交易结算
		accountInfo.setWay("1".equals(s.getType())?"3":"4");//收支方式（1：佣金收益 2：销售收益） 支出（3：提现 4：结算）
		accountInfo.setAmount(s.getAmount());
		accountInfo.setUnionId(s.getId());
		accountInfo.setStatus("4");//状态 （1：已到账 2：未到账 3：未提现结算 4：已提现结算 ）
		accountInfoService.save(accountInfo);

		addMessage(redirectAttributes, "结算成功");
		return "redirect:"+Global.getAdminPath()+"/settlement/settlementInfo/list?repage";
	}
	
	@RequiresPermissions("settlement:settlementInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(SettlementInfo settlementInfo, RedirectAttributes redirectAttributes) {
		settlementInfoService.delete(settlementInfo);
		addMessage(redirectAttributes, "删除提现结算信息成功");
		return "redirect:"+Global.getAdminPath()+"/settlement/settlementInfo/?repage";
	}

}