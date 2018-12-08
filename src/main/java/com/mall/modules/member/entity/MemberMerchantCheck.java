package com.mall.modules.member.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mall.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 商户审核Entity
 * @author wankang
 * @version 2018-11-12
 */
public class MemberMerchantCheck extends DataEntity<MemberMerchantCheck> {
	
	private static final long serialVersionUID = 1L;
	private String merchantCode;		// 商户ID
	private String status;		// 审核状态
	private String checkBy;		// 审核人
	private Date checkDate;		// 审核时间

	private String merchantName; // 商户名称
	private String memberStatus; // 会员状态
	private String memberType; // 用户类型
	private String mobile; // 用户手机号码
	private String registerWay; // 注册途径
	private String merchantType; // 商户类型
	private String merchantRefereeName; // 商户推荐人名称
	private String merchantRefereeAccount; // 商户推荐人账号

	private MemberInfo memberInfo;

	public MemberMerchantCheck() {
		super();
	}

	public MemberMerchantCheck(String id){
		super(id);
	}

	public String getMerchantRefereeName() {
		return merchantRefereeName;
	}

	public void setMerchantRefereeName(String merchantRefereeName) {
		this.merchantRefereeName = merchantRefereeName;
	}

	public String getMerchantRefereeAccount() {
		return merchantRefereeAccount;
	}

	public void setMerchantRefereeAccount(String merchantRefereeAccount) {
		this.merchantRefereeAccount = merchantRefereeAccount;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getRegisterWay() {
		return registerWay;
	}

	public void setRegisterWay(String registerWay) {
		this.registerWay = registerWay;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Length(min=1, max=64, message="商户ID长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	@Length(min=1, max=1, message="审核状态长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=64, message="审核人长度必须介于 0 和 64 之间")
	public String getCheckBy() {
		return checkBy;
	}

	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
}