package com.mall.modules.coupon.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mall.modules.coupon.entity.CouponConfig;
import com.mall.modules.gift.entity.GiftConfigCoupon;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

	@Autowired
	private CouponConfigService couponConfigService;

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

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void exchangeGiftGenCoupon(GiftConfigCoupon giftConfigCoupon, String giftCustomerCode) throws Exception {
		User currUser = UserUtils.getUser();
		String customerCode = currUser.getId();
		String couponConfigId = giftConfigCoupon.getCouponId();
		CouponConfig couponConfig = couponConfigService.get(couponConfigId);
		CouponCustomer couponCustomer = genCouponCustomerByGift(couponConfig, customerCode);
		couponCustomer.setGiftCode(giftCustomerCode);
		this.save(couponCustomer);
	}

	public CouponCustomer genCouponCustomerByGift(CouponConfig couponConfig, String customerCode) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CouponCustomer couponCustomer = new CouponCustomer();
		couponCustomer.setConfigId(couponConfig.getId());
		couponCustomer.setCouponType(couponConfig.getCouponType());
		couponCustomer.setCouponName(couponConfig.getCouponName());
		Integer expiryTime = couponConfig.getExpiryTime();
		couponCustomer.setStartDate(new Date());
		if(expiryTime == 0) {
			couponCustomer.setEndDate(simpleDateFormat.parse("9999-12-31 00:00:00"));
		}else {
			couponCustomer.setEndDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * expiryTime));
		}
		couponCustomer.setLimitAmount(couponConfig.getLimitAmount());
		couponCustomer.setCustomerCode(customerCode);
		couponCustomer.setCouponStatus("0");
		couponCustomer.setAccessChannel("0");
		return couponCustomer;
	}
	
}