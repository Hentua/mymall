package com.mall.modules.member.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.SmsAliyunUtils;
import com.mall.modules.member.dao.MemberVerifyCodeDao;
import com.mall.modules.member.entity.MemberVerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void sendVerifyCodeSms(String telPhone) throws ClientException {

        String templateCode = "SMS_147417546";
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
        this.save(memberVerifyCode);
    }

    public boolean validVerifyCode(String telPhone, String verifyCode) {
        boolean flag = false;
        int count = verifyCodeDao.validVerifyCode(telPhone, verifyCode);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

}