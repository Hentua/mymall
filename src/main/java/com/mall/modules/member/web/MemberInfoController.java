package com.mall.modules.member.web;

import com.google.common.collect.Lists;
import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.excel.ExportExcel;
import com.mall.common.web.BaseController;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.member.service.MemberVerifyCodeService;
import com.mall.modules.sys.entity.Office;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户信息Controller
 *
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
    public MemberInfo get(@RequestParam(required = false) String id) {
        MemberInfo entity = null;
        if (StringUtils.isNotBlank(id)) {
            MemberInfo queryCondition = new MemberInfo();
            queryCondition.setId(id);
            entity = memberInfoService.get(queryCondition);
        }
        if (entity == null) {
            entity = new MemberInfo();
        }
        return entity;
    }

    @RequiresPermissions("member:memberInfo:view")
    @RequestMapping(value = {"list", ""})
    public String list(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        memberInfo.setOperatorCode(UserUtils.getUser().getId());
        memberInfo.setUserType("1");
        Page<MemberInfo> page = memberInfoService.findPage(new Page<MemberInfo>(request, response), memberInfo);
        model.addAttribute("page", page);
        return "modules/member/memberInfoList";
    }

    @RequiresPermissions("member:memberInfo:view")
    @RequestMapping(value = {"allList"})
    public String allList(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        memberInfo.setUserType("1");
        Page<MemberInfo> page = memberInfoService.findPage(new Page<MemberInfo>(request, response), memberInfo);
        model.addAttribute("page", page);
        return "modules/member/allMemberInfoList";
    }

    @RequiresPermissions("member:memberInfo:view")
    @RequestMapping(value = {"merchantInfoListByPower"})
    public String merchantInfoListByPower(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        memberInfo.setUserType("1");
        Page<MemberInfo> page = memberInfoService.findListByPower(new Page<MemberInfo>(request, response), memberInfo);
        model.addAttribute("page", page);
        return "modules/member/merchantInfoListByPower";
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
    @RequestMapping(value = {"operatorMemberInfo"})
    public String operatorMemberInfo(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<MemberInfo> page = memberInfoService.findPage(new Page<MemberInfo>(request, response), memberInfo);
        model.addAttribute("page", page);
        return "modules/member/operatorMemberInfoList";
    }

    @RequestMapping(value = {"exportMerchantMemberInfo"})
    public void exportMerchantMemberInfo(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        User currUser = UserUtils.getUser();
        String refereeId = currUser.getId();
        memberInfo.setRefereeId(refereeId);
        List<MemberInfo> memberInfos = memberInfoService.findList(memberInfo);
        ExportExcel exportExcel = new ExportExcel("会员信息", MemberInfo.class);
        try {
            exportExcel.setDataList(memberInfos);
            exportExcel.write(response, "会员信息.xlsx");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"exportOperatorMemberInfo"})
    public void exportOperatorMemberInfo(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<MemberInfo> memberInfos = memberInfoService.findList(memberInfo);
        ExportExcel exportExcel = new ExportExcel("会员信息", MemberInfo.class);
        try {
            exportExcel.setDataList(memberInfos);
            exportExcel.write(response, "会员信息.xlsx");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresPermissions("member:memberInfo:view")
    @RequestMapping(value = "form")
    public String form(MemberInfo memberInfo, Model model) {
        model.addAttribute("memberInfo", memberInfo);
        return "modules/member/memberInfoForm";
    }

    @RequiresPermissions("member:memberInfo:view")
    @RequestMapping(value = "allListForm")
    public String allListForm(MemberInfo memberInfo, Model model) {
        model.addAttribute("memberInfo", memberInfo);
        return "modules/member/modifyMemberOperatorForm";
    }

    @RequestMapping(value = "uncheckMerchant")
    public String uncheckMerchant(MemberInfo memberInfo, Model model) {
        memberInfoService.uncheckStatus(memberInfo.getId());
        model.addAttribute("message", "取消商户审核成功");
        return "redirect:" + Global.getAdminPath() + "/member/memberInfo/merchantInfoListByPower?repage";
    }

    @RequiresPermissions("member:memberInfo:edit")
    @RequestMapping(value = "modifyMemberOperator")
    public String modifyMemberOperator(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
        if (null == memberInfo) {
            addMessage(redirectAttributes, "表单信息不合法");
            return "redirect:" + Global.getAdminPath() + "/member/memberInfo/merchantInfoListByPower?repage";
        }
        if (StringUtils.isBlank(memberInfo.getId())) {
            addMessage(redirectAttributes, "未选择要编辑的商户");
            return "redirect:" + Global.getAdminPath() + "/member/memberInfo/merchantInfoListByPower?repage";
        }
        if (StringUtils.isBlank(memberInfo.getOperatorCode())) {
            model.addAttribute("message", "未选择商户归属的运营");
            memberInfo = this.get(memberInfo.getId());
            return allListForm(memberInfo, model);
        }
        memberInfoService.modifyMemberOperator(memberInfo);
        addMessage(redirectAttributes, "操作成功");
        return "redirect:" + Global.getAdminPath() + "/member/memberInfo/merchantInfoListByPower?repage";
    }

    @RequiresPermissions("member:memberInfo:edit")
    @RequestMapping(value = "disableUser")
    public String disableUser(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
        if (null == memberInfo) {
            addMessage(redirectAttributes, "表单信息不合法");
            return "redirect:" + Global.getAdminPath() + "/member/memberInfo/merchantInfoListByPower?repage";
        }
        if (StringUtils.isBlank(memberInfo.getId())) {
            addMessage(redirectAttributes, "未选择要禁止登录的会员");
            return "redirect:" + Global.getAdminPath() + "/member/memberInfo/merchantInfoListByPower?repage";
        }
        User user = UserUtils.get(memberInfo.getId());
        if (null == user || "0".equals(user.getLoginFlag())) {
            addMessage(redirectAttributes, "操作失败，用户信息不合法");
            return "redirect:" + Global.getAdminPath() + "/member/memberInfo/merchantInfoListByPower?repage";
        }
        user.setLoginFlag("0");
        systemService.modifyLoginFlag(user);
        addMessage(redirectAttributes, "操作成功");
        return "redirect:" + Global.getAdminPath() + "/member/memberInfo/merchantInfoListByPower?repage";
    }

    @RequiresPermissions("member:memberInfo:edit")
    @RequestMapping(value = "enableUser")
    public String enableUser(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
        if (null == memberInfo) {
            addMessage(redirectAttributes, "表单信息不合法");
            return "redirect:" + Global.getAdminPath() + "/member/memberInfo/merchantInfoListByPower?repage";
        }
        if (StringUtils.isBlank(memberInfo.getId())) {
            addMessage(redirectAttributes, "未选择要允许登录的会员");
            return "redirect:" + Global.getAdminPath() + "/member/memberInfo/merchantInfoListByPower?repage";
        }
        User user = UserUtils.get(memberInfo.getId());
        if (null == user || "1".equals(user.getLoginFlag())) {
            addMessage(redirectAttributes, "操作失败，用户信息不合法");
            return "redirect:" + Global.getAdminPath() + "/member/memberInfo/merchantInfoListByPower?repage";
        }
        user.setLoginFlag("1");
        systemService.modifyLoginFlag(user);
        addMessage(redirectAttributes, "操作成功");
        return "redirect:" + Global.getAdminPath() + "/member/memberInfo/merchantInfoListByPower?repage";
    }

    @RequestMapping(value = "merchantData")
    public String merchantData(MemberInfo memberInfo, Model model) {
        model.addAttribute("memberInfo", memberInfo);
        return "modules/member/merchantData";
    }

    @RequestMapping(value = "modifyMerchantType")
    public String modifyMerchantType(MemberInfo memberInfo, Model model) {
        memberInfoService.uncheckStatus(memberInfo.getId());
        memberInfo = memberInfoService.get(memberInfo);
        model.addAttribute("memberInfo", memberInfo);
        return merchantData(memberInfo, model);
    }

    @RequestMapping(value = "submitMerchantData")
    public String submitMerchantData(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
        if("1".equals(memberInfo.getMerchantType())) {
            if (StringUtils.isBlank(memberInfo.getCompanyName())) {
                model.addAttribute("message", "公司名称不能为空");
                return merchantData(memberInfo, model);
            }
            if (StringUtils.isBlank(memberInfo.getPublicAccount())) {
                model.addAttribute("message", "对公账户不能为空");
                return merchantData(memberInfo, model);
            }
            if (StringUtils.isBlank(memberInfo.getPublicAccountBank())) {
                model.addAttribute("message", "对公账户开户行不能为空");
                return merchantData(memberInfo, model);
            }
            if (StringUtils.isBlank(memberInfo.getPublicAccountName())) {
                model.addAttribute("message", "对公账户名称不能为空");
                return merchantData(memberInfo, model);
            }
            if (StringUtils.isBlank(memberInfo.getProductLicense())) {
                model.addAttribute("message", "产品许可证不能为空");
                return merchantData(memberInfo, model);
            }
            if (StringUtils.isBlank(memberInfo.getBusinessLicenseImage())) {
                model.addAttribute("message", "营业执照不能为空");
                return merchantData(memberInfo, model);
            }
            if (StringUtils.isBlank(memberInfo.getPermit())) {
                model.addAttribute("message", "开户许可证不能为空");
                return merchantData(memberInfo, model);
            }
        }else{
            if (StringUtils.isBlank(memberInfo.getPersonAccount())) {
                model.addAttribute("message", "个人银行账户不能为空");
                return merchantData(memberInfo, model);
            }
            if (StringUtils.isBlank(memberInfo.getPersonAccountBank())) {
                model.addAttribute("message", "个人银行账户开户行不能为空");
                return merchantData(memberInfo, model);
            }
            if (StringUtils.isBlank(memberInfo.getPersonAccountName())) {
                model.addAttribute("message", "个人银行账户名称不能为空");
                return merchantData(memberInfo, model);
            }
        }
        memberInfoService.submitMerchantData(memberInfo);
        addMessage(redirectAttributes, "提交审核成功");
        memberInfo = this.get(memberInfo.getId());
        return merchantData(memberInfo, model);
    }

    @RequiresPermissions("member:memberInfo:edit")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @RequestMapping(value = "save")
    public String save(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, memberInfo)) {
            return form(memberInfo, model);
        }
        String mobile = memberInfo.getMobile();
        String password = memberInfo.getPassword();
        String repeatPassword = memberInfo.getRepeatPassword();
        String nickname = memberInfo.getNickname();
        String verifyCode = memberInfo.getVerifyCode();
        String message = "";
        boolean flag = true;
        if (StringUtils.isBlank(verifyCode)) {
            flag = false;
            message = "验证码不能为空";
        } else if (!MemberInfoService.isPhone(mobile)) {
            flag = false;
            message = "手机号码格式不正确";
        } else if (UserUtils.getByLoginName(mobile) != null) {
            flag = false;
            message = "用户已存在";
        } else if (!memberVerifyCodeService.validVerifyCode(mobile, verifyCode, "0")) {
            flag = false;
            message = "验证码不正确";
        }
        if (!password.equals(repeatPassword)) {
            flag = false;
            message = "两次输入密码不一致";
        }
        if (!flag) {
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
        List<Role> roleList = Lists.newArrayList();
        roleList.add(new Role("2000"));
        user.setRoleList(roleList);
        // 保存用户信息
        systemService.saveUser(user);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
            UserUtils.clearCache(user);
        }

        // 初始化会员信息
        memberInfo.setRefereeId(refereeId);
        memberInfo.setRegisterWay("1");
        memberInfo.setBalance(0.00);
        memberInfo.preInsert();
        memberInfo.setId(user.getId());
        memberInfo.setReferee(memberInfoService.genRefereeId());
        memberInfo.setStatus("0");
        memberInfo.setIsNewRecord(true);
        memberInfoService.save(memberInfo);
        addMessage(redirectAttributes, "保存会员信息成功");
        return "redirect:" + Global.getAdminPath() + "/member/memberInfo/merchantMemberInfo?repage";
    }

    /**
     * 获取会员信息
     *
     * @param request  请求体
     * @param response 响应体
     */
    @ResponseBody
    @RequestMapping(value = "getMemberInfo")
    public void getMemberInfo(HttpServletRequest request, HttpServletResponse response) {
        String referee = request.getParameter("referee");
        MemberInfo queryCondition = new MemberInfo();
        queryCondition.setReferee(referee);
        MemberInfo memberInfo = memberInfoService.get(queryCondition);
        renderString(response, memberInfo);
    }

    @RequestMapping(value = "checkPayPasswordForm")
    public String checkPayPasswordForm(MemberInfo memberInfo, HttpServletRequest request, Model model, HttpServletResponse response) {
        String failedCallbackUrl = request.getParameter("failedCallbackUrl");
        String successCallbackUrl = request.getParameter("successCallbackUrl");

        MemberInfo memberInfoData = memberInfoService.get(memberInfo);
        if (null == memberInfoData) {
            throw new ServiceException("获取会员信息失败");
        }
        model.addAttribute("memberInfo", memberInfoData);
        model.addAttribute("failedCallbackUrl", failedCallbackUrl);
        model.addAttribute("successCallbackUrl", successCallbackUrl);
        return "modules/member/checkPayPassword";
    }

}