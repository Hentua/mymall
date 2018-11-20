package com.mall.modules.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用户信息Entity
 * @author wankang
 * @version 2018-10-10
 */
public class MemberInfo extends DataEntity<MemberInfo> {

	public final static String USER_PHOTO_BASE_PATH = "userPhoto/";
	
	private static final long serialVersionUID = 1L;
	private String referee;		// 自己的推荐码
	private String refereeId;		// 推荐人ID
	private Double balance;		// 账户余额
	private Double commission;  //佣金
	private String avatar;		// 头像地址
	private String registerWay;		// 注册途径（0-注册页面自主注册，1-商户管理后台直接添加）
	private String nickname; // 昵称
	private String sex; // 性别0-男， 1-女

	private String mobile; // 手机号码

	private String status; // 会员当前状态 0-审核中， 1-已生效， 2-审核未通过， 3-审核中
	private String remarks; // 备注
	private String refereeName; // 推荐人名称

	private String userType; // 用户类型
	private String operatorCode; // 归属运营ID
	private String operatorName; // 归属运营名称
	private String openid; // 普通用户的标识，对当前开发者帐号唯一
	private String unionid; // 用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
	private String wechatNickname; // 微信普通用户昵称
	private String headimgurl; // 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空

	@JsonIgnore
	private String password;
	@JsonIgnore
	private String repeatPassword;
	@JsonIgnore
	private String payPassword; // 支付密码
	@JsonIgnore
	private String verifyCode; // 短信验证码

	@JsonIgnore
	private String companyName; // 公司名称
	@JsonIgnore
	private String publicAccount; // 对公账户
	@JsonIgnore
	private String businessLicenseImage; // 营业执照图片
	@JsonIgnore
	private String productLicense; // 产品许可证
	@JsonIgnore
	private String publicAccountName; // 对公账户名称
	@JsonIgnore
	private String publicAccountBank; // 开户行

	private String loginFlag;

	public MemberInfo() {
		super();
	}

	public MemberInfo(String id){
		super(id);
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getWechatNickname() {
		return wechatNickname;
	}

	public void setWechatNickname(String wechatNickname) {
		this.wechatNickname = wechatNickname;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserType() {
		return userType;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPublicAccount() {
		return publicAccount;
	}

	public void setPublicAccount(String publicAccount) {
		this.publicAccount = publicAccount;
	}

	public String getBusinessLicenseImage() {
		return businessLicenseImage;
	}

	public void setBusinessLicenseImage(String businessLicenseImage) {
		this.businessLicenseImage = businessLicenseImage;
	}

	public String getProductLicense() {
		return productLicense;
	}

	public void setProductLicense(String productLicense) {
		this.productLicense = productLicense;
	}

	public String getPublicAccountName() {
		return publicAccountName;
	}

	public void setPublicAccountName(String publicAccountName) {
		this.publicAccountName = publicAccountName;
	}

	public String getPublicAccountBank() {
		return publicAccountBank;
	}

	public void setPublicAccountBank(String publicAccountBank) {
		this.publicAccountBank = publicAccountBank;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getRefereeName() {
		return refereeName;
	}

	public void setRefereeName(String refereeName) {
		this.refereeName = refereeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@NotEmpty(message = "密码不能为空")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotEmpty(message = "密码不能为空")
	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Length(min=1, max=20, message="自己的推荐码长度必须介于 1 和 20 之间")
	public String getReferee() {
		return referee;
	}

	public void setReferee(String referee) {
		this.referee = referee;
	}
	
	@Length(min=0, max=64, message="推荐人ID长度必须介于 0 和 64 之间")
	public String getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(String refereeId) {
		this.refereeId = refereeId;
	}
	
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	@Length(min=0, max=200, message="头像地址长度必须介于 0 和 200 之间")
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Length(min=1, max=2, message="注册途径（0-注册页面自主注册，1-商户管理后台直接添加）长度必须介于 1 和 2 之间")
	public String getRegisterWay() {
		return registerWay;
	}

	public void setRegisterWay(String registerWay) {
		this.registerWay = registerWay;
	}
	
}