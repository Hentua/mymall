package com.mall.modules.account.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mall.common.persistence.DataEntity;
import com.mall.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 账户流水Entity
 * @author hub
 * @version 2018-11-10
 */
public class AccountFlow extends DataEntity<AccountFlow> {
	
	private static final long serialVersionUID = 1L;
	private String flowNo;		// 流水单号
	private String userId;		// 用户id
	private Double amount;		// 金额
	private String type;		// 类型：1收入 2支出
	private String mode;		// 收入（1：充值，2：佣金转余额,5退款）支出（3：提现，4：消费）
	private String orderId;		// 订单id 充值提现记录为空
	private String incomeExpenditureMode;		// 收支方式 1：微信 2：用户转账
	private String bankAccount;		// 银行账户
	private String bankAccountName;		// 开户人名称
	private String bankName;		// 开户行
	private String platBankAccount; //平台收款银行账户
	private Date transferDate;      //转账时间
	private String transferImage;   //转账截图


	private String checkStatus;		// 充值提现审核状态：1未审核 2已审核
	private String paymentType;     //消费类型 0普通订单 1礼包购买

	private String nickname; //用户名称
	private String userMobile; //用户账号
	private String org;


	private Date startDate; //筛选开始时间
	private Date endDate;  //筛选结束时间



	private Date transferStartDate; //筛选开始时间
	private Date transferEndDate;  //筛选结束时间

	public Date getTransferStartDate() {
		return transferStartDate;
	}

	public void setTransferStartDate(Date transferStartDate) {
		this.transferStartDate = transferStartDate;
	}

	public Date getTransferEndDate() {
		return transferEndDate;
	}

	public void setTransferEndDate(Date transferEndDate) {
		this.transferEndDate = transferEndDate;
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

	public AccountFlow() {
		super();
	}

	public AccountFlow(String id){
		super(id);
	}


	@ExcelField(title = "用户账号", sort = 1)
	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	@ExcelField(title = "平台收款账户", sort = 8)
	public String getPlatBankAccount() {
		return platBankAccount;
	}

	public void setPlatBankAccount(String platBankAccount) {
		this.platBankAccount = platBankAccount;
	}
	@ExcelField(title = "转账时间", sort = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public String getTransferImage() {
		return transferImage;
	}

	public void setTransferImage(String transferImage) {
		this.transferImage = transferImage;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	@ExcelField(title = "用户名称", sort = 2)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	@ExcelField(title = "流水单号", sort = 3)
	@Length(min=0, max=100, message="流水单号长度必须介于 0 和 100 之间")
	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	
	@Length(min=0, max=64, message="用户id长度必须介于 0 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@ExcelField(title = "金额", sort = 4)
	@NotNull(message="金额不能为空")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Length(min=1, max=3, message="类型：1收入 2支出长度必须介于 1 和 3 之间")
	public String getType() {
		return type;
	}

	public String getTypeText(){
		if("1".equals(this.getType())){
			return "收入";
		}
		if("2".equals(this.getType())){
			return "支出";
		}
		return "";
	}

	@ExcelField(title = "类型", sort = 5)
	public String getTypeStr(){
		String str = "";
		if("1".equals(this.getType())){
			if("1".equals(this.getMode())){
				return "收入-银行转账充值";
			}
			if("2".equals(this.getMode())){
				return "收入-佣金转余额";
			}
			if("5".equals(this.getMode())){
				return "收入-消费退款";
			}
		}
		if("2".equals(this.getType())){
			if("3".equals(this.getMode())){
				return "支出-提现";
			}
			if("4".equals(this.getMode())){
				return "支出-消费";
			}
		}

		return str;
	}
	@ExcelField(title = "操作时间", sort = 6)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date createDateStr(){
		return this.createDate;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=3, message="收入（1：充值，2：佣金转余额）支出（3：提现，4：消费）长度必须介于 0 和 3 之间")
	public String getMode() {
		return mode;
	}

	public String getModeText(){
		if("1".equals(this.getMode())){
			return "充值";
		}
		if("2".equals(this.getMode())){
			return "佣金转余额";
		}
		if("3".equals(this.getMode())){
			return "提现";
		}
		if("4".equals(this.getMode())){
			return "消费";
		}
		if("5".equals(this.getMode())){
			return "消费退款";
		}
		return "";
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	@Length(min=0, max=64, message="订单id 充值提现记录为空长度必须介于 0 和 64 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=3, message="收支方式 1：微信 2：用户转账长度必须介于 0 和 3 之间")
	public String getIncomeExpenditureMode() {
		return incomeExpenditureMode;
	}


	public String getIncomeExpenditureModeText(){
		if("1".equals(this.getIncomeExpenditureMode())){
			return "微信";
		}
		if("2".equals(this.getIncomeExpenditureMode())){
			return "银行转账";
		}
		return "";
	}


	public void setIncomeExpenditureMode(String incomeExpenditureMode) {
		this.incomeExpenditureMode = incomeExpenditureMode;
	}

	@ExcelField(title = "付款账号", sort = 9)
	@Length(min=0, max=100, message="银行账户长度必须介于 0 和 100 之间")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@ExcelField(title = "付款人名称", sort = 10)
	@Length(min=0, max=50, message="开户人名称长度必须介于 0 和 50 之间")
	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	@ExcelField(title = "付款银行", sort = 11)
	@Length(min=0, max=200, message="开户行长度必须介于 0 和 200 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=3, message="充值提现审核状态：1未审核 2已审核长度必须介于 0 和 3 之间")
	public String getCheckStatus() {
		return checkStatus;
	}


	@ExcelField(title = "审核状态", sort = 11)
	public String getCheckStatusText() {
		if("1".equals(this.getCheckStatus())){
			return "待审核";
		}
		if("2".equals(this.getCheckStatus())){
			return "已审核";
		}
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
}