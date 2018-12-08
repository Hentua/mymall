package com.mall.modules.coupon.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.service.ServiceException;
import com.mall.modules.coupon.dao.CouponCustomerDao;
import com.mall.modules.coupon.entity.CouponCustomer;
import com.mall.modules.coupon.entity.CouponLog;
import com.mall.modules.coupon.entity.CouponMerchant;
import com.mall.modules.gift.entity.GiftConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    public Map<String, String> enabledCouponsCountByIndex(String customerCode) {
        return dao.enabledCouponsCountByIndex(customerCode);
    }



    @Transactional(readOnly = false, rollbackFor = {Exception.class, ServiceException.class}, propagation = Propagation.REQUIRED)
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
        couponLog.setRemarks("礼包兑换");
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
        couponLog.setRemarks("礼包兑换");
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
        if (couponCustomer.getBalance() == 0) {
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

    public CouponCustomer genCouponCustomerByMerchant(CouponMerchant couponMerchant, String customerCode) {
        CouponCustomer couponCustomer = new CouponCustomer();
        couponCustomer.setCouponType(couponMerchant.getCouponType());
        couponCustomer.setCustomerCode(customerCode);
        couponCustomer.setBalance(0.00);
        return couponCustomer;
    }

    /**
     * 平台赠送优惠券
     *
     * @param amount         赠送金额
     * @param couponType     优惠券类型 0-五折券 1-七折券
     * @param customerCode   会员ID
     * @param remarks        赠送说明
     * @param produceChannel 产生渠道 0-礼包 1-商家赠送 2-平台赠送 3-佣金转余额 4-充值 5-订单支出 6-赠送支出 7-退单返回
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void saveCouponCustomerByPlatform(Double amount, String couponType, String customerCode, String remarks, String produceChannel) {
        // 优惠券实体
        CouponCustomer couponCustomer = new CouponCustomer();
        couponCustomer.setBalance(amount);
        couponCustomer.setCustomerCode(customerCode);
        couponCustomer.setCouponType(couponType);
        // 保存优惠券日志
        CouponLog couponLog = new CouponLog();
        couponLog.setCouponType(couponType);
        couponLog.setRemarks(remarks);
        couponLog.setAmount(amount);
        couponLog.setProduceChannel(produceChannel);
        couponLog.setType("1");
        couponLog.setCustomerCode(customerCode);
        couponLog.setProduceAmount(amount);
        couponLog.setMerchantCode("0");
        if (amount > 0) {
            couponLogService.save(couponLog);
            this.saveCoupon(couponCustomer);
        }
    }

}