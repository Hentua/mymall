package com.mall.modules.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mall.common.config.Global;
import com.mall.common.persistence.DataEntity;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * 用户信息Entity
 * @author wankang
 * @version 2018-10-10
 */
public class MemberInfo extends DataEntity<MemberInfo> {

	public final static String USER_PHOTO_BASE_PATH = "/userfiles/userPhoto/";
	
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
	private String merchantRefereeId; // 推荐商户邀请人

	private String refereeAccount;
	private String merchantRefereeName;
	private String merchantRefereeAccount;
	private String shippingAddress; // 商家发货地址

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
	private String permit; // 开户许可证
	@JsonIgnore
	private String publicAccountName; // 对公账户名称
	@JsonIgnore
	private String publicAccountBank; // 开户行
	@JsonIgnore
	private String personAccount; // 个人银行账户
	@JsonIgnore
	private String personAccountName; // 个人银行账户名称
	@JsonIgnore
	private String personAccountBank; // 个人银行账户开户行
	@JsonIgnore
	private String merchantType; // 商户标识 0-推广者 1-商户
	@JsonIgnore
	private String idcardFront; // 法人身份证正面
	@JsonIgnore
	private String idcardBack; // 法人身份证反面
	@JsonIgnore
	private String specialQualification; // 特殊资质
	@JsonIgnore
	private Date startDate;
	@JsonIgnore
	private Date endDate;

	private String loginFlag;

	//店铺头图
	private String merchantHeadImg;

	//店铺联系电话
	private String merchantServicePhone;

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getRefereeAccount() {
		return refereeAccount;
	}

	public void setRefereeAccount(String refereeAccount) {
		this.refereeAccount = refereeAccount;
	}

	@ExcelField(title = "商户推荐人", sort = 6, groups = 1)
	public String getMerchantRefereeName() {
		return merchantRefereeName;
	}

	public void setMerchantRefereeName(String merchantRefereeName) {
		this.merchantRefereeName = merchantRefereeName;
	}

	@ExcelField(title = "商户推荐人账号", sort = 7, groups = 1)
	public String getMerchantRefereeAccount() {
		return merchantRefereeAccount;
	}

	public void setMerchantRefereeAccount(String merchantRefereeAccount) {
		this.merchantRefereeAccount = merchantRefereeAccount;
	}

	public String getPermit() {
		return permit;
	}

	public void setPermit(String permit) {
		this.permit = permit;
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

	public String getPersonAccount() {
		return personAccount;
	}

	public void setPersonAccount(String personAccount) {
		this.personAccount = personAccount;
	}

	public String getPersonAccountName() {
		return personAccountName;
	}

	public void setPersonAccountName(String personAccountName) {
		this.personAccountName = personAccountName;
	}

	public String getPersonAccountBank() {
		return personAccountBank;
	}

	public void setPersonAccountBank(String personAccountBank) {
		this.personAccountBank = personAccountBank;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getIdcardFront() {
		return idcardFront;
	}

	public void setIdcardFront(String idcardFront) {
		this.idcardFront = idcardFront;
	}

	public String getIdcardBack() {
		return idcardBack;
	}

	public void setIdcardBack(String idcardBack) {
		this.idcardBack = idcardBack;
	}

	public String getSpecialQualification() {
		return specialQualification;
	}

	public void setSpecialQualification(String specialQualification) {
		this.specialQualification = specialQualification;
	}

	public String getMerchantServicePhone() {
		return merchantServicePhone;
	}

	public void setMerchantServicePhone(String merchantServicePhone) {
		this.merchantServicePhone = merchantServicePhone;
	}

	public String getMerchantHeadImg() {
		return merchantHeadImg;
	}

	public void setMerchantHeadImg(String merchantHeadImg) {
		this.merchantHeadImg = merchantHeadImg;
	}

	public MemberInfo() {
		super();
	}

	public MemberInfo(String id){
		super(id);
	}

	public String getMerchantRefereeId() {
		return merchantRefereeId;
	}

	public void setMerchantRefereeId(String merchantRefereeId) {
		this.merchantRefereeId = merchantRefereeId;
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

	@ExcelField(title = "归属督导经理", sort = 8, groups = 1)
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

	@ExcelField(title = "备注", sort = 10)
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

	@ExcelField(title = "会员账号", sort = 2, groups = 0)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title = "会员名称", sort = 1, groups = 0)
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

	public String getFullAvatarUrl() {
		String fullImageUrl = "";
		if (null != avatar) {
			fullImageUrl = Global.getConfig("userfiles.baseURL") + avatar;
		}
		return fullImageUrl;
	}

	public String getFullMerchantHeadImgUrl() {
		String fullImageUrl = "";
		if (null != merchantHeadImg) {
			fullImageUrl = Global.getConfig("userfiles.baseURL") + merchantHeadImg;
		}
		return fullImageUrl;
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

	@ExcelField(title = "注册途径", sort = 3, groups = 0)
	public String getRegisterWayZh() {
		String registerWayZh = "";
		if(StringUtils.isNotBlank(this.registerWay)) {
			switch (this.registerWay) {
				case "0": registerWayZh = "自主注册";break;
				case "1": registerWayZh = "后台添加";break;
				default:;
			}
		}
		return registerWayZh;
	}

	@ExcelField(title = "注册时间", sort = 4, groups = 0)
	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@ExcelField(title = "商户类型", sort = 5, groups = 1)
	public String getMerchantTypeZh() {
		String merchantTypeZh = "";
		if(StringUtils.isNotBlank(this.merchantType)) {
			switch (this.merchantType) {
				case "0": merchantTypeZh = "推广者";break;
				case "1": merchantTypeZh = "商户";break;
				default:;
			}
		}
		return merchantTypeZh;
	}

	@ExcelField(title = "商户审核状态", sort = 9, groups = 1)
	public String getStatusZh() {
		String statusZh = "";
		if(StringUtils.isNotBlank(this.status)) {
			switch (this.status) {
				case "0": statusZh = "未提交";break;
				case "1": statusZh = "已生效";break;
				case "2": statusZh = "审核未通过";break;
				case "3": statusZh = "审核中";break;
				default:;
			}
		}
		return statusZh;
	}

}