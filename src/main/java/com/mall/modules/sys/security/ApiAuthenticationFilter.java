/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.mall.modules.sys.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.mall.common.config.Global;
import com.mall.common.security.Digests;
import com.mall.common.utils.*;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.service.SystemService;
import com.mall.modules.sys.utils.UserUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * app请求鉴权
 * @author hub
 * @version 2018-10-10
 */
@Service
public class ApiAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

    public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
    public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
    public static final String DEFAULT_MESSAGE_PARAM = "message";
    private static final Logger log = LoggerFactory.getLogger(ApiAuthenticationFilter.class);
    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
    private String messageParam = DEFAULT_MESSAGE_PARAM;

    private SystemService systemService;

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        if (password==null){
            password = "";
        }
        boolean rememberMe = isRememberMe(request);
        String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
        String captcha = getCaptcha(request);
        boolean mobile = isMobileLogin(request);
        return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile);
    }

    /**
     * 获取登录用户名
     * @param request
     * @return
     */
    protected String getUsername(ServletRequest request) {
        String username = super.getUsername(request);
        if (StringUtils.isBlank(username)){
            username = StringUtils.toString(request.getAttribute(getUsernameParam()), StringUtils.EMPTY);
        }
        return username;
    }

    /**
     * 获取登录密码
     * @param request
     * @return
     */
    @Override
    protected String getPassword(ServletRequest request) {
        String password = super.getPassword(request);
        if (StringUtils.isBlank(password)){
            password = StringUtils.toString(request.getAttribute(getPasswordParam()), StringUtils.EMPTY);
        }
        return password;
    }

    /**
     * 获取缓存信息
     * @param request
     * @return
     */
    @Override
    protected boolean isRememberMe(ServletRequest request) {
        String isRememberMe = WebUtils.getCleanParam(request, getRememberMeParam());
        if (StringUtils.isBlank(isRememberMe)){
            isRememberMe = StringUtils.toString(request.getAttribute(getRememberMeParam()), StringUtils.EMPTY);
        }
        return StringUtils.toBoolean(isRememberMe);
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    public String getMobileLoginParam() {
        return mobileLoginParam;
    }

    protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }

    public String getMessageParam() {
        return messageParam;
    }

    /**
     * 登录失败
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
                                     AuthenticationException e, ServletRequest request, ServletResponse response) {
//        String className = e.getClass().getName(), message = "";
//        if (IncorrectCredentialsException.class.getName().equals(className)
//                || UnknownAccountException.class.getName().equals(className)){
//            message = "用户或密码错误, 请重试.";
//        }
//        else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
//            message = StringUtils.replace(e.getMessage(), "msg:", "");
//        }
//        else{
//            message = "系统出现点问题，请稍后再试！";
//            e.printStackTrace(); // 输出到控制台
//        }
//        request.setAttribute(getFailureKeyAttribute(), className);
//        request.setAttribute(getMessageParam(), message);
        return false;
    }


    /**
     * 登录成功
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,
                                     Subject subject, ServletRequest request, ServletResponse response) {
        String tokenStr = getToken(subject);
        EhCacheUtils.put(tokenStr,subject.getPrincipal());
        request.setAttribute("token",tokenStr);
        return true;
    }

    /**
     * 获取token字符串
     * @param subject
     * @return
     */
    private String getToken(Subject subject){
        SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal)subject.getPrincipal();
        String secretKey = Global.getConfig("shiro.app.secretKey");
        String token= DigestUtils.md5Hex(secretKey + principal.getLoginName() + DateUtils.getDate("yyyyMM"));
//        String token = new String(Digests.md5((secretKey + principal.getLoginName() + DateUtils.getDate("yyyyMMdd")).getBytes()));
        return token;
    }

    /**
     * 获取系统业务对象
     */
    public SystemService getSystemService() {
        if (systemService == null){
            systemService = SpringContextHolder.getBean(SystemService.class);
        }
        return systemService;
    }


    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
                    "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    /**
     * APP登录 请求鉴权
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request,ServletResponse response) throws Exception {
        log.info("==================APP请求鉴权=====================");
        String uri = ((HttpServletRequest) request).getServletPath();
        Enumeration enu=request.getParameterNames();
        JSONObject jo =new JSONObject();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            jo.put(paraName,request.getParameter(paraName));
//            System.out.println(paraName+": "+request.getParameter(paraName));
        }
        log.info("url = "+uri);
        log.info("jsonparam = "+jo.toJSONString());
        //登录验证
        if (uri.indexOf("/login") != -1 && uri.indexOf("/loginOut") == -1) {
            boolean executeLogin = this.executeLogin(request, response);
            if(!executeLogin){
                responseWrite(response,ResultGenerator.genFailResult("账号或密码错误"));
                return false;
            }
            return true;
        }
        //接口鉴权
        if (this.isAuthenticated(request)) {
            return true;
        } else {
            responseWrite(response,ResultGenerator.genFailResult("请先登录", ResultStatus.ACCESS_DENIED));
            return false;
        }
    }

    /**
     * 验证token
     * @param request
     * @return
     */
    private boolean isAuthenticated(ServletRequest request){
        if(null == UserUtils.getPrincipal(request)){
            return false;
        }
        return true;
    }


    /**
     * response write
     * @param response
     * @param result
     */
    public  void responseWrite(ServletResponse response,Result result){
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(result.toString());
            out.flush();
            out.close();
        } catch (Exception e) {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e2) {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}