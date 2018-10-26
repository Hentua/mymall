package com.mall.modules.coupon.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.coupon.dao.CouponCustomerDao;
import com.mall.modules.coupon.entity.CouponCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
