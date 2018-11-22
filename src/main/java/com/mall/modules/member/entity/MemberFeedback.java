package com.mall.modules.member.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 会员反馈信息Entity
 * @author wankang
 * @version 2018-10-29
 */
public class MemberFeedback extends DataEntity<MemberFeedback> {
	
	private static final long serialVersionUID = 1L;
	private String customerCode;		// 会员ID
	private String feedbackDetail;		// 反馈信息
	private String customerName; // 会员昵称
	private String reply; // 运营反馈
	private String mobile; // 会员账号

	public MemberFeedback() {
		super();
	}

	public MemberFeedback(String id){
		super(id);
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Length(min=1, max=500, message="反馈信息长度必须介于 1 和 500 之间")
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getFeedbackDetail() {
		return feedbackDetail;
	}

	public void setFeedbackDetail(String feedbackDetail) {
		this.feedbackDetail = feedbackDetail;
	}
	
}