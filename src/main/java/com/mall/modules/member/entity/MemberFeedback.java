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
	
	public MemberFeedback() {
		super();
	}

	public MemberFeedback(String id){
		super(id);
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Length(min=1, max=64, message="会员ID长度必须介于 1 和 64 之间")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	@Length(min=1, max=500, message="反馈信息长度必须介于 1 和 500 之间")
	public String getFeedbackDetail() {
		return feedbackDetail;
	}

	public void setFeedbackDetail(String feedbackDetail) {
		this.feedbackDetail = feedbackDetail;
	}
	
}