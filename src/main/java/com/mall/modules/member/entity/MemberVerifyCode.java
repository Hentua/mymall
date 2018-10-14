package com.mall.modules.member.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.mall.common.persistence.DataEntity;

/**
 * 会员注册验证码Entity
 * @author wankang
 * @version 2018-10-11
 */
public class MemberVerifyCode extends DataEntity<MemberVerifyCode> {
	
	private static final long serialVersionUID = 1L;
	private String phone;		// 手机号
	private String verifyCode;		// 验证码
	private Date outTime;		// 超时时间
	
	public MemberVerifyCode() {
		super();
	}

	public MemberVerifyCode(String id){
		super(id);
	}

	@Length(min=0, max=20, message="手机号长度必须介于 0 和 20 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=10, message="验证码长度必须介于 0 和 10 之间")
	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	
}