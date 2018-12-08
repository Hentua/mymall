package com.mall.modules.member.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 店铺信息审核Entity
 * @author hub
 * @version 2018-12-05
 */
public class MerchantInfoCheck extends DataEntity<MerchantInfoCheck> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家id
	private String merchantName;		// 店铺名称
	private String avatar;		// 头像地址
	private String merchantHeadImg;		// 头图
	private String merchantServicePhone;		// 客服电话
	private String checkStatus;		// 审核状态1：待审核 2：已审核 3:已驳回
	private String checkRemark;		// 审核备注
	private String userMobile;
	private String userName;


	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public MerchantInfoCheck() {
		super();
	}

	public MerchantInfoCheck(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家id长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=255, message="店铺名称长度必须介于 0 和 255 之间")
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	@Length(min=0, max=500, message="头像地址长度必须介于 0 和 500 之间")
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Length(min=0, max=500, message="头图长度必须介于 0 和 500 之间")
	public String getMerchantHeadImg() {
		return merchantHeadImg;
	}

	public void setMerchantHeadImg(String merchantHeadImg) {
		this.merchantHeadImg = merchantHeadImg;
	}
	
	@Length(min=1, max=255, message="客服电话长度必须介于 1 和 255 之间")
	public String getMerchantServicePhone() {
		return merchantServicePhone;
	}

	public void setMerchantServicePhone(String merchantServicePhone) {
		this.merchantServicePhone = merchantServicePhone;
	}
	
	@Length(min=0, max=10, message="审核状态1：待审核 2：已审核 3:已驳回长度必须介于 0 和 10 之间")
	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	@Length(min=0, max=500, message="审核备注长度必须介于 0 和 500 之间")
	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	
}