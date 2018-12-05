package com.mall.modules.order.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mall.common.persistence.Page;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.api.ApiExceptionHandleUtil;
import com.mall.common.utils.api.ApiPageEntityHandleUtil;
import com.mall.common.web.BaseController;
import com.mall.modules.account.service.AccountService;
import com.mall.modules.activity.entity.ActivityInfo;
import com.mall.modules.activity.service.ActivityInfoService;
import com.mall.modules.coupon.entity.CouponCustomer;
import com.mall.modules.coupon.entity.CouponLog;
import com.mall.modules.coupon.service.CouponCustomerService;
import com.mall.modules.coupon.service.CouponLogService;
import com.mall.modules.coupon.service.CouponMerchantService;
import com.mall.modules.gift.entity.GiftConfig;
import com.mall.modules.gift.entity.GiftConfigGoods;
import com.mall.modules.gift.entity.GiftCustomer;
import com.mall.modules.gift.service.GiftConfigService;
import com.mall.modules.gift.service.GiftCustomerService;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.goods.entity.GoodsStandard;
import com.mall.modules.goods.service.GoodsInfoService;
import com.mall.modules.goods.service.GoodsStandardService;
import com.mall.modules.member.entity.MemberDeliveryAddress;
import com.mall.modules.member.service.MemberDeliveryAddressService;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.order.entity.*;
import com.mall.modules.order.service.OrderInfoService;
import com.mall.modules.order.service.OrderPaymentInfoService;
import com.mall.modules.order.service.OrderReturnsService;
import com.mall.modules.order.service.OrderShoppingCartService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.service.SystemService;
import com.mall.modules.sys.utils.DictUtils;
import com.mall.modules.sys.utils.UserUtils;
import com.sohu.idcenter.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 订单API
 *
 * @author wankang
 * @since 2018-10-12
 */
@RestController
@RequestMapping(value = "${adminPath}/api")
public class OrderInfoApi extends BaseController {

    private static IdWorker idWorker = new IdWorker();
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private MemberDeliveryAddressService memberDeliveryAddressService;
    @Autowired
    private OrderPaymentInfoService orderPaymentInfoService;
    @Autowired
    private OrderShoppingCartService orderShoppingCartService;
    @Autowired
    private CouponCustomerService couponCustomerService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private CouponLogService couponLogService;
    @Autowired
    private CouponMerchantService couponMerchantService;
    @Autowired
    private OrderReturnsService orderReturnsService;

    @Autowired
    private AccountService accountService;
    @Autowired
    private GiftCustomerService giftCustomerService;
    @Autowired
    private GiftConfigService giftConfigService;
    @Autowired
    private GoodsStandardService goodsStandardService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private ActivityInfoService activityInfoService;

    /**
     * 提交订单 订单30分钟内需要支付 否则关闭订单
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "submitOrder", method = RequestMethod.POST)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void submitOrder(HttpServletRequest request, HttpServletResponse response) {
        // 基础数据初始化及基本工具定义
        DecimalFormat df = new DecimalFormat("#.00");
        String orderType = request.getParameter("orderType");
        String orderStatus = "0";
        double amountTotal = 0.00;
        double discountAmountTotal = 0.00;
        // 开始获取表单数据
        String couponCode = request.getParameter("couponCode");
        String goodsList = request.getParameter("goodsList");
        String addressId = request.getParameter("addressId");
        String giftCustomerId = request.getParameter("giftCustomerId");
        String giftConfigId = request.getParameter("giftConfigId");
        String remarks = request.getParameter("remarks");
        // 获取活动数据
        ActivityInfo activityInfo = activityInfoService.enabledActivityInfo(new ActivityInfo());
        GiftCustomer giftCustomer;
        GiftConfig giftConfig;
        try {
            User currUser = UserUtils.getUser();
            if (null == currUser) {
                throw new ServiceException("用户未登录");
            }
            if (StringUtils.isBlank(orderType) || (!"0".equals(orderType) && !"1".equals(orderType))) {
                throw new ServiceException("订单类型不合法");
            }
            if (StringUtils.isBlank(addressId)) {
                throw new ServiceException("未选择收货地址");
            }
            String customerCode = currUser.getId();

            MemberDeliveryAddress memberDeliveryAddress = memberDeliveryAddressService.get(addressId);
            if (null == memberDeliveryAddress) {
                throw new ServiceException("所选地址不合法");
            }

            // 获取优惠券信息
            Double couponBalance = 0.00;
            String couponType = "";
            CouponCustomer couponCustomer = null;
            if (StringUtils.isNotBlank(couponCode) && "0".equals(orderType)) {
                couponCustomer = couponCustomerService.get(couponCode);
                if (null != couponCustomer) {
                    if (couponCustomer.getBalance() <= 0) {
                        throw new ServiceException("优惠券不可使用");
                    }
                    couponBalance = couponCustomer.getBalance();
                    couponType = couponCustomer.getCouponType();
                } else {
                    throw new ServiceException("优惠券余额不足");
                }
            }

            // 组装合并订单支付信息
            OrderPaymentInfo orderPaymentInfo = OrderPaymentInfoService.genDefaultPaymentInfo(orderType);
            String paymentNo = orderPaymentInfo.getPaymentNo();

            // 订单Collection 根据店铺进行订单拆单
            Map<String, OrderInfo> orderInfoMap = Maps.newHashMap();
            // 获取前端传来的商品列表
            JSONArray goodsArr;
            if ("0".equals(orderType)) {
                goodsArr = JSON.parseArray(goodsList);
            } else {
                if (StringUtils.isBlank(giftCustomerId) || StringUtils.isBlank(giftConfigId)) {
                    throw new ServiceException("未选择要兑换的礼包");
                }
                orderStatus = "1";
                giftCustomer = giftCustomerService.get(giftCustomerId);
                giftConfig = giftConfigService.get(giftConfigId);
                if (null == giftCustomer || null == giftConfig) {
                    throw new ServiceException("未选择要兑换的礼包");
                }
                int giftCount = giftCustomer.getGiftCount();
                if (giftCount <= 0) {
                    throw new ServiceException("礼包已兑换");
                }
                if (!customerCode.equals(giftCustomer.getCustomerCode())) {
                    throw new ServiceException("礼包不可兑换");
                }
                if (!giftConfig.getGiftCategory().equals(giftCustomer.getGiftCategory())) {
                    throw new ServiceException("所选礼包不合法");
                }
                giftCustomer.setGiftCount(giftCount - 1);
                giftCustomerService.save(giftCustomer);
                // 获取礼包商品信息
                goodsArr = new JSONArray();
                for (GiftConfigGoods g : giftConfig.getGiftConfigGoodsList()) {
                    JSONObject goodsJson = new JSONObject();
                    goodsJson.put("goodsId", g.getGoodsId());
                    goodsJson.put("goodsCount", g.getGoodsCount());
                    goodsJson.put("goodsStandard", g.getGoodsStandardId());
                    goodsJson.put("goodsSettlementsAmount", g.getGoodsSettlementPrice());
                    goodsArr.add(goodsJson);
                }
                // 是否赠送商户资格
                String merchantQualification = giftCustomer.getMerchantQualification();
                if ("1".equals(merchantQualification)) {
                    couponMerchantService.exchangeGiftGenCoupon(giftConfig, giftCustomerId, customerCode);
                } else if ("0".equals(merchantQualification)) {
                    couponCustomerService.exchangeGiftGenCoupon(giftConfig, giftCustomerId, customerCode);
                }
            }
            if (null == goodsArr || goodsArr.size() <= 0) {
                throw new ServiceException("未选择要购买的商品，请重新选择");
            }
            // 获取商品信息
            List<OrderGoods> orderGoodsList;
            for (int i = 0; i < goodsArr.size(); i++) {
                JSONObject goodsInfoJson = goodsArr.getJSONObject(i);
                String goodsId = goodsInfoJson.getString("goodsId");
                String shoppingCartId = goodsInfoJson.getString("shoppingCartId");
                Double goodsCount = goodsInfoJson.getDouble("goodsCount");
                String goodsStandardId = goodsInfoJson.getString("goodsStandard");
                Double settlementsAmount = goodsInfoJson.getDouble("goodsSettlementsAmount");
                double goodsDiscountAmount = 0.00;
                double goodsActivityDiscountAmount = 0.00;
                String goodsRecommendId = "";
                if (StringUtils.isNotBlank(shoppingCartId)) {
                    OrderShoppingCart orderShoppingCart = orderShoppingCartService.get(shoppingCartId);
                    if (null != orderShoppingCart) {
                        goodsRecommendId = orderShoppingCart.getGoodsRecommendId();
                    }
                }
                // 验证数据正确性
                if (StringUtils.isBlank(goodsId)) {
                    throw new ServiceException("所选商品无效");
                }
                if (goodsCount <= 0) {
                    throw new ServiceException("商品数量不合法");
                }
                if (StringUtils.isBlank(goodsStandardId)) {
                    throw new ServiceException("无效的商品规格");
                }
                GoodsInfo goodsInfo = goodsInfoService.get(goodsId);
                GoodsStandard goodsStandard = goodsStandardService.get(goodsStandardId);
                if (null == goodsStandard || null == goodsInfo || !goodsStandard.getGoodsId().equals(goodsInfo.getId()) || goodsInfo.getStatus() != 3) {
                    throw new ServiceException("不合法的商品信息");
                }
                String merchantCode = goodsInfo.getMerchantId();
                OrderInfo orderInfo;
                String orderNo;
                double orderGoodsAmountTotal;
                double orderGoodsCount;
                double orderAmountTotal;
                double settlementsTotal;
                double orderDiscountAmountTotal;
                double orderActivityDiscountAmount;
                if (orderInfoMap.containsKey(merchantCode)) {
                    orderInfo = orderInfoMap.get(merchantCode);
                    orderGoodsAmountTotal = orderInfo.getGoodsAmountTotal();
                    orderGoodsCount = orderInfo.getGoodsCount();
                    orderGoodsList = orderInfo.getOrderGoodsList();
                    orderAmountTotal = orderInfo.getOrderAmountTotal();
                    settlementsTotal = orderInfo.getSettlementsAmount();
                    orderDiscountAmountTotal = orderInfo.getDiscountAmountTotal();
                    orderActivityDiscountAmount = orderInfo.getActivityDiscountAmount();
                } else {
                    // 初始化订单基础信息
                    orderNo = String.valueOf(idWorker.getId());
                    orderInfo = new OrderInfo();
                    orderInfo.setOrderNo(orderNo);
                    orderInfo.setMerchantCode(merchantCode);
                    orderInfo.setOrderStatus(orderStatus);
                    orderInfo.setOrderType(orderType);
                    orderInfo.setCustomerCode(customerCode);
                    orderInfo.setPaymentNo(paymentNo);
                    orderInfo.setRemarks(remarks);
                    orderGoodsAmountTotal = 0.00;
                    orderGoodsCount = 0.00;
                    orderGoodsList = Lists.newArrayList();
                    orderAmountTotal = 0.00;
                    settlementsTotal = 0.00;
                    orderDiscountAmountTotal = 0.00;
                    orderActivityDiscountAmount = 0.00;

                    // 初始化物流信息
                    OrderLogistics orderLogistics = orderInfoService.genOrderLogistics(orderNo, merchantCode, memberDeliveryAddress);
                    orderInfo.setOrderLogistics(orderLogistics);

                    // 生成运费信息
                    orderInfo.setLogisticsFee(orderLogistics.getLogisticsFee());
                    orderAmountTotal += orderLogistics.getLogisticsFee();

                    //初始化活动信息
                    if (null != activityInfo) {
                        orderInfo.setActivityId(activityInfo.getId());
                    }
                }
                orderGoodsCount += goodsCount;
                //商品单价
                double price = goodsStandard.getPrice();
                double goodsAmountTotal = Double.valueOf(df.format(price * goodsCount));
                double goodsSubAmountTotal = Double.valueOf(df.format(price * goodsCount));
                // 计算优惠金额
                if ("0".equals(orderType)) {
                    if (null != activityInfo) {
                        if (null != couponCustomer && StringUtils.isNotBlank(couponType)) {
                            if ("1".equals(activityInfo.getCouponFlag())) {
                                Double discountRate = activityInfo.getDiscountRate();
                                goodsActivityDiscountAmount = Double.valueOf(df.format(goodsSubAmountTotal * discountRate));
                                goodsSubAmountTotal -= goodsActivityDiscountAmount;
                            }
                        } else {
                            Double discountRate = activityInfo.getDiscountRate();
                            goodsActivityDiscountAmount = Double.valueOf(df.format(goodsSubAmountTotal * discountRate));
                            goodsSubAmountTotal -= goodsActivityDiscountAmount;
                        }
                    }
                    settlementsAmount = goodsStandard.getSettlementsAmount();
                    if (null != couponCustomer && StringUtils.isNotBlank(couponType) && goodsSubAmountTotal > 0.01) {
                        String discountType = goodsInfo.getDiscountType();
                        if ("0".equals(couponType)) {
                            if ("3".equals(discountType) || "1".equals(discountType)) {
                                double discountAmount = Double.valueOf(df.format(goodsSubAmountTotal * 0.5));
                                if (couponBalance > discountAmount) {
                                    goodsDiscountAmount = discountAmount;
                                } else {
                                    goodsDiscountAmount = couponBalance;
                                }
                            }
                        } else if ("1".equals(couponType)) {
                            if ("3".equals(discountType) || "2".equals(discountType)) {
                                double discountAmount = Double.valueOf(df.format(goodsSubAmountTotal * 0.3));
                                if (couponBalance > discountAmount) {
                                    goodsDiscountAmount = discountAmount;
                                } else {
                                    goodsDiscountAmount = couponBalance;
                                }
                            }
                        }
                        couponBalance = couponBalance - goodsDiscountAmount;
                    }
                }
                //结算总价
                settlementsTotal += Double.valueOf(df.format(settlementsAmount * goodsCount));

                goodsSubAmountTotal -= goodsDiscountAmount;
                orderAmountTotal += goodsSubAmountTotal;
                orderGoodsAmountTotal += goodsAmountTotal;
                orderDiscountAmountTotal += goodsDiscountAmount;
                orderActivityDiscountAmount += goodsActivityDiscountAmount;

                orderInfo.setGoodsAmountTotal(orderGoodsAmountTotal);
                orderInfo.setGoodsCount(orderGoodsCount);
                orderInfo.setOrderAmountTotal(orderAmountTotal);
                orderInfo.setSettlementsAmount(settlementsTotal);
                orderInfo.setDiscountAmountTotal(orderDiscountAmountTotal);
                orderInfo.setCouponCode(couponCode);
                orderInfo.setActivityDiscountAmount(orderActivityDiscountAmount);

                OrderGoods orderGoods = orderInfoService.genOrderGoods(goodsInfo);
                orderGoods.setOrderNo(orderInfo.getOrderNo());
                orderGoods.setCount(goodsCount);
                orderGoods.setSubtotal(goodsSubAmountTotal);
                orderGoods.setId("");
                orderGoods.setGoodsPrice(price);
                orderGoods.setGoodsStandard(goodsStandard.getId());
                orderGoods.setGoodsStandardName(goodsStandard.getName());
                orderGoods.setSettlementsAmount(settlementsAmount);
                orderGoods.setGoodsRecommendId(goodsRecommendId);
                orderGoods.setDiscountAmount(goodsDiscountAmount);
                orderGoods.setActivityDiscountAmount(goodsActivityDiscountAmount);
                orderGoodsList.add(orderGoods);

                orderInfo.setOrderGoodsList(orderGoodsList);
                orderInfoMap.put(merchantCode, orderInfo);

                // 删除购物车数据
                if (StringUtils.isNotBlank(shoppingCartId)) {
                    OrderShoppingCart deleteCondition = new OrderShoppingCart();
                    deleteCondition.setId(shoppingCartId);
                    orderShoppingCartService.delete(deleteCondition);
                }
            }

            // 订单后续计算 并生成账单信息
            for (OrderInfo orderInfo : orderInfoMap.values()) {
                if ("1".equals(orderType)) {
                    orderInfo.setPayTime(new Date());
                    orderInfo.setOrderAmountTotal(0.00);
                    orderInfo.setDiscountAmountTotal(0.00);
                    orderInfo.setActivityDiscountAmount(0.00);
                } else {
                    double orderAmountTotal = orderInfo.getOrderAmountTotal();
                    discountAmountTotal += orderInfo.getDiscountAmountTotal();
                    discountAmountTotal += orderInfo.getActivityDiscountAmount();
                    // 修正错误金额
                    if (orderAmountTotal < 0) {
                        orderAmountTotal = 0;
                    }
                    amountTotal += orderAmountTotal;
                    orderInfo.setOrderAmountTotal(orderAmountTotal);
                    // 保存优惠券使用日志
                    if(discountAmountTotal > 0) {
                        CouponLog couponLog = new CouponLog();
                        couponLog.setCouponType(couponType);
                        couponLog.setRemarks("订单消费使用");
                        couponLog.setAmount(orderInfo.getDiscountAmountTotal());
                        couponLog.setProduceChannel("5");
                        couponLog.setType("1");
                        couponLog.setCustomerCode(customerCode);
                        couponLog.setProduceAmount(orderInfo.getGoodsAmountTotal());
                        couponLog.setOrderNo(orderInfo.getOrderNo());
                        couponLogService.save(couponLog);
                    }
                }
                orderInfoService.save(orderInfo);
            }
            if ("0".equals(orderType)) {
                orderPaymentInfo.setAmountTotal(amountTotal);
                orderPaymentInfo.setDiscountAmount(discountAmountTotal);
            } else {
                orderPaymentInfo.setAmountTotal(0.00);
                orderPaymentInfo.setDiscountAmount(0.00);
            }
            orderPaymentInfoService.save(orderPaymentInfo);
            // 保存优惠券信息
            if (null != couponCustomer) {
                couponCustomer.setBalance(couponBalance);
                couponCustomerService.save(couponCustomer);
            }
            renderString(response, ResultGenerator.genSuccessResult(orderPaymentInfo));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 计算订单金额
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "calculationOrderAmount", method = RequestMethod.POST)
    public void calculationOrderAmount(HttpServletRequest request, HttpServletResponse response) {
        // 基础数据初始化及基本工具定义
        DecimalFormat df = new DecimalFormat("#.00");
        String orderType = request.getParameter("orderType");
        double amountTotal = 0.00;
        double discountAmount = 0.00;
        // 开始获取表单数据
        String couponCode = request.getParameter("couponCode");
        String goodsList = request.getParameter("goodsList");
        // 获取活动数据
        ActivityInfo activityInfo = activityInfoService.enabledActivityInfo(new ActivityInfo());
        try {
            // 组装合并订单支付信息
            OrderPaymentInfo orderPaymentInfo = OrderPaymentInfoService.genDefaultPaymentInfo(orderType);
            CouponCustomer couponCustomer = null;
            String couponType = "";
            double couponBalance = 0.00;
            if (StringUtils.isNotBlank(couponCode)) {
                couponCustomer = couponCustomerService.get(couponCode);
                couponBalance = couponCustomer.getBalance();
                couponType = couponCustomer.getCouponType();
            }
            if ("0".equals(orderType)) {
                JSONArray goodsArr = JSON.parseArray(goodsList);
                if (null == goodsArr || goodsArr.size() <= 0) {
                    throw new ServiceException("未选择要购买的商品，请重新选择");
                }
                for (int i = 0; i < goodsArr.size(); i++) {
                    JSONObject goodsInfoJson = goodsArr.getJSONObject(i);
                    String goodsId = goodsInfoJson.getString("goodsId");
                    Double goodsCount = goodsInfoJson.getDouble("goodsCount");
                    String goodsStandardId = goodsInfoJson.getString("goodsStandard");
                    // 验证数据正确性
                    if (StringUtils.isBlank(goodsId)) {
                        throw new ServiceException("所选商品无效");
                    }
                    if (goodsCount <= 0) {
                        throw new ServiceException("商品数量不合法");
                    }
                    if (StringUtils.isBlank(goodsStandardId)) {
                        throw new ServiceException("无效的商品规格");
                    }
                    GoodsInfo goodsInfo = goodsInfoService.get(goodsId);
                    GoodsStandard goodsStandard = goodsStandardService.get(goodsStandardId);
                    //商品单价
                    double price = goodsStandard.getPrice();
                    double goodsAmountTotal = Double.valueOf(df.format(price * goodsCount));
                    if (null == goodsInfo || !goodsStandard.getGoodsId().equals(goodsInfo.getId())) {
                        throw new ServiceException("不合法的商品信息");
                    }
                    // 计算优惠金额
                    double goodsDiscountAmount = 0.00;
                    if (null != activityInfo) {
                        if (null != couponCustomer && StringUtils.isNotBlank(couponType)) {
                            if ("1".equals(activityInfo.getCouponFlag())) {
                                Double discountRate = activityInfo.getDiscountRate();
                                goodsDiscountAmount += Double.valueOf(df.format(goodsAmountTotal * discountRate));
                                goodsAmountTotal -= goodsDiscountAmount;
                            }
                        } else {
                            Double discountRate = activityInfo.getDiscountRate();
                            goodsDiscountAmount += Double.valueOf(df.format(goodsAmountTotal * discountRate));
                            goodsAmountTotal -= goodsDiscountAmount;
                        }
                    }
                    double couponDiscountAmount = 0.00;
                    if (null != couponCustomer && StringUtils.isNotBlank(couponType) && goodsAmountTotal > 0.01) {
                        String discountType = goodsInfo.getDiscountType();
                        if ("0".equals(couponType)) {
                            if ("3".equals(discountType) || "1".equals(discountType)) {
                                couponDiscountAmount = Double.valueOf(df.format(goodsAmountTotal * 0.5));
                                if (couponBalance <= couponDiscountAmount) {
                                    couponDiscountAmount = couponBalance;
                                }
                            }
                        } else if ("1".equals(couponType)) {
                            if ("3".equals(discountType) || "2".equals(discountType)) {
                                couponDiscountAmount = Double.valueOf(df.format(goodsAmountTotal * 0.3));
                                if (couponBalance <= couponDiscountAmount) {
                                    couponDiscountAmount = couponBalance;
                                }
                            }
                        }
                        couponBalance = couponBalance - couponDiscountAmount;
                    }
                    goodsAmountTotal -= couponDiscountAmount;
                    discountAmount += couponDiscountAmount;
                    discountAmount += goodsDiscountAmount;
                    amountTotal += goodsAmountTotal;
                }
                orderPaymentInfo.setAmountTotal(Double.parseDouble(df.format(amountTotal)));
                orderPaymentInfo.setDiscountAmount(discountAmount);
            } else if ("1".equals(orderType)) {
                orderPaymentInfo.setDiscountAmount(0.00);
                orderPaymentInfo.setAmountTotal(0.00);
            }
            renderString(response, ResultGenerator.genSuccessResult(orderPaymentInfo));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 用户取消订单
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "orderCancel", method = RequestMethod.POST)
    public void orderCancel(HttpServletRequest request, HttpServletResponse response) {
        String orderId = request.getParameter("orderId");
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            if (StringUtils.isBlank(orderId)) {
                throw new ServiceException("未选择要取消的订单");
            }
            OrderInfo updateCondition = new OrderInfo();
            updateCondition.setCustomerCode(customerCode);
            updateCondition.setId(orderId);
            OrderInfo orderInfo = orderInfoService.getOrderBasicInfo(updateCondition);
            if (null == orderInfo) {
                throw new ServiceException("订单不存在");
            }
            if (!"0".equals(orderInfo.getOrderStatus())) {
                throw new ServiceException("操作不合法");
            }
            int result = orderInfoService.orderCancel(updateCondition);
            if (result > 0) {
                renderString(response, ResultGenerator.genSuccessResult());
            } else {
                throw new ServiceException("订单取消失败");
            }
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 用户确认收货 订单完成
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "orderComplete", method = RequestMethod.POST)
    public void orderComplete(HttpServletRequest request, HttpServletResponse response) {
        String orderId = request.getParameter("orderId");
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            if (StringUtils.isBlank(orderId)) {
                throw new ServiceException("未选择订单");
            }
            OrderInfo updateCondition = new OrderInfo();
            updateCondition.setCustomerCode(customerCode);
            updateCondition.setId(orderId);
            OrderInfo orderInfo = orderInfoService.getOrderBasicInfo(updateCondition);
            if (null == orderInfo) {
                throw new ServiceException("订单不存在");
            }
            if (!"2".equals(orderInfo.getOrderStatus())) {
                throw new ServiceException("操作不合法");
            }
            int result = orderInfoService.orderComplete(updateCondition);
            if (result > 0) {
                renderString(response, ResultGenerator.genSuccessResult());
            } else {
                throw new ServiceException("确认收货失败");
            }
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 订单状态统计
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "orderCount", method = RequestMethod.POST)
    public void orderCount(HttpServletRequest request, HttpServletResponse response) {
        try {
            User currUser = UserUtils.getUser();
            String customerCode = currUser.getId();
            renderString(response, ResultGenerator.genSuccessResult(orderInfoService.orderCount(customerCode)));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 根据订单状态条件查询订单列表
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "orderList", method = RequestMethod.POST)
    public void orderList(HttpServletRequest request, HttpServletResponse response) {
        String orderStatus = request.getParameter("orderStatus");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        User currUser = UserUtils.getUser();
        try {
            Page<OrderInfo> page = ApiPageEntityHandleUtil.packagePage(new Page<OrderInfo>(), pageNo, pageSize);
            String customerCode = currUser.getId();
            OrderInfo queryCondition = new OrderInfo();
            queryCondition.setOrderStatus(orderStatus);
            queryCondition.setCustomerCode(customerCode);
            renderString(response, ResultGenerator.genSuccessResult(orderInfoService.findOrderDetailList(page, queryCondition).getList()));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 删除订单 逻辑删除 仅让用户前端查询不到
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "deleteOrder", method = RequestMethod.POST)
    public void deleteOrder(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        String orderId = request.getParameter("orderId");
        try {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setId(orderId);
            orderInfo.setCustomerCode(customerCode);
            int result = orderInfoService.deleteByUser(orderInfo);
            if (result > 0) {
                renderString(response, ResultGenerator.genSuccessResult());
            } else {
                renderString(response, ResultGenerator.genFailResult("删除失败，订单有误"));
            }
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 根据订单查询物流信息
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "logisticsInfo", method = RequestMethod.POST)
    public void logisticsInfo(HttpServletRequest request, HttpServletResponse response) {
        // 订单编号或售后单编号
        String orderNo = request.getParameter("orderNo");
        // 订单物流类型 0-订单 1-售后单
        String logisticsType = request.getParameter("logisticsType");
        try {
            if (StringUtils.isBlank(orderNo)) {
                throw new ServiceException("未选择要查询的订单");
            }
            String expressNo = null;
            String expressType = null;
            if (StringUtils.isBlank(logisticsType) || "0".equals(logisticsType)) {
                OrderLogistics orderLogistics = orderInfoService.getOrderLogistics(orderNo);
                if (null != orderLogistics) {
                    expressNo = orderLogistics.getExpressNo();
                    expressType = orderLogistics.getLogisticsType();
                }
            } else {
                OrderReturns queryCondition = new OrderReturns();
                queryCondition.setReturnsNo(orderNo);
                List<OrderReturns> orderReturnsList = orderReturnsService.findList(queryCondition);
                if (null != orderReturnsList && orderReturnsList.size() > 0) {
                    OrderReturns orderReturns = orderReturnsList.get(0);
                    expressNo = orderReturns.getExpressNo();
                    expressType = orderReturns.getLogisticsType();
                }
            }
            OrderLogisticsInfo orderLogisticsInfo = null;
            if (StringUtils.isNotBlank(expressNo) && StringUtils.isNotBlank(expressType)) {
                orderLogisticsInfo = orderInfoService.getOrderLogisticsInfo(expressType, expressNo);
            }
            if (null != orderLogisticsInfo) {
                JSONObject jsonObject = JSON.parseObject(orderLogisticsInfo.getLastResult());
                jsonObject.put("expressName", DictUtils.getDictLabel(jsonObject.getString("com"), "express_type", ""));
                renderString(response, ResultGenerator.genSuccessResult(jsonObject));
            } else {
                renderString(response, ResultGenerator.genSuccessResult(new JSONObject()));
            }
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 发货提醒
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "remind", method = RequestMethod.POST)
    public void remind(HttpServletRequest request, HttpServletResponse response) {
        String orderId = request.getParameter("orderId");
        try {
            if (StringUtils.isBlank(orderId)) {
                throw new ServiceException("未选择订单");
            }
            OrderInfo orderInfo = orderInfoService.get(orderId);
            if (null == orderInfo) {
                throw new ServiceException("不合法的订单信息");
            }
            Date createDate = orderInfo.getCreateDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(createDate);
            calendar.add(Calendar.HOUR, 24);
            if (System.currentTimeMillis() <= calendar.getTimeInMillis()) {
                throw new ServiceException("订单超过24小时未发货才可提醒发货");
            }
            if (!"1".equalsIgnoreCase(orderInfo.getOrderStatus())) {
                throw new ServiceException("未发货订单才能提醒发货");
            }
            if (!"0".equalsIgnoreCase(orderInfo.getRemindFlag())) {
                throw new ServiceException("已经提醒过商家发货了");
            }
            orderInfoService.remind(orderId);
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 验证商品信息
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "validateGoodsInfo", method = RequestMethod.POST)
    public void validateGoodsInfo(HttpServletRequest request, HttpServletResponse response) {
        String goodsInfoIds = request.getParameter("goodsInfo");
        try {
            if(StringUtils.isBlank(goodsInfoIds)) {
                throw new ServiceException("参数不能为空");
            }
            JSONArray goodsInfoArr = JSONArray.parseArray(goodsInfoIds);
            if(null == goodsInfoArr || goodsInfoArr.size() <= 0) {
                throw new ServiceException("未选择商品");
            }
            Set<String> notEnoughGoodsId = Sets.newHashSet();
            for (Object goodIdObj : goodsInfoArr) {
                String goodId = (String) goodIdObj;
                GoodsInfo goodsInfo = goodsInfoService.get(goodId);
                if(null == goodsInfo) {
                    throw new ServiceException("商品信息不合法");
                }
                if (goodsInfo.getStatus() != 3) {
                    notEnoughGoodsId.add(goodsInfo.getId());
                }
            }
            renderString(response, ResultGenerator.genSuccessResult(notEnoughGoodsId));
        }catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 快递100推送服务回调接口
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "kuaidi100Callback")
    public void kuaidi100Callback(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = request.getParameter("param");
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        JSONObject returnResult = new JSONObject();
        OrderLogisticsInfo orderLogisticsInfo = new OrderLogisticsInfo();
        JSONObject lastResult = jsonObject.getJSONObject("lastResult");
        String expressNo = lastResult.getString("nu");
        String expressType = lastResult.getString("com");
        String lastResultAll = jsonObject.toJSONString();
        String lastResultStr = lastResult.toJSONString();
        orderLogisticsInfo.setExpressType(expressType);
        orderLogisticsInfo.setExpressNo(expressNo);
        orderLogisticsInfo.setLastResultAll(lastResultAll);
        orderLogisticsInfo.setLastResult(lastResultStr);
        orderInfoService.saveOrderLogisticsInfo(orderLogisticsInfo);
        returnResult.put("result", true);
        returnResult.put("returnCode", "200");
        returnResult.put("message", "成功");
        renderString(response, returnResult);
    }

}
