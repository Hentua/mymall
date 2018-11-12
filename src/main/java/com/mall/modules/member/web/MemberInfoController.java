package com.mall.modules.member.web;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.member.service.MemberVerifyCodeService;
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
	private MemberVerifyCodeService memberVerifyCodeService;

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
		Page<MemberInfo> page = memberInfoService.findPage(new Page<MemberInfo>(request, response), memberInfo);
		model.addAttribute("page", page);
		return "modules/member/memberInfoList";
	}

	@RequiresPermissions("member:memberInfo:view")
	@RequestMapping(value = {"merchantMemberInfo"})
	public String merchantMemberInfo(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currUser = UserUtils.getUser();
		String refereeId = currUser.getId();
		memberInfo.setRefereeId(refereeId);
		Page<MemberInfo> page = memberInfoService.findPage(new Page<MemberInfo>(request, response), memberInfo);
		model.addAttribute("page", page);
		return "modules/member/merchantMemberInfoList";
	}

	@RequiresPermissions("member:memberInfo:view")
	@RequestMapping(value = "form")
	public String form(MemberInfo memberInfo, Model model) {
		model.addAttribute("memberInfo", memberInfo);
		return "modules/member/memberInfoForm";
	}

	@RequestMapping(value = "merchantData")
	public String merchantData(MemberInfo memberInfo, Model model) {
		model.addAttribute("memberInfo", memberInfo);
		return "modules/member/merchantData";
	}

	@RequestMapping(value = "submitMerchantData")
	public String submitMerchantData(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
		if(StringUtils.isBlank(memberInfo.getCompanyName())) {
			model.addAttribute("message", "公司名称不能为空");
			return merchantData(memberInfo, model);
		}
		if(StringUtils.isBlank(memberInfo.getPublicAccount())) {
			model.addAttribute("message", "对公账户不能为空");
			return merchantData(memberInfo, model);
		}
		if(StringUtils.isBlank(memberInfo.getPublicAccountBank())) {
			model.addAttribute("message", "对公账户开户行不能为空");
			return merchantData(memberInfo, model);
		}
		if(StringUtils.isBlank(memberInfo.getPublicAccountName())) {
			model.addAttribute("message", "对公账户名称不能为空");
			return merchantData(memberInfo, model);
		}
		if(StringUtils.isBlank(memberInfo.getProductLicense())) {
			model.addAttribute("message", "产品许可证不能为空");
			return merchantData(memberInfo, model);
		}
		if(StringUtils.isBlank(memberInfo.getBusinessLicenseImage())) {
			model.addAttribute("message", "营业执照不能为空");
			return merchantData(memberInfo, model);
		}
		memberInfoService.submitMerchantData(memberInfo);
		addMessage(redirectAttributes, "提交审核成功");
		memberInfo = this.get(memberInfo.getId());
		return merchantData(memberInfo, model);
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
		String verifyCode = memberInfo.getVerifyCode();
		String message = "";
		boolean flag = true;
		if(StringUtils.isBlank(verifyCode)) {
			flag = false;
			message = "验证码不能为空";
		} else if(!MemberInfoService.isPhone(mobile)) {
			flag = false;
			message = "手机号码格式不正确";
		}else if (UserUtils.getByLoginName(mobile) != null) {
			flag = false;
			message = "用户已存在";
		}else if(!memberVerifyCodeService.validVerifyCode(message, verifyCode, "0")) {
			flag = false;
			message = "验证码不正确";
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
		return "redirect:"+Global.getAdminPath()+"/member/memberInfo/merchantMemberInfo?repage";
	}

}