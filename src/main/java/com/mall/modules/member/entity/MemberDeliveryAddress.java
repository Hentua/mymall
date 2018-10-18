package com.mall.modules.member.entity;

import org.hibernate.validator.constraints.Length;
import com.mall.modules.sys.entity.Area;
import javax.validation.constraints.NotNull;

import com.mall.common.persistence.DataEntity;

/**
 * 收货地址Entity
 * @author wankang
 * @version 2018-10-17
 */
public class MemberDeliveryAddress extends DataEntity<MemberDeliveryAddress> {
	
	private static final long serialVersionUID = 1L;
	private String customerCode;		// 用户编号
	private String realname;		// 收货人姓名
	private String telphone;		// 收货人电话
	private String telphoneBak;		// 备用电话
	private Area country;		// 国家 对应area表ID
	private Area province;		// 省份 对应area表ID
	private Area city;		// 城市 对应area表ID
	private Area area;		// 地区 对应area表ID
	private String detailAddress;		// 详细收获地址
	private String zip;		// 邮政编码
	private String isDefaultAddress;		// 是否默认收费地址（0-否，1-是）
	
	public MemberDeliveryAddress() {
		super();
	}

	public MemberDeliveryAddress(String id){
		super(id);
	}

	@Length(min=1, max=64, message="用户编号长度必须介于 1 和 64 之间")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	@Length(min=1, max=100, message="收货人姓名长度必须介于 1 和 100 之间")
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	@Length(min=1, max=20, message="收货人电话长度必须介于 1 和 20 之间")
	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	
	@Length(min=0, max=20, message="备用电话长度必须介于 0 和 20 之间")
	public String getTelphoneBak() {
		return telphoneBak;
	}

	public void setTelphoneBak(String telphoneBak) {
		this.telphoneBak = telphoneBak;
	}
	
	@NotNull(message="国家 对应area表ID不能为空")
	public Area getCountry() {
		return country;
	}

	public void setCountry(Area country) {
		this.country = country;
	}
	
	@NotNull(message="省份 对应area表ID不能为空")
	public Area getProvince() {
		return province;
	}

	public void setProvince(Area province) {
		this.province = province;
	}
	
	@NotNull(message="城市 对应area表ID不能为空")
	public Area getCity() {
		return city;
	}

	public void setCity(Area city) {
		this.city = city;
	}
	
	@NotNull(message="地区 对应area表ID不能为空")
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@Length(min=1, max=255, message="详细收获地址长度必须介于 1 和 255 之间")
	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	
	@Length(min=0, max=10, message="邮政编码长度必须介于 0 和 10 之间")
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Length(min=0, max=1, message="是否默认收费地址（0-否，1-是）长度必须介于 0 和 1 之间")
	public String getIsDefaultAddress() {
		return isDefaultAddress;
	}

	public void setIsDefaultAddress(String isDefaultAddress) {
		this.isDefaultAddress = isDefaultAddress;
	}
	
}