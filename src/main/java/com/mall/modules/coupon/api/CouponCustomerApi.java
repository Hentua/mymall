package com.mall.modules.coupon.api;

import com.google.common.collect.Sets;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.api.ApiExceptionHandleUtil;
import com.mall.common.web.BaseController;
import com.mall.modules.coupon.entity.CouponCustomer;
import com.mall.modules.coupon.service.CouponCustomerService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 用户优惠券API
 *
 * @author wankang
 * @since 2018-10-26
 */
@RestController
@RequestMapping(value = "${adminPath}/api")
public class CouponCustomerApi extends BaseController {

    @Autowired
    private CouponCustomerService couponCustomerService;

    /**
     * 根据订单数据获取可用卡券列表
     *
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "orderEnabledCoupons", method = RequestMethod.POST)
    public void orderEnabledCoupons(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        String [] merchantCodes = request.getParameterValues("merchantCodes");
        try {
            if(merchantCodes.length <= 0) {
                throw new ServiceException("未选择商品");
            }
            Set<String> merchantCodeSet = Sets.newHashSet();
            Collections.addAll(merchantCodeSet, merchantCodes);
            CouponCustomer queryCondition = new CouponCustomer();
            queryCondition.setCustomerCode(customerCode);
            queryCondition.setMerchantCodes(merchantCodeSet);
            List<CouponCustomer> couponCustomers = couponCustomerService.orderEnabledCoupons(queryCondition);
            renderString(response, ResultGenerator.genSuccessResult(couponCustomers));
        }catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 获取当前用户可用所有优惠券列表
     *
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "couponsList", method = RequestMethod.POST)
    public void enabledCoupons(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            CouponCustomer queryCondition = new CouponCustomer();
            queryCondition.setCustomerCode(customerCode);
            List<CouponCustomer> couponCustomers = couponCustomerService.findList(queryCondition);
            renderString(response, ResultGenerator.genSuccessResult(couponCustomers));
        }catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }
}