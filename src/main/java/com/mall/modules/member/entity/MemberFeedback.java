package com.mall.modules.member.entity;

import com.mall.common.utils.StringUtils;
import com.mall.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

import java.util.Date;

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
	private String customerId; // 会员ID
	private String status; // 反馈状态

	private Date startDate;
	private Date endDate;

	public MemberFeedback() {
		super();
	}

	public MemberFeedback(String id){
		super(id);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@ExcelField(title = "状态", sort = 6)
	public String getStatusZh() {
		String statusZh = "";
		if(StringUtils.isNotBlank(this.reply)) {
			statusZh = "已处理";
		}else {
			statusZh = "未处理";
		}
		return statusZh;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ExcelField(title = "会员ID", sort = 2)
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title = "处理信息", sort = 5)
	@Length(min=1, max=500, message="反馈信息长度必须介于 1 和 500 之间")
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	@ExcelField(title = "会员昵称", sort = 1)
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

	@ExcelField(title = "反馈信息", sort = 4)
	public String getFeedbackDetail() {
		return feedbackDetail;
	}

	public void setFeedbackDetail(String feedbackDetail) {
		this.feedbackDetail = feedbackDetail;
	}

	@ExcelField(title = "反馈时间", sort = 3)
	@Override
	public Date getCreateDate() {
		return createDate;
	}
	
}