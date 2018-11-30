package com.mall.modules.commission.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 佣金提现Entity
 * @author hub
 * @version 2018-11-15
 */
public class CommissionTakeOut extends DataEntity<CommissionTakeOut> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String userName;		// 用户id
	private Double amount;		// 提现金额
	private String bankAccount;		// 银行账户
	private String bankAccountName;		// 开户人名称
	private String bankName;		// 开户行
	private String checkStatus;		// 提现审核状态：1未审核 2已审核
    private String userMobile;
    private String checkRemark;

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	public CommissionTakeOut() {
		super();
	}

	public CommissionTakeOut(String id){
		super(id);
	}


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

	@Length(min=0, max=64, message="用户id长度必须介于 0 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=100, message="银行账户长度必须介于 0 和 100 之间")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	@Length(min=0, max=50, message="开户人名称长度必须介于 0 和 50 之间")
	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	
	@Length(min=0, max=200, message="开户行长度必须介于 0 和 200 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=3, message="提现审核状态：1未审核 2已审核长度必须介于 0 和 3 之间")
	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
}