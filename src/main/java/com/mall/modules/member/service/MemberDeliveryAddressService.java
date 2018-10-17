package com.mall.modules.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.member.entity.MemberDeliveryAddress;
import com.mall.modules.member.dao.MemberDeliveryAddressDao;

/**
 * 收货地址Service
 * @author wankang
 * @version 2018-10-17
 */
@Service
@Transactional(readOnly = true)
public class MemberDeliveryAddressService extends CrudService<MemberDeliveryAddressDao, MemberDeliveryAddress> {

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
	
}