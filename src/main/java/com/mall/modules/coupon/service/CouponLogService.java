package com.mall.modules.coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.coupon.entity.CouponLog;
import com.mall.modules.coupon.dao.CouponLogDao;

/**
 * 优惠券记录Service
 * @author wankang
 * @version 2018-11-20
 */
@Service
@Transactional(readOnly = true)
public class CouponLogService extends CrudService<CouponLogDao, CouponLog> {

	public CouponLog get(String id) {
		return super.get(id);
	}
	
	public List<CouponLog> findList(CouponLog couponLog) {
		return super.findList(couponLog);
	}
	
	public Page<CouponLog> findPage(Page<CouponLog> page, CouponLog couponLog) {
		return super.findPage(page, couponLog);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void save(CouponLog couponLog) {
		super.save(couponLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(CouponLog couponLog) {
		super.delete(couponLog);
	}
	
}