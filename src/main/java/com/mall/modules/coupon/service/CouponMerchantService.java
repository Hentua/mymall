package com.mall.modules.coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.coupon.entity.CouponMerchant;
import com.mall.modules.coupon.dao.CouponMerchantDao;

/**
 * 商家优惠券Service
 * @author wankang
 * @version 2018-11-07
 */
@Service
@Transactional(readOnly = true)
public class CouponMerchantService extends CrudService<CouponMerchantDao, CouponMerchant> {

	public CouponMerchant get(String id) {
		return super.get(id);
	}
	
	public List<CouponMerchant> findList(CouponMerchant couponMerchant) {
		return super.findList(couponMerchant);
	}
	
	public Page<CouponMerchant> findPage(Page<CouponMerchant> page, CouponMerchant couponMerchant) {
		return super.findPage(page, couponMerchant);
	}
	
	@Transactional(readOnly = false)
	public void save(CouponMerchant couponMerchant) {
		super.save(couponMerchant);
	}
	
	@Transactional(readOnly = false)
	public void delete(CouponMerchant couponMerchant) {
		super.delete(couponMerchant);
	}
	
}