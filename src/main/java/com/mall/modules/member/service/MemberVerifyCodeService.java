package com.mall.modules.member.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.SmsAliyunUtils;
import com.mall.common.utils.StringUtils;
import com.mall.modules.member.dao.MemberVerifyCodeDao;
import com.mall.modules.member.entity.MemberVerifyCode;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 会员注册验证码Service
 *
 * @author wankang
 * @version 2018-10-11
 */
@Service
@Transactional(readOnly = true)
public class MemberVerifyCodeService extends CrudService<MemberVerifyCodeDao, MemberVerifyCode> {

    @Autowired
    private MemberVerifyCodeDao verifyCodeDao;

    public MemberVerifyCode get(String id) {
        return super.get(id);
    }

    public List<MemberVerifyCode> findList(MemberVerifyCode memberVerifyCode) {
        return super.findList(memberVerifyCode);
    }

    public Page<MemberVerifyCode> findPage(Page<MemberVerifyCode> page, MemberVerifyCode memberVerifyCode) {
        return super.findPage(page, memberVerifyCode);
    }

    @Transactional(readOnly = false)
    public void save(MemberVerifyCode memberVerifyCode) {
        super.save(memberVerifyCode);
    }

    @Transactional(readOnly = false)
    public void delete(MemberVerifyCode memberVerifyCode) {
        super.delete(memberVerifyCode);
    }

    @Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
    public void sendVerifyCodeSms(String telPhone, String type) throws ClientException, ServiceException {
        String templateCode = Global.getConfig("sms.template.member.registry");
        // 0-注册验证码 1-忘记密码短信验证码 2-修改用户敏感信息验证码
        if (StringUtils.isBlank(type)) {
            type = "0";
        } else if("1".equals(type)) {
            User user = UserUtils.getByLoginName(telPhone);
            if(null == user) {
                throw new ServiceException("用户不存在");
            }
            templateCode = Global.getConfig("sms.template.member.forgetpassword");
        }else if("2".equals(type)) {
            /*User user = UserUtils.getByLoginName(telPhone);
            if(null == user) {
                throw new ServiceException("用户不存在");
            }*/
            templateCode = Global.getConfig("sms.template.member.modifyinfo");
        }
        //生成短信验证码
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String templateParam = "{ \"code\":\"" + verifyCode + "\"}";

        SendSmsResponse sendSmsResponse = SmsAliyunUtils.sendMessage(telPhone, templateCode, templateParam);
        if (!"OK".equalsIgnoreCase(sendSmsResponse.getCode())) {
            throw new ServiceException("短信发送失败");
        }
        long currentTime = System.currentTimeMillis() + 5 * 60 * 1000;
        Date outTime = new Date(currentTime);
        MemberVerifyCode memberVerifyCode = new MemberVerifyCode();
        memberVerifyCode.setPhone(telPhone);
        memberVerifyCode.setVerifyCode(verifyCode);
        memberVerifyCode.setOutTime(outTime);
        memberVerifyCode.setType(type);
        this.save(memberVerifyCode);
    }

    public boolean validVerifyCode(String telPhone, String verifyCode, String type) {
        boolean flag = false;
        int count = verifyCodeDao.validVerifyCode(telPhone, verifyCode, type);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

}