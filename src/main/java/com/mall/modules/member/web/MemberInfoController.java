package com.mall.modules.member.web;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.account.entity.AccountInfo;
import com.mall.modules.account.service.AccountInfoService;
import com.mall.modules.commission.entity.CommissionInfo;
import com.mall.modules.commission.service.CommissionConfigService;
import com.mall.modules.commission.service.CommissionInfoService;
import com.mall.modules.coupon.entity.CouponConfig;
import com.mall.modules.coupon.service.CouponConfigService;
import com.mall.modules.coupon.service.CouponCustomerService;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.sys.entity.Office;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.service.SystemService;
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
import java.util.Date;

/**
 * 用户信息Controller
 * @author wankang
 * @version 2018-10-10
 */
@Controller
@RequestMapping(value = "${adminPath}/member/memberInfo")
public class MemberInfoController extends BaseController {

	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CouponConfigService couponConfigService;
	@Autowired
	private CouponCustomerService couponCustomerService;

	//佣金详情service
	@Autowired
	private CommissionInfoService commissionInfoService;

	@Autowired
	private AccountInfoService accountInfoService;

	//佣金配置service
	@Autowired
	private CommissionConfigService commissionConfigService;
	
	@ModelAttribute
	public MemberInfo get(@RequestParam(required=false) String id) {
		MemberInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			MemberInfo queryCondition = new MemberInfo();
			queryCondition.setId(id);
			entity = memberInfoService.get(queryCondition);
		}
		if (entity == null){
			entity = new MemberInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("member:memberInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currUser = UserUtils.getUser();
		String refereeId = currUser.getId();
		memberInfo.setRefereeId(refereeId);
		Page<MemberInfo> page = memberInfoService.findPage(new Page<MemberInfo>(request, response), memberInfo); 
		model.addAttribute("page", page);
		return "modules/member/memberInfoList";
	}

	@RequiresPermissions("member:memberInfo:view")
	@RequestMapping(value = {"memberInfoCheckList"})
	public String memberInfoCheckList(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberInfo> page = memberInfoService.findPage(new Page<MemberInfo>(request, response), memberInfo);
		model.addAttribute("page", page);
		return "modules/member/memberInfoCheckList";
	}

	@RequiresPermissions("member:memberInfo:edit")
	@RequestMapping(value = "checkPass")
	public String checkPass(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {

		memberInfo.setStatus("1");
		memberInfoService.memberCheck(memberInfo);
		UserUtils.clearCache();
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
		return "redirect:"+Global.getAdminPath()+"/member/memberInfo/memberInfoCheckList/?repage";
	}

	@RequiresPermissions("member:memberInfo:edit")
	@RequestMapping(value = "checkReject")
	public String checkReject(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
		memberInfo.setStatus("2");
		memberInfoService.memberCheck(memberInfo);
		User user = UserUtils.get(memberInfo.getId());
		systemService.deleteUser(user);
		UserUtils.clearCache();
		addMessage(redirectAttributes, "审核成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberInfo/memberInfoCheckList/?repage";
	}

	@RequiresPermissions("member:memberInfo:view")
	@RequestMapping(value = "form")
	public String form(MemberInfo memberInfo, Model model) {
		model.addAttribute("memberInfo", memberInfo);
		return "modules/member/memberInfoForm";
	}

	@RequiresPermissions("member:memberInfo:edit")
	@RequestMapping(value = "couponDistribution")
	public String couponDistribution(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		CouponConfig queryCondition = new CouponConfig();
		queryCondition.setStatus("0");
		Page<CouponConfig> page = couponConfigService.findPage(new Page<CouponConfig>(request, response), queryCondition);
		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("couponConfigs", page);
		return "modules/member/memberCoupon";
	}

	@RequiresPermissions("member:memberInfo:edit")
	@RequestMapping(value = "memberCouponDistribution")
	public String memberCouponDistribution(Model model, HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String couponId = request.getParameter("couponId");
		MemberInfo memberInfo = this.get(id);
		try {
			couponCustomerService.customerCouponDistribution(id, couponId);
		}catch (Exception e) {
			if(e instanceof ServiceException) {
				model.addAttribute("message", e.getMessage());
				return couponDistribution(memberInfo, request, response, model);
			}else {
				e.printStackTrace();
				model.addAttribute("message", "分配失败");
				return couponDistribution(memberInfo, request, response, model);
			}
		}
		model.addAttribute("message", "优惠券分配成功");
		return couponDistribution(memberInfo, request, response, model);
	}

	@RequiresPermissions("member:memberInfo:edit")
	@RequestMapping(value = "save")
	public String save(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, memberInfo)){
			return form(memberInfo, model);
		}
		String mobile = memberInfo.getMobile();
		String password = memberInfo.getPassword();
		String repeatPassword = memberInfo.getRepeatPassword();
		String nickname = memberInfo.getNickname();
		String message = "";
		boolean flag = true;
		if(!MemberInfoService.isPhone(mobile)) {
			flag = false;
			message = "手机号码格式不正确";
		}else if (UserUtils.getByLoginName(mobile) != null) {
			flag = false;
			message = "用户已存在";
		}
		if(!password.equals(repeatPassword)) {
			flag = false;
			message = "两次输入密码不一致";
		}
		if(!flag) {
			model.addAttribute("message", message);
			return form(memberInfo, model);
		}
		User currUser = UserUtils.getUser();
		String refereeId = currUser.getId();

		// 初始化SysUser实体数据
		User user = new User();
		user.setLoginName(mobile);
		user.setMobile(mobile);
		// 普通会员固定归属公司 ID为1000
		user.setCompany(new Office("1000"));
		user.setOffice(new Office("1000"));
		user.setPassword(SystemService.entryptPassword(password));
		user.setName(nickname);
		user.setUserType("0");
		// 赋予角色
//		List<Role> roleList = Lists.newArrayList();
//		roleList.add(new Role("1000"));
//		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
			UserUtils.clearCache();
		}

		// 初始化会员信息
		memberInfo.setRefereeId(refereeId);
		memberInfo.setRegisterWay("1");
		memberInfo.setBalance(0.00);
		memberInfo.preInsert();
		memberInfo.setId(user.getId());
		memberInfo.setReferee(MemberInfoService.genRefereeId());
		memberInfo.setStatus("0");
		memberInfo.setIsNewRecord(true);
		memberInfoService.save(memberInfo);
		addMessage(redirectAttributes, "保存会员信息成功");
		return "redirect:"+Global.getAdminPath()+"/member/memberInfo/?repage";
	}

}