package com.mall.modules.member.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.utils.StringUtils;
import com.mall.modules.member.dao.MemberDeliveryAddressDao;
import com.mall.modules.member.entity.MemberDeliveryAddress;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 收货地址Service
 * @author wankang
 * @version 2018-10-17
 */
@Service
@Transactional(readOnly = true)
public class MemberDeliveryAddressService extends CrudService<MemberDeliveryAddressDao, MemberDeliveryAddress> {

	@Autowired
	private MemberDeliveryAddressDao memberDeliveryAddressDao;

	public MemberDeliveryAddress get(String id) {
		return super.get(id);
	}
	
	public List<MemberDeliveryAddress> findList(MemberDeliveryAddress memberDeliveryAddress) {
		return super.findList(memberDeliveryAddress);
	}
	
	public Page<MemberDeliveryAddress> findPage(Page<MemberDeliveryAddress> page, MemberDeliveryAddress memberDeliveryAddress) {
		return super.findPage(page, memberDeliveryAddress);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberDeliveryAddress memberDeliveryAddress) {
		super.save(memberDeliveryAddress);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberDeliveryAddress memberDeliveryAddress) {
		super.delete(memberDeliveryAddress);
	}

	/**
	 * 根据请求体信息生成MemberDeliveryAddress实体信息
	 * @param request 请求体
	 * @return MemberDeliveryAddress实体
	 */
	public MemberDeliveryAddress genMemberDeliveryAddress(HttpServletRequest request) {
		User currUser = UserUtils.getUser();
		String id = request.getParameter("id");
		String customerCode = currUser.getId();
		String realname = request.getParameter("realname");
		String telphone = request.getParameter("telphone");
		if(StringUtils.isNotBlank(telphone) && !MemberInfoService.isPhone(telphone)) {
			return null;
		}
		String telphoneBak = request.getParameter("telphoneBak");
		if(StringUtils.isNotBlank(telphoneBak) && !MemberInfoService.isPhone(telphoneBak)) {
			return null;
		}
		String country = "0";
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String area = request.getParameter("area");
		String detailAddress = request.getParameter("detailAddress");
		String zip = request.getParameter("zip");
		String isDefaultAddress = request.getParameter("isDefaultAddress");
		MemberDeliveryAddress memberDeliveryAddress = new MemberDeliveryAddress();
		if(StringUtils.isNotBlank(id)) {
			memberDeliveryAddress.setId(id);
		}
		memberDeliveryAddress.setArea(area);
		memberDeliveryAddress.setCity(city);
		memberDeliveryAddress.setCountry(country);
		memberDeliveryAddress.setProvince(province);
		memberDeliveryAddress.setRealname(realname);
		memberDeliveryAddress.setTelphone(telphone);
		memberDeliveryAddress.setTelphoneBak(telphoneBak);
		memberDeliveryAddress.setZip(zip);
		memberDeliveryAddress.setCustomerCode(customerCode);
		memberDeliveryAddress.setDetailAddress(detailAddress);
		memberDeliveryAddress.setIsDefaultAddress(isDefaultAddress);
		return memberDeliveryAddress;
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void modifyDefaultDeliveryAddress(String customerCode, String id) {
		memberDeliveryAddressDao.restoreDefaultDeliveryAddress(customerCode);
		memberDeliveryAddressDao.setDefaultDeliveryAddress(id);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteAddress(String id) {
		return memberDeliveryAddressDao.deleteAddress(id);
	}
	
}