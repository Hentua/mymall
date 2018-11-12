package com.mall.modules.member.web;

import com.google.common.collect.Lists;
import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.account.entity.AccountInfo;
import com.mall.modules.account.service.AccountInfoService;
import com.mall.modules.commission.entity.CommissionInfo;
import com.mall.modules.commission.service.CommissionConfigService;
import com.mall.modules.commission.service.CommissionInfoService;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.entity.MemberMerchantCheck;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.member.service.MemberMerchantCheckService;
import com.mall.modules.sys.entity.Role;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.service.SystemService;
import com.mall.modules.sys.utils.UserUtils;
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
import java.util.Date;
import java.util.List;

/**
 * 商户审核Controller
 * @author wankang
 * @version 2018-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/member/memberMerchantCheck")
public class MemberMerchantCheckController extends BaseController {

	@Autowired
	private MemberMerchantCheckService memberMerchantCheckService;
	@Autowired
	private MemberInfoService memberInfoService;

	//佣金详情service
	@Autowired
	private CommissionInfoService commissionInfoService;
	@Autowired
	private SystemService systemService;

	@Autowired
	private AccountInfoService accountInfoService;

	//佣金配置service
	@Autowired
	private CommissionConfigService commissionConfigService;

	@ModelAttribute
	public MemberMerchantCheck get(@RequestParam(required=false) String id) {
		MemberMerchantCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberMerchantCheckService.get(id);
		}
		if (entity == null){
			entity = new MemberMerchantCheck();
		}else {
			MemberInfo queryCondition = new MemberInfo();
			queryCondition.setId(entity.getMerchantCode());
			entity.setMemberInfo(memberInfoService.get(queryCondition));
		}
		return entity;
	}
	
	@RequiresPermissions("member:memberMerchantCheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(MemberMerchantCheck memberMerchantCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberMerchantCheck> page = memberMerchantCheckService.findPage(new Page<MemberMerchantCheck>(request, response), memberMerchantCheck); 
		model.addAttribute("page", page);
		return "modules/member/memberMerchantCheckList";
	}

	@RequiresPermissions("member:memberMerchantCheck:view")
	@RequestMapping(value = "form")
	public String form(MemberMerchantCheck memberMerchantCheck, Model model) {
		model.addAttribute("memberMerchantCheck", memberMerchantCheck);
		return "modules/member/memberMerchantCheckForm";
	}

	@RequiresPermissions("member:memberMerchantCheck:view")
	@RequestMapping(value = {"checkLog"})
	public String checkLog(MemberMerchantCheck memberMerchantCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currUser = UserUtils.getUser();
		memberMerchantCheck.setMerchantCode(currUser.getId());
		Page<MemberMerchantCheck> page = memberMerchantCheckService.findPage(new Page<MemberMerchantCheck>(request, response), memberMerchantCheck);
		model.addAttribute("page", page);
		return "modules/member/memberMerchantCheckLogList";
	}

	@RequiresPermissions("member:memberMerchantCheck:view")
	@RequestMapping(value = "checkLogHis")
	public String checkLogHis(MemberMerchantCheck memberMerchantCheck, Model model) {
		model.addAttribute("memberMerchantCheck", memberMerchantCheck);
		return "modules/member/memberMerchantCheckLog";
	}

	@RequestMapping(value = "checkPass")
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String checkPass(MemberMerchantCheck memberMerchantCheck, Model model, RedirectAttributes redirectAttributes) {
		String remarks = memberMerchantCheck.getRemarks();
		memberMerchantCheck = this.get(memberMerchantCheck.getId());
		memberMerchantCheck.setRemarks(remarks);
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setId(memberMerchantCheck.getMerchantCode());
		memberInfo.setStatus("1");
		User user = UserUtils.get(memberInfo.getId());
		if(null == user) {
			addMessage(redirectAttributes, "审核失败，用户不存在");
			return "redirect:"+Global.getAdminPath()+"/member/memberMerchantCheck/?repage";
		}
		memberInfoService.memberCheck(memberInfo);
		List<Role> roleList = user.getRoleList();
		if(null == roleList) {
			roleList = Lists.newArrayList();
		}
		roleList.add(new Role("1001"));
		user.setRoleList(roleList);
		systemService.saveUser(user);
		UserUtils.clearCache();
		User currUser = UserUtils.getUser();
		memberMerchantCheck.setCheckBy(currUser.getId());
		memberMerchantCheck.setCheckDate(new Date());
		memberMerchantCheck.setStatus("1");
		memberMerchantCheckService.save(memberMerchantCheck);
		//  用户注册返佣金
		//商家信息
		memberInfo = memberInfoService.get(memberInfo);
		//推荐人信息
		User referee = UserUtils.get(memberInfo.getRefereeId());
		//新增佣金记录
		//卖家推荐人佣金
		CommissionInfo commissionInfo = new CommissionInfo();
		//1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣
		commissionInfo.setType("3");
		commissionInfo.setUserId(referee.getId());
		commissionInfo.setProduceUserId(memberInfo.getId());
		commissionInfo.setOriginAmount(0.0);
		commissionInfo.setAmount(commissionConfigService.getCommissionAmount("3",0.0));
		commissionInfo.setUnionId("");
		commissionInfoService.save(commissionInfo);


		//新增账单流水记录
		//卖家账单流水
		AccountInfo merchantAccountInfo = new AccountInfo();
		merchantAccountInfo.setUserId(referee.getId());
		merchantAccountInfo.setType("1"); //收支类型 1：收入 2：支出
		merchantAccountInfo.setWay("1");//收支方式（1：佣金收益 2：销售收益） 支出（3：提现 4：结算）
		merchantAccountInfo.setAmount(commissionInfo.getAmount());
		merchantAccountInfo.setUnionId(commissionInfo.getId());
		merchantAccountInfo.setStatus("1");//状态 （1：已到账 2：未到账 3：未提现结算 4：已提现结算 ）
		merchantAccountInfo.setToAccountDate(new Date());
		accountInfoService.save(merchantAccountInfo);
		addMessage(redirectAttributes, "审核成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberMerchantCheck/?repage";
	}

	@RequestMapping(value = "checkReject")
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String checkReject(MemberMerchantCheck memberMerchantCheck, Model model, RedirectAttributes redirectAttributes) {
		String remarks = memberMerchantCheck.getRemarks();
		memberMerchantCheck = this.get(memberMerchantCheck.getId());
		memberMerchantCheck.setRemarks(remarks);
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setId(memberMerchantCheck.getMerchantCode());
		memberInfo.setStatus("2");
		memberInfoService.memberCheck(memberInfo);
		User currUser = UserUtils.getUser();
		memberMerchantCheck.setCheckBy(currUser.getId());
		memberMerchantCheck.setCheckDate(new Date());
		memberMerchantCheck.setStatus("2");
		memberMerchantCheckService.save(memberMerchantCheck);
		UserUtils.clearCache();
		addMessage(redirectAttributes, "审核成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberMerchantCheck/?repage";
	}

	@RequiresPermissions("member:memberMerchantCheck:edit")
	@RequestMapping(value = "save")
	public String save(MemberMerchantCheck memberMerchantCheck, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, memberMerchantCheck)){
			return form(memberMerchantCheck, model);
		}
		memberMerchantCheckService.save(memberMerchantCheck);
		addMessage(redirectAttributes, "保存商户审核成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberMerchantCheck/?repage";
	}
	
	@RequiresPermissions("member:memberMerchantCheck:edit")
	@RequestMapping(value = "delete")
	public String delete(MemberMerchantCheck memberMerchantCheck, RedirectAttributes redirectAttributes) {
		memberMerchantCheckService.delete(memberMerchantCheck);
		addMessage(redirectAttributes, "删除商户审核成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberMerchantCheck/?repage";
	}

}