package com.mall.modules.member.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 用户银行卡Entity
 * @author hub
 * @version 2018-11-29
 */
public class MemberBankAccount extends DataEntity<MemberBankAccount> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 所属用户
	private String bankAccount;		// 银行卡号
	private String bankAccountName;		// 开户人名称
	private String bankAddress;		// 开户行地址
	
	public MemberBankAccount() {
		super();
	}

	public MemberBankAccount(String id){
		super(id);
	}

	@Length(min=0, max=64, message="所属用户长度必须介于 0 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=255, message="银行卡号长度必须介于 0 和 255 之间")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	@Length(min=0, max=255, message="开户人名称长度必须介于 0 和 255 之间")
	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	
	@Length(min=0, max=255, message="开户行地址长度必须介于 0 和 255 之间")
	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	
}