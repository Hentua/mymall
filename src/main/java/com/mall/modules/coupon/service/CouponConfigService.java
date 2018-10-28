package com.mall.modules.coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.coupon.entity.CouponConfig;
import com.mall.modules.coupon.dao.CouponConfigDao;

/**
 * 优惠券规则配置Service
 * @author wankang
 * @version 2018-10-28
 */
@Service
@Transactional(readOnly = true)
public class CouponConfigService extends CrudService<CouponConfigDao, CouponConfig> {

	public CouponConfig get(String id) {
		return super.get(id);
	}
	
	public List<CouponConfig> findList(CouponConfig couponConfig) {
		return super.findList(couponConfig);
	}
	
	public Page<CouponConfig> findPage(Page<CouponConfig> page, CouponConfig couponConfig) {
		return super.findPage(page, couponConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(CouponConfig couponConfig) {
		super.save(couponConfig);
	}
	
	@Transactional(readOnly = false)
	public void delete(CouponConfig couponConfig) {
		super.delete(couponConfig);
	}
	
}