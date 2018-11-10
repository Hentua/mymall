package com.mall.modules.coupon.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mall.common.utils.DateUtils;
import com.mall.modules.coupon.entity.CouponConfig;
import com.mall.modules.gift.entity.GiftConfigCoupon;
import com.mall.modules.sys.entity.Role;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.service.SystemService;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

	@Autowired
	private CouponConfigService couponConfigService;

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

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void exchangeGiftGenCoupon(GiftConfigCoupon giftConfigCoupon, String giftCustomerCode) throws Exception {
		User currUser = UserUtils.getUser();
		String merchantCode = currUser.getId();
		String couponConfigId = giftConfigCoupon.getCouponId();
		CouponConfig couponConfig = couponConfigService.get(couponConfigId);
		CouponMerchant couponMerchant = genCouponMerchant(couponConfig, merchantCode);
		couponMerchant.setGiftCode(giftCustomerCode);
		this.save(couponMerchant);
	}

	public CouponMerchant genCouponMerchant(CouponConfig couponConfig, String merchantCode) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CouponMerchant couponMerchant = new CouponMerchant();
		couponMerchant.setConfigId(couponConfig.getId());
		couponMerchant.setCouponType(couponConfig.getCouponType());
		couponMerchant.setCouponName(couponConfig.getCouponName());
		Integer transferExpiryTime = couponConfig.getTransferExpiryTime();
		couponMerchant.setStartDate(DateUtils.getStartOfDay(new Date()));
		if(transferExpiryTime == 0) {
			couponMerchant.setEndDate(DateUtils.getEndOfDay(simpleDateFormat.parse("9999-12-31 00:00:00")));
		}else {
			couponMerchant.setEndDate(DateUtils.getEndOfDay(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * transferExpiryTime)));
		}
		couponMerchant.setLimitAmount(couponConfig.getLimitAmount());
		couponMerchant.setMerchantCode(merchantCode);
		couponMerchant.setCouponStatus("0");
		couponMerchant.setAccessChannel("0");
		return couponMerchant;
	}
	
}