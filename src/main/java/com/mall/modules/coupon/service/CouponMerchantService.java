package com.mall.modules.coupon.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.coupon.dao.CouponMerchantDao;
import com.mall.modules.coupon.entity.CouponCustomer;
import com.mall.modules.coupon.entity.CouponLog;
import com.mall.modules.coupon.entity.CouponMerchant;
import com.mall.modules.gift.entity.GiftConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商家优惠券Service
 *
 * @author wankang
 * @version 2018-11-20
 */
@Service
@Transactional(readOnly = true)
public class CouponMerchantService extends CrudService<CouponMerchantDao, CouponMerchant> {

    @Autowired
    private CouponLogService couponLogService;
    @Autowired
    private CouponCustomerService couponCustomerService;

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
        couponLog.setRemarks("礼包兑换");
        couponLog.setAmount(giftConfig.getHalfCoupon());
        couponLog.setProduceChannel("0");
        couponLog.setType("0");
        couponLog.setCustomerCode(merchantCode);
        couponLog.setProduceAmount(giftConfig.getHalfCoupon());
        couponLog.setGiftId(giftCustomerId);
        if (halfCoupon.getBalance() > 0) {
            couponLogService.save(couponLog);
        }
        // 保存七折券
        CouponMerchant thirtyCoupon = new CouponMerchant();
        thirtyCoupon.setCouponType("1");
        thirtyCoupon.setMerchantCode(merchantCode);
        thirtyCoupon.setBalance(giftConfig.getThirtyCoupon());
        this.saveCoupon(thirtyCoupon);
        // 保存七折券日志
        couponLog = new CouponLog();
        couponLog.setRemarks("礼包兑换");
        couponLog.setProduceChannel("0");
        couponLog.setType("0");
        couponLog.setCustomerCode(merchantCode);
        couponLog.setGiftId(giftCustomerId);
        couponLog.setCouponType("1");
        couponLog.setAmount(giftConfig.getThirtyCoupon());
        couponLog.setProduceAmount(giftConfig.getThirtyCoupon());
        if (thirtyCoupon.getBalance() > 0) {
            couponLogService.save(couponLog);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void saveCoupon(CouponMerchant couponMerchant) {
        if (couponMerchant.getBalance() == 0) {
            return;
        }
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

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void couponTransfer(CouponMerchant couponMerchant, String customerCode) {
        CouponCustomer couponCustomer = couponCustomerService.genCouponCustomerByMerchant(couponMerchant, customerCode);
        couponCustomer.setBalance(couponMerchant.getBalance());
        // 给获赠会员消费优惠券增加余额
        couponCustomerService.saveCoupon(couponCustomer);
        // 保存会员消费券日志
        CouponLog couponLog = new CouponLog();
        couponLog.setCouponType(couponMerchant.getCouponType());
        couponLog.setRemarks("商家赠送");
        couponLog.setAmount(couponMerchant.getBalance());
        couponLog.setProduceChannel("1");
        couponLog.setType("1");
        couponLog.setCustomerCode(customerCode);
        couponLog.setProduceAmount(couponMerchant.getBalance());
        couponLog.setMerchantCode(couponMerchant.getMerchantCode());
        if (couponMerchant.getBalance() > 0) {
            couponLogService.save(couponLog);
        }
        // 从商户优惠券扣减余额
        couponMerchant.setBalance(0 - couponMerchant.getBalance());
        this.saveCoupon(couponMerchant);
        // 保存商家优惠券日志
        CouponLog merchantCouponLog = new CouponLog();
        merchantCouponLog.setCouponType(couponMerchant.getCouponType());
        merchantCouponLog.setRemarks("赠送支出");
        merchantCouponLog.setAmount(couponMerchant.getBalance());
        merchantCouponLog.setProduceChannel("6");
        merchantCouponLog.setType("0");
        merchantCouponLog.setCustomerCode(customerCode);
        merchantCouponLog.setProduceAmount(couponMerchant.getBalance());
        merchantCouponLog.setMerchantCode(couponMerchant.getMerchantCode());
        couponLogService.save(merchantCouponLog);
    }
}