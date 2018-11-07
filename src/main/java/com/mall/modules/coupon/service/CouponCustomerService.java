package com.mall.modules.coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.coupon.entity.CouponCustomer;
import com.mall.modules.coupon.dao.CouponCustomerDao;

/**
 * 用户优惠券Service
 * @author wankang
 * @version 2018-11-07
 */
@Service
@Transactional(readOnly = true)
public class CouponCustomerService extends CrudService<CouponCustomerDao, CouponCustomer> {

	public CouponCustomer get(String id) {
		return super.get(id);
	}
	
	public List<CouponCustomer> findList(CouponCustomer couponCustomer) {
		return super.findList(couponCustomer);
	}
	
	public Page<CouponCustomer> findPage(Page<CouponCustomer> page, CouponCustomer couponCustomer) {
		return super.findPage(page, couponCustomer);
	}
	
	@Transactional(readOnly = false)
	public void save(CouponCustomer couponCustomer) {
		super.save(couponCustomer);
	}
	
	@Transactional(readOnly = false)
	public void delete(CouponCustomer couponCustomer) {
		super.delete(couponCustomer);
	}
	
}