package com.mall.modules.member.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.member.entity.MemberVerifyCode;
import org.apache.ibatis.annotations.Param;

/**
 * 会员注册验证码DAO接口
 * @author wankang
 * @version 2018-10-11
 */
@MyBatisDao
public interface MemberVerifyCodeDao extends CrudDao<MemberVerifyCode> {

    /**
     * 验证短信验证码是否正确
     * @param telPhone 手机号
     * @param verifyCode 验证码
     * @return 查询的数据
     */
    public int validVerifyCode(@Param("telPhone") String telPhone, @Param("verifyCode") String verifyCode);
	
}