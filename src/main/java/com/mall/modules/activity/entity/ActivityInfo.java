package com.mall.modules.activity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mall.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 活动配置Entity
 * @author wankang
 * @version 2018-11-14
 */
public class ActivityInfo extends DataEntity<ActivityInfo> {
	
	private static final long serialVersionUID = 1L;
	private String activityName;		// 活动名称
	private Date startDate;		// 活动开始时间
	private Date endDate;		// 活动结束时间
	private String status;		// 0-下线 1-上线 2-预上线 到活动开始时间时自动上线
	private Double discountRate;		// 折扣比例
	private String couponFlag;		// 是否可同时使用优惠券 0-不可使用 1-可使用
	
	public ActivityInfo() {
		super();
	}

	public ActivityInfo(String id){
		super(id);
	}

	@Length(min=1, max=20, message="活动名称长度必须介于 1 和 20 之间")
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="活动开始时间不能为空")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="活动结束时间不能为空")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=1, max=1, message="0-下线 1-上线长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@NotNull(message="折扣比例不能为空")
	public Double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}
	
	@Length(min=1, max=1, message="是否可同时使用优惠券 0-不可使用 1-可使用长度必须介于 1 和 1 之间")
	public String getCouponFlag() {
		return couponFlag;
	}

	public void setCouponFlag(String couponFlag) {
		this.couponFlag = couponFlag;
	}
	
}