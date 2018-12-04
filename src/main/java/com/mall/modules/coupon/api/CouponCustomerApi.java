package com.mall.modules.coupon.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.api.ApiExceptionHandleUtil;
import com.mall.common.web.BaseController;
import com.mall.modules.coupon.entity.CouponCustomer;
import com.mall.modules.coupon.entity.CouponLog;
import com.mall.modules.coupon.service.CouponCustomerService;
import com.mall.modules.coupon.service.CouponLogService;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.goods.service.GoodsInfoService;
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
    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private CouponLogService couponLogService;

    /**
     * 根据订单数据获取可用卡券列表
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "orderEnabledCoupons", method = RequestMethod.POST)
    public void orderEnabledCoupons(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        String goodsIdArrStr = request.getParameter("goodsIdArr");
        try {
            if (StringUtils.isBlank(goodsIdArrStr)) {
                throw new ServiceException("未选择商品");
            }
            String[] goodsIdArr = goodsIdArrStr.split(",");
            Set<String> goodsIdSet = Sets.newHashSet();
            Set<String> discountType = Sets.newHashSet();
            Collections.addAll(goodsIdSet, goodsIdArr);
            for (String goodsId : goodsIdSet) {
                GoodsInfo goodsInfo = goodsInfoService.get(goodsId);
                if (null != goodsInfo) {
                    discountType.add(goodsInfo.getDiscountType());
                }
            }
            CouponCustomer queryCondition = new CouponCustomer();
            queryCondition.setCustomerCode(customerCode);
            boolean allCoupon = discountType.contains("3") || discountType.contains("1") || discountType.contains("2");
            if (allCoupon) {
                if(!discountType.contains("3") && !(discountType.contains("1") && discountType.contains("2"))) {
                    if (discountType.contains("1")) {
                        queryCondition.setCouponType("0");
                    } else if (discountType.contains("2")) {
                        queryCondition.setCouponType("1");
                    }
                }
            } else {
                renderString(response, ResultGenerator.genSuccessResult(Lists.newArrayList()));
            }
            List<CouponCustomer> couponCustomers = couponCustomerService.findList(queryCondition);
            renderString(response, ResultGenerator.genSuccessResult(couponCustomers));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 获取当前用户可用所有优惠券列表
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "couponsList", method = RequestMethod.POST)
    public void enabledCoupons(HttpServletRequest request, HttpServletResponse response) {
        String couponType = request.getParameter("couponType");
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            CouponCustomer queryCondition = new CouponCustomer();
            queryCondition.setCouponType(couponType);
            queryCondition.setCustomerCode(customerCode);
            List<CouponCustomer> couponCustomers = couponCustomerService.findList(queryCondition);
            renderString(response, ResultGenerator.genSuccessResult(couponCustomers));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 获取用户优惠券流水
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "customerCouponLog", method = RequestMethod.POST)
    public void customerCouponLog(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        String couponType = request.getParameter("couponType");
        try {
            CouponLog queryCondition = new CouponLog();
            queryCondition.setCouponType(couponType);
            queryCondition.setCustomerCode(customerCode);
            queryCondition.setType("1");
            List<CouponLog> couponLogs = couponLogService.findList(queryCondition);
            renderString(response, ResultGenerator.genSuccessResult(couponLogs));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }
}
