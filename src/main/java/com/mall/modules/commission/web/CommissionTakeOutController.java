package com.mall.modules.commission.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.mall.common.utils.excel.ExportExcel;
import com.mall.modules.account.service.AccountService;
import com.mall.modules.commission.entity.CommissionInfo;
import com.mall.modules.commission.entity.CommissionInfoDto;
import com.mall.modules.commission.service.CommissionInfoService;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
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
import com.mall.modules.commission.entity.CommissionTakeOut;
import com.mall.modules.commission.service.CommissionTakeOutService;

import java.util.List;

import static com.mall.common.service.BaseService.dataScopeFilter;

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

	@Autowired
	private CommissionInfoService commissionInfoService;
	
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
	public String list(CommissionInfo commissionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {

		commissionInfo.setType("6");
		Page<CommissionInfo> page = commissionInfoService.findPage(new Page<CommissionInfo>(request, response), commissionInfo);
		model.addAttribute("page", page);
		return "modules/commission/commissionTakeOutList";
	}

	/**
	 * 运营端-佣金提现审核
	 * @param commissionInfo
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = {"listExportData"})
	public void exportData(CommissionInfo commissionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		commissionInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "uoo", "uo"));
		String [] itemIds = request.getParameterValues("itemIds");
		List<CommissionInfo> commissionInfos;
		if(null != itemIds && itemIds.length > 0) {
			commissionInfos = Lists.newArrayList();
			for (String itemId : itemIds) {
				commissionInfos.add(commissionInfoService.get(itemId));
			}
		}else {
			commissionInfo.setType("6");
			commissionInfos = commissionInfoService.findList(commissionInfo);
		}

		ExportExcel exportExcel = new ExportExcel("佣金提现审核", CommissionInfoDto.class);
		try {
			exportExcel.setDataList(commissionInfos);
			exportExcel.write(response, "佣金提现审核.xlsx");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "form")
	public String form(CommissionInfo commissionInfo, Model model) {
		commissionInfo = commissionInfoService.get(commissionInfo.getId());
		model.addAttribute("commissionInfo", commissionInfo);
		return "modules/commission/commissionTakeOutForm";
	}


	@RequestMapping(value = "checks")
	public String checks(CommissionInfo c,HttpServletRequest request,   RedirectAttributes redirectAttributes) {

//			commissionInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "uoo", "uo"));
			String [] itemIds = request.getParameterValues("itemIds");
			List<CommissionInfo> commissionInfos =Lists.newArrayList();
			if(null != itemIds && itemIds.length > 0) {
				for (String itemId : itemIds) {
					commissionInfos.add(commissionInfoService.get(itemId));
				}
			}
			for (CommissionInfo commissionInfo: commissionInfos) {
				if("2".equals(commissionInfo.getCheckStatus())){
					addMessage(redirectAttributes, "打款失败[该笔提现已打款]");
					return "redirect:"+Global.getAdminPath()+"/commission/commissionTakeOut/?repage";
				}
				MemberInfo memberInfo = new MemberInfo();
				memberInfo.setId(commissionInfo.getUserId());
				memberInfo = memberInfoService.get(memberInfo);
				if(memberInfo.getCommission()<commissionInfo.getAmount()){
					addMessage(redirectAttributes, "打款失败[用户佣金余额不足]");
					return "redirect:"+Global.getAdminPath()+"/commission/commissionTakeOut/?repage";
				}
				//审核成功后
				accountService.editAccount(memberInfo.getBalance(),memberInfo.getCommission()-commissionInfo.getAmount(),memberInfo.getId());
				commissionInfo.setCheckStatus("2");
				commissionInfo.setCheckRemark(c.getCheckRemark());
				commissionInfoService.save(commissionInfo);
			}
			addMessage(redirectAttributes, "打款成功");
		return "redirect:"+Global.getAdminPath()+"/commission/commissionTakeOut/list?repage";
	}


	@RequestMapping(value = "save")
	public String save(CommissionInfo c, Model model, RedirectAttributes redirectAttributes) {
		if("3".equals(c.getCheckStatus())){
			CommissionInfo commissionInfo = commissionInfoService.get(c.getId());
			commissionInfo.setCheckStatus(c.getCheckStatus());
			commissionInfo.setCheckRemark(c.getCheckRemark());
			commissionInfoService.save(commissionInfo);
		}
		if("2".equals(c.getCheckStatus())){
			CommissionInfo commissionInfo = commissionInfoService.get(c.getId());
			if("2".equals(commissionInfo.getCheckStatus())){
				addMessage(redirectAttributes, "打款失败[该笔提现已打款]");
				return "redirect:"+Global.getAdminPath()+"/commission/commissionTakeOut/?repage";
			}
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setId(commissionInfo.getUserId());
			memberInfo = memberInfoService.get(memberInfo);
			if(memberInfo.getCommission()<commissionInfo.getAmount()){
				addMessage(redirectAttributes, "打款失败[用户佣金余额不足]");
				return "redirect:"+Global.getAdminPath()+"/commission/commissionTakeOut/?repage";
			}
			//审核成功后
			accountService.editAccount(memberInfo.getBalance(),memberInfo.getCommission()-commissionInfo.getAmount(),memberInfo.getId());
			commissionInfo.setCheckStatus("2");
			commissionInfo.setCheckRemark(c.getCheckRemark());
			commissionInfoService.save(commissionInfo);
			addMessage(redirectAttributes, "打款成功");
		}
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
		CommissionInfo commissionInfo = commissionInfoService.get(commissionTakeOut.getId());
		if("2".equals(commissionTakeOut.getCheckStatus())){
			addMessage(redirectAttributes, "打款成功");
			return "redirect:"+Global.getAdminPath()+"/commission/commissionTakeOut/?repage";
		}
		commissionTakeOut.setCheckStatus("2");
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setId(commissionInfo.getUserId());
		memberInfo = memberInfoService.get(memberInfo);
		if(memberInfo.getCommission()<commissionInfo.getAmount()){
			addMessage(redirectAttributes, "打款失败[用户佣金余额不足]");
			return "redirect:"+Global.getAdminPath()+"/commission/commissionTakeOut/?repage";
		}
		//审核成功后
		accountService.editAccount(memberInfo.getBalance(),memberInfo.getCommission()-commissionInfo.getAmount(),memberInfo.getId());
		commissionInfo.setCheckStatus("2");
		commissionInfo.setCheckRemark(commissionTakeOut.getCheckRemark());
		commissionInfoService.save(commissionInfo);
		addMessage(redirectAttributes, "打款成功");
		return "redirect:"+Global.getAdminPath()+"/commission/commissionTakeOut/?repage";
	}



}