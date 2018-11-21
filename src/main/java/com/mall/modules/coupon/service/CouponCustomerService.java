package com.mall.modules.coupon.service;

import java.util.List;
import java.util.Map;

import com.mall.modules.coupon.entity.CouponLog;
import com.mall.modules.gift.entity.GiftConfig;
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
 *
 * @author wankang
 * @version 2018-11-20
 */
@Service
@Transactional(readOnly = true)
public class CouponCustomerService extends CrudService<CouponCustomerDao, CouponCustomer> {

    @Autowired
    private CouponLogService couponLogService;

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

    public Map<String, String> enabledCouponsCount(String customerCode) {
        return dao.enabledCouponsCount(customerCode);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void exchangeGiftGenCoupon(GiftConfig giftConfig, String giftCustomerId, String customerCode) {
        CouponCustomer halfCoupon = new CouponCustomer();
        // 保存礼包五折券
        halfCoupon.setCouponType("0");
        halfCoupon.setCustomerCode(customerCode);
        halfCoupon.setBalance(giftConfig.getHalfCoupon());
        this.saveCoupon(halfCoupon);
        // 保存五折优惠券日志
        CouponLog couponLog = new CouponLog();
        couponLog.setCouponType("0");
        couponLog.setRemarks("礼包赠送");
        couponLog.setAmount(giftConfig.getHalfCoupon());
        couponLog.setProduceChannel("0");
        couponLog.setType("1");
        couponLog.setCustomerCode(customerCode);
        couponLog.setProduceAmount(giftConfig.getHalfCoupon());
        couponLog.setGiftId(giftCustomerId);
        if (halfCoupon.getBalance() > 0) {
            couponLogService.save(couponLog);
        }
        // 保存七折券
        CouponCustomer thirtyCoupon = new CouponCustomer();
        thirtyCoupon.setCouponType("1");
        thirtyCoupon.setCustomerCode(customerCode);
        thirtyCoupon.setBalance(giftConfig.getThirtyCoupon());
        this.saveCoupon(thirtyCoupon);
        // 保存七折券日志
        couponLog = new CouponLog();
        couponLog.setProduceChannel("0");
        couponLog.setType("1");
        couponLog.setCustomerCode(customerCode);
        couponLog.setGiftId(giftCustomerId);
        couponLog.setCouponType("1");
        couponLog.setAmount(giftConfig.getThirtyCoupon());
        couponLog.setProduceAmount(giftConfig.getThirtyCoupon());
        if (thirtyCoupon.getBalance() > 0) {
            couponLogService.save(couponLog);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void saveCoupon(CouponCustomer couponCustomer) {
        if(couponCustomer.getBalance() <= 0) {
            return;
        }
        CouponCustomer queryCondition = new CouponCustomer();
        queryCondition.setCustomerCode(couponCustomer.getCustomerCode());
        queryCondition.setCouponType(couponCustomer.getCouponType());
        List<CouponCustomer> couponCustomers = this.findList(queryCondition);
        CouponCustomer currCoupon = null;
        if (null != couponCustomers && couponCustomers.size() > 0) {
            currCoupon = couponCustomers.get(0);
        }
        if (null == currCoupon) {
            this.save(couponCustomer);
        } else {
            couponCustomer.setId(currCoupon.getId());
            couponCustomer.setBalance(currCoupon.getBalance() + couponCustomer.getBalance());
            this.save(couponCustomer);
        }
    }

}