package com.mall.modules.commission.entity;

import com.mall.common.persistence.DataEntity;
import com.mall.modules.sys.utils.DictUtils;
import org.hibernate.validator.constraints.Length;

/**
 * 佣金明细Entity
 * @author hub
 * @version 2018-10-21
 */
public class CommissionInfo extends DataEntity<CommissionInfo> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣 6佣金提现 7佣金转余额
	private String userId;		// 佣金收入人ID
	private String produceUserId;		// 被推荐人ID
	private Double originAmount = 0.0;		// 产生佣金金额
	private Double amount;		// 佣金金额
	private String unionId;		// 关联订单号 【推荐商家入驻无订单号】
	private String orderBathNo;   //
	private String settlementId;		// 提现结算单ID
	private String produceUserName; //被推荐人名称
	private String status;  //清算状态 0未清算 1已清算
	private String userMobile;
	private String userName;
	private String produceUserMobile;
	private String mode;		// 佣金计算方式（1固定金额 2百分比金额）
	private Double number= 0.0;		// 数值


	//佣金提现
	private String bankAccount;		// 银行账户
	private String bankAccountName;		// 开户人名称
	private String bankName;		// 开户行
	private String checkStatus;		// 提现审核状态：1未审核 2已审核 3驳回
	private String checkRemark;     //审核备注


	public Double getPayAmount(){
		Double serviceCharge = Double.parseDouble(DictUtils.getDictValue("service_charge","service_charge","0"));
		if(serviceCharge<=0){
			return amount;
		}
		return (double) Math.round(amount*serviceCharge * 100) / 100;
	}



	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public CommissionInfo() {
		super();
	}

	public CommissionInfo(String id){
		super(id);
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getProduceUserMobile() {
		return produceUserMobile;
	}

	public void setProduceUserMobile(String produceUserMobile) {
		this.produceUserMobile = produceUserMobile;
	}

	public String getStatus() {
		return status;
	}

	public String getStatusText(){
		if("6".equals(this.getType())){
			if("1".equals(this.getCheckStatus())){
				return "待审核";
			}
			if("2".equals(this.getCheckStatus())){
				return "已审核";
			}
			if("3".equals(this.getCheckStatus())){
				return "已驳回";
			}
		}else if("7".equals(this.getType())){
			return "已完成";
		}else{
			if("1".equals(this.getStatus())) {
				return  "已清算";
			}else{
				return "未清算";
			}
		}
		return this.getType()+"-"+this.getStatus();
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProduceUserName() {
		return produceUserName;
	}

	public void setProduceUserName(String produceUserName) {
		this.produceUserName = produceUserName;
	}

	@Length(min=0, max=10, message="1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣长度必须介于 0 和 10 之间")
	public String getType() {
		return type;
	}

	public String getText(){
		if("1".equals(this.getType())){
			return "["+this.getProduceUserName()+"]消费返佣";
		}
		if("2".equals(this.getType())){
			return"["+this.getProduceUserName()+"]销售返佣";
		}
		if("3".equals(this.getType())){
			return "["+this.getProduceUserName()+"]入驻返佣";
		}
		if("4".equals(this.getType())){
			return "["+this.getProduceUserName()+"]送出礼包返佣";
		}
		if("5".equals(this.getType())){
			return "送出礼包返佣";
		}
		if("6".equals(this.getType())){
			if("1".equals(this.checkStatus)){
				return "提现-待审核";
			}
			if("2".equals(this.checkStatus)){
				return "提现-已完成";
			}
			if("3".equals(this.checkStatus)){
				return "提现-已驳回";
			}
			return "提现";
		}
		if("7".equals(this.getType())){
			return "佣金转余额";
		}
		return this.getType();
	}



	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="佣金收入人ID长度必须介于 0 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="被推荐人ID长度必须介于 0 和 64 之间")
	public String getProduceUserId() {
		return produceUserId;
	}

	public void setProduceUserId(String produceUserId) {
		this.produceUserId = produceUserId;
	}
	
	public Double getOriginAmount() {
		return originAmount;
	}

	public void setOriginAmount(Double originAmount) {
		this.originAmount = originAmount;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=64, message="关联订单号 【推荐商家入驻无订单号】长度必须介于 0 和 64 之间")
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
	@Length(min=0, max=64, message="提现结算单ID长度必须介于 0 和 64 之间")
	public String getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}
	
}