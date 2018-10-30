package com.mall.modules.coupon.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.IdGen;
import com.mall.modules.coupon.dao.CouponCustomerDao;
import com.mall.modules.coupon.entity.CouponConfig;
import com.mall.modules.coupon.entity.CouponCustomer;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户优惠券Service
 *
 * @author wankang
 * @since 2018-10-26
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class CouponCustomerService extends CrudService<CouponCustomerDao, CouponCustomer> {

    @Autowired
    private CouponCustomerDao couponCustomerDao;
    @Autowired
    private CouponConfigService couponConfigService;

    @Override
    public CouponCustomer get(String id) {
        return super.get(id);
    }

    @Override
    public List<CouponCustomer> findList(CouponCustomer couponCustomer) {
        return super.findList(couponCustomer);
    }

    @Override
    public Page<CouponCustomer> findPage(Page<CouponCustomer> page, CouponCustomer couponCustomer) {
        return super.findPage(page, couponCustomer);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void save(CouponCustomer couponCustomer) {
        super.save(couponCustomer);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void delete(CouponCustomer couponCustomer) {
        super.delete(couponCustomer);
    }

    public List<CouponCustomer> orderEnabledCoupons(CouponCustomer couponCustomer) {
        return couponCustomerDao.orderEnabledCoupons(couponCustomer);
    }

    public List<CouponCustomer> enabledMerchantLimitCoupons(CouponCustomer couponCustomer) {
        return couponCustomerDao.enabledMerchantLimitCoupons(couponCustomer);
    }

    public Map<String, String> enabledCouponsCount(String customerCode) {
        return couponCustomerDao.enabledCouponsCount(customerCode);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void customerCouponDistribution(String memberId, String couponId) throws Exception {
        Date now = new Date();
        CouponConfig couponConfig = couponConfigService.get(couponId);
        if(null == couponConfig) {
            throw new ServiceException("优惠券规则不存在");
        }
        User currUser = UserUtils.getUser();
        CouponCustomer couponCustomer = new CouponCustomer();
        couponCustomer.setMerchantCode(currUser.getId());
        couponCustomer.setCouponNo(String.valueOf(IdGen.randomLong()));
        couponCustomer.setCouponName(couponConfig.getCouponName());
        couponCustomer.setCustomerCode(memberId);
        couponCustomer.setStartDate(now);
        couponCustomer.setEndDate(new Date(now.getTime() + Long.parseLong(couponConfig.getEapiryTime()) * 24 * 60 * 60 * 1000));
        couponCustomer.setDiscountAmount(couponConfig.getDiscountAmount());
        couponCustomer.setDiscountRate(couponConfig.getDiscountRate());
        couponCustomer.setCouponStatus("0");
        couponCustomer.setCouponType(couponConfig.getCouponType());
        couponCustomer.setLimitAmount(couponConfig.getLimitAmount());
        this.save(couponCustomer);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void updateCouponUsed(String id) {
        couponCustomerDao.updateCouponUsed(id);
    }
}
