package com.mall.modules.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用户信息Entity
 * @author wankang
 * @version 2018-10-10
 */
public class MemberInfo extends DataEntity<MemberInfo> {
	
	private static final long serialVersionUID = 1L;
	private String referee;		// 自己的推荐码
	private String refereeId;		// 推荐人ID
	private Double balance;		// 账户余额
	private Double commission;  //佣金
	private String avatar;		// 头像地址
	private String registerWay;		// 注册途径（0-注册页面自主注册，1-商户管理后台直接添加）
	private String nickname; // 昵称
	private String sex; // 性别0-男， 1-女

	private String mobile; // 手机号码

	private String status; // 会员当前状态 0-审核中， 1-已生效， 2-审核未通过
	private String remarks; // 备注
	private String refereeName; // 推荐人名称

	@JsonIgnore
	private String password;
	@JsonIgnore
	private String repeatPassword;
	@JsonIgnore
	private String payPassword; // 支付密码

	public MemberInfo() {
		super();
	}

	public MemberInfo(String id){
		super(id);
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getRefereeName() {
		return refereeName;
	}

	public void setRefereeName(String refereeName) {
		this.refereeName = refereeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@NotEmpty(message = "密码不能为空")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotEmpty(message = "密码不能为空")
	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Length(min=1, max=20, message="自己的推荐码长度必须介于 1 和 20 之间")
	public String getReferee() {
		return referee;
	}

	public void setReferee(String referee) {
		this.referee = referee;
	}
	
	@Length(min=0, max=64, message="推荐人ID长度必须介于 0 和 64 之间")
	public String getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(String refereeId) {
		this.refereeId = refereeId;
	}
	
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	@Length(min=0, max=200, message="头像地址长度必须介于 0 和 200 之间")
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Length(min=1, max=2, message="注册途径（0-注册页面自主注册，1-商户管理后台直接添加）长度必须介于 1 和 2 之间")
	public String getRegisterWay() {
		return registerWay;
	}

	public void setRegisterWay(String registerWay) {
		this.registerWay = registerWay;
	}
	
}