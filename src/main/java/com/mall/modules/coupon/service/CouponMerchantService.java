package com.mall.modules.coupon.service;

import java.util.List;

import com.mall.modules.coupon.entity.CouponCustomer;
import com.mall.modules.coupon.entity.CouponLog;
import com.mall.modules.gift.entity.GiftConfig;
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
 * @version 2018-11-20
 */
@Service
@Transactional(readOnly = true)
public class CouponMerchantService extends CrudService<CouponMerchantDao, CouponMerchant> {

	@Autowired
	private CouponLogService couponLogService;

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
	public void exchangeGiftGenCoupon(GiftConfig giftConfig, String giftCustomerId, String merchantCode) {
		CouponMerchant halfCoupon = new CouponMerchant();
		// 保存礼包五折券
		halfCoupon.setCouponType("0");
		halfCoupon.setMerchantCode(merchantCode);
		halfCoupon.setBalance(giftConfig.getHalfCoupon());
		this.saveCoupon(halfCoupon);
		// 保存五折优惠券日志
		CouponLog couponLog = new CouponLog();
		couponLog.setCouponType("0");
		couponLog.setRemarks("礼包赠送");
		couponLog.setAmount(giftConfig.getHalfCoupon());
		couponLog.setProduceChannel("0");
		couponLog.setType("0");
		couponLog.setCustomerCode(merchantCode);
		couponLog.setProductAmount(giftConfig.getHalfCoupon());
		couponLog.setGiftId(giftCustomerId);
		couponLogService.save(couponLog);
		// 保存七折券
		CouponMerchant thirtyCoupon = new CouponMerchant();
		thirtyCoupon.setCouponType("1");
		thirtyCoupon.setMerchantCode(merchantCode);
		thirtyCoupon.setBalance(giftConfig.getThirtyCoupon());
		this.saveCoupon(thirtyCoupon);
		// 保存七折券日志
		couponLog.setCouponType("1");
		couponLog.setAmount(giftConfig.getThirtyCoupon());
		couponLog.setProductAmount(giftConfig.getThirtyCoupon());
		couponLogService.save(couponLog);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void saveCoupon(CouponMerchant couponMerchant) {
		CouponMerchant queryCondition = new CouponMerchant();
		queryCondition.setMerchantCode(couponMerchant.getMerchantCode());
		queryCondition.setCouponType(couponMerchant.getCouponType());
		List<CouponMerchant> couponMerchants = this.findList(queryCondition);
		CouponMerchant currCoupon = null;
		if (null != couponMerchants && couponMerchants.size() > 0) {
			currCoupon = couponMerchants.get(0);
		}
		if (null == currCoupon) {
			this.save(couponMerchant);
		} else {
			couponMerchant.setId(currCoupon.getId());
			couponMerchant.setBalance(currCoupon.getBalance() + couponMerchant.getBalance());
			this.save(couponMerchant);
		}
	}
}