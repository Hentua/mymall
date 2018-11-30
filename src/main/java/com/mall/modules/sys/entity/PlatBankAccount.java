package com.mall.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 平台银行账户管理Entity
 * @author hub
 * @version 2018-11-30
 */
public class PlatBankAccount extends DataEntity<PlatBankAccount> {
	
	private static final long serialVersionUID = 1L;
	private String bankAccount;		// 平台银行账户
	private String bankAccountName;		// 平台银行账户开户人
	private String bankName;		// 平台银行账户开户行
	private String title;		// title
	
	public PlatBankAccount() {
		super();
	}

	public PlatBankAccount(String id){
		super(id);
	}

	@Length(min=0, max=255, message="平台银行账户长度必须介于 0 和 255 之间")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	@Length(min=0, max=255, message="平台银行账户开户人长度必须介于 0 和 255 之间")
	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	
	@Length(min=0, max=255, message="平台银行账户开户行长度必须介于 0 和 255 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=255, message="title长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}