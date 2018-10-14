/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.mall.modules.sys.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.mall.common.config.Global;
import com.mall.common.utils.Collections3;
import com.mall.common.utils.excel.annotation.ExcelField;
import com.mall.common.utils.excel.fieldtype.RoleListType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.mall.common.persistence.DataEntity;
import com.mall.common.supcan.annotation.treelist.cols.SupCol;

/**
 * 用户Entity
 * @author ThinkGem
 * @version 2013-12-05
 */
public class UserVo {

    private String loginName;// 登录名
    private String no;		// 工号
    private String name;	// 姓名
    private String email;	// 邮箱
    private String phone;	// 电话
    private String mobile;	// 手机
    private String photo;	// 头像

    //昵称
    private String nickName;
    //推荐码
    private String referee;
    //推荐人ID
    private String refereeId;
    //注册途径（0-注册页面自主注册，1-商户管理后台直接添加）
    private String registerWay;

    public UserVo(){}

    public UserVo(User user){
        this.setLoginName(user.getLoginName());
        this.setEmail(user.getEmail());
        this.setMobile(user.getMobile());
        this.setNickName(user.getNickName());
        this.setPhoto(user.getPhoto());
        this.setPhone(user.getPhone());
        this.setName(user.getName());
    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public String getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(String refereeId) {
        this.refereeId = refereeId;
    }

    public String getRegisterWay() {
        return registerWay;
    }

    public void setRegisterWay(String registerWay) {
        this.registerWay = registerWay;
    }
}