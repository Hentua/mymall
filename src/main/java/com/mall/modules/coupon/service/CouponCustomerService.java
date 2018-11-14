package com.mall.modules.coupon.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.utils.DateUtils;
import com.mall.modules.coupon.dao.CouponCustomerDao;
import com.mall.modules.coupon.entity.CouponConfig;
import com.mall.modules.coupon.entity.CouponCustomer;
import com.mall.modules.coupon.entity.CouponMerchant;
import com.mall.modules.gift.entity.GiftConfigCoupon;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户优惠券Service
 *
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

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
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
        for (int i = 0; i < giftConfigCoupon.getCouponCount(); i++) {
            this.save(couponCustomer);
        }
    }

    public CouponCustomer genCouponCustomerByGift(CouponConfig couponConfig, String customerCode) throws Exception {
        Date now = new Date();
        CouponCustomer couponCustomer = new CouponCustomer();
        couponCustomer.setConfigId(couponConfig.getId());
        couponCustomer.setCouponType(couponConfig.getCouponType());
        couponCustomer.setCouponName(couponConfig.getCouponName());
        Integer expiryTime = couponConfig.getExpiryTime();
        couponCustomer.setStartDate(DateUtils.getStartOfDay(now));
        couponCustomer.setEndDate(genCouponEndDate(expiryTime));
        couponCustomer.setLimitAmount(couponConfig.getLimitAmount());
        couponCustomer.setCustomerCode(customerCode);
        couponCustomer.setCouponStatus("0");
        couponCustomer.setAccessChannel("0");
        couponCustomer.setBalance(couponConfig.getLimitAmount());
        return couponCustomer;
    }

    public void saveCouponCustomerByMerchant(CouponMerchant couponMerchant) throws Exception {
        Date now = new Date();
        User currUser = UserUtils.getUser();
        String couponConfigId = couponMerchant.getConfigId();
        CouponConfig couponConfig = couponConfigService.get(couponConfigId);
        Integer expiryTime = couponConfig.getExpiryTime();
        CouponCustomer couponCustomer = new CouponCustomer();
        couponCustomer.setConfigId(couponMerchant.getConfigId());
        couponCustomer.setCouponType(couponMerchant.getCouponType());
        couponCustomer.setCouponName(couponMerchant.getCouponName());
        couponCustomer.setStartDate(now);
        couponCustomer.setEndDate(genCouponEndDate(expiryTime));
        couponCustomer.setLimitAmount(couponMerchant.getLimitAmount());
        couponCustomer.setCustomerCode(couponMerchant.getTransferCustomerCode());
        couponCustomer.setCouponStatus("0");
        couponCustomer.setAccessChannel("1");
        couponCustomer.setTransferMerchantCode(currUser.getId());
        couponCustomer.setBalance(couponMerchant.getLimitAmount());
        this.save(couponCustomer);
    }

    /**
     * 生成平台赠送优惠券
     *
     * @param couponName   优惠券名称
     * @param couponType   优惠券类型 0-五折券 1-七折券
     * @param expiryTime   过期时间 从当前时间开始计算 单位为日 到最后一天23:59:59为止
     * @param amount       总金额
     * @param customerCode 赠送会员ID
     */
    public void saveCouponCustomerByPlatform(String couponName, String couponType, Integer expiryTime, Double amount, String customerCode) throws Exception {
        CouponCustomer couponCustomer = new CouponCustomer();
        couponCustomer.setCouponName(couponName);
        couponCustomer.setCouponType(couponType);
        couponCustomer.setEndDate(genCouponEndDate(expiryTime));
        couponCustomer.setStartDate(new Date());
        couponCustomer.setLimitAmount(amount);
        couponCustomer.setCustomerCode(customerCode);
        couponCustomer.setCouponStatus("0");
        couponCustomer.setAccessChannel("2");
        couponCustomer.setBalance(amount);
        couponCustomer.setConfigId("0");
        this.save(couponCustomer);
    }

    private Date genCouponEndDate(Integer expiryTime) throws Exception {
        if (expiryTime == 0) {
            return DateUtils.getEndOfDay(DateUtils.parseDate("9999-12-31"));
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, expiryTime);
            return DateUtils.getEndOfDay(calendar.getTime());
        }
    }

    public Map<String, String> enabledCouponsCount(String customerCode) {
        return dao.enabledCouponsCount(customerCode);
    }

}