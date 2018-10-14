package com.mall.modules.member.api;

import com.google.common.collect.Lists;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.ApiExceptionHandleUtil;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.member.service.MemberVerifyCodeService;
import com.mall.modules.sys.entity.Office;
import com.mall.modules.sys.entity.Role;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.service.SystemService;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 会员接口
 *
 * @author wankang
 * @since 2018-10-11
 */
@RestController
@RequestMapping(value = "${adminPath}/api")
public class MemberInfoApi extends BaseController {

    @Autowired
    private MemberVerifyCodeService memberVerifyCodeService;

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void memberRegister(HttpServletRequest request, HttpServletResponse response) {
        // 解析前端数据
        String mobile = request.getParameter("mobile");
        String verifyCode = request.getParameter("verifyCode");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        String refereeCode = request.getParameter("refereeCode");
        String sex = request.getParameter("sex");
        String nickname = request.getParameter("nickname");
        try {
            // 数据验证
            if (StringUtils.isBlank(mobile)) {
                throw new ServiceException("手机号不能为空");
            } else if (StringUtils.isBlank(verifyCode)) {
                throw new ServiceException("验证码不能为空");
            } else if (StringUtils.isBlank(password)) {
                throw new ServiceException("密码不能为空");
            } else if (StringUtils.isBlank(repeatPassword)) {
                throw new ServiceException("密码不能为空");
            } else if (StringUtils.isBlank(refereeCode)) {
                throw new ServiceException("邀请人不能为空");
            } else {
                if(!MemberInfoService.isPhone(mobile)) {
                    throw new ServiceException("手机号码格式不正确");
                }
            }
            boolean validResult = memberVerifyCodeService.validVerifyCode(mobile, verifyCode);
            if (!validResult) {
                throw new ServiceException("验证码错误");
            }
            if (!password.equals(repeatPassword)) {
                throw new ServiceException("两次输入密码不一致");
            }
            if (UserUtils.getByLoginName(mobile) != null) {
                throw new ServiceException("用户已存在");
            }
            MemberInfo condition = new MemberInfo();
            condition.setReferee(refereeCode);
            MemberInfo refereeInfo = memberInfoService.get(condition);
            if (null == refereeInfo) {
                throw new ServiceException("邀请码无效");
            }
            String refereeId = refereeInfo.getId();

            // 初始化SysUser实体数据
            User user = new User();
            user.setLoginName(mobile);
            user.setMobile(mobile);
            // 普通会员固定归属公司 ID为1000
            user.setCompany(new Office("1000"));
            user.setOffice(new Office("1000"));
            user.setPassword(SystemService.entryptPassword(password));
            user.setName(nickname);
            // 赋予角色
            List<Role> roleList = Lists.newArrayList();
            roleList.add(new Role("1000"));
            user.setRoleList(roleList);
            // 保存用户信息
            systemService.saveUser(user);
            // 清除当前用户缓存
            if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
                UserUtils.clearCache();
            }

            // 初始化会员信息
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setNickname(nickname);
            memberInfo.setRefereeId(memberInfoService.genRefereeCode());
            memberInfo.setRefereeId(refereeId);
            memberInfo.setRegisterWay("0");
            memberInfo.setSex(sex);
            memberInfo.setBalance(0.00);
            memberInfo.preInsert();
            memberInfo.setId(user.getId());
            memberInfo.setReferee(MemberInfoService.genRefereeId());
            memberInfo.setIsNewRecord(true);
            memberInfoService.save(memberInfo);
            renderString(response, ResultGenerator.genSuccessResult("注册成功"));

            // todo 用户注册返佣金


        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    @RequestMapping(value = "genVerifyCode", method = RequestMethod.POST)
    public void genVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        String mobile = request.getParameter("mobile");
        try {
            if (StringUtils.isBlank(mobile)) {
                throw new ServiceException("手机号不能为空");
            }else {
                if(!MemberInfoService.isPhone(mobile)) {
                    throw new ServiceException("手机号码格式不正确");
                }
            }
            memberVerifyCodeService.sendVerifyCodeSms(mobile);
            renderString(response, ResultGenerator.genSuccessResult("验证码发送成功"));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

}
