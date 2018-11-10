package com.mall.modules.order.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mall.common.persistence.Page;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.api.ApiExceptionHandleUtil;
import com.mall.common.utils.api.ApiPageEntityHandleUtil;
import com.mall.common.web.BaseController;
import com.mall.modules.account.service.AccountInfoService;
import com.mall.modules.coupon.entity.CouponCustomer;
import com.mall.modules.coupon.service.CouponCustomerService;
//import com.mall.modules.gift.entity.GiftCustomer;
//import com.mall.modules.gift.entity.GiftCustomerGoods;
//import com.mall.modules.gift.entity.GiftExchangeLog;
//import com.mall.modules.gift.service.GiftCustomerService;
//import com.mall.modules.gift.service.GiftExchangeLogService;
import com.mall.modules.coupon.service.CouponMerchantService;
import com.mall.modules.gift.entity.GiftConfig;
import com.mall.modules.gift.entity.GiftConfigCoupon;
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
import com.mall.modules.order.entity.*;
import com.mall.modules.order.service.OrderInfoService;
import com.mall.modules.order.service.OrderPaymentInfoService;
import com.mall.modules.order.service.OrderShoppingCartService;
import com.mall.modules.sys.entity.Role;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.service.SystemService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private CouponMerchantService couponMerchantService;

    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private GiftCustomerService giftCustomerService;
    @Autowired
    private GiftConfigService giftConfigService;
    @Autowired
    private GoodsStandardService goodsStandardService;
    @Autowired
    private SystemService systemService;

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
        // 初始化优惠数据
        double discountAmountTotal = 0.00;
        // 开始获取表单数据
        String couponCode = request.getParameter("couponCode");
        String goodsList = request.getParameter("goodsList");
        String addressId = request.getParameter("addressId");
        String giftCustomerId = request.getParameter("giftCustomerId");
        String giftConfigId = request.getParameter("giftConfigId");
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

            CouponCustomer couponCustomer = null;
            if (StringUtils.isNotBlank(couponCode)) {
                couponCustomer = couponCustomerService.get(couponCode);
            }

            // 组装合并订单支付信息
            OrderPaymentInfo orderPaymentInfo = OrderPaymentInfoService.genDefaultPaymentInfo(orderType);
            String paymentNo = orderPaymentInfo.getPaymentNo();

            // 订单Collection 根据店铺进行订单拆单
            Map<String, OrderInfo> orderInfoMap = Maps.newHashMap();
            // 获取前端传来的商品列表
            JSONArray goodsArr;
            goodsArr = JSON.parseArray(goodsList);
            if ("0".equals(orderType)) {
                goodsArr = JSON.parseArray(goodsList);
            } else {
                if (StringUtils.isBlank(giftCustomerId)) {
                    throw new ServiceException("未选择要兑换的礼包");
                }
                orderStatus = "1";
                giftCustomer = giftCustomerService.get(giftCustomerId);
                giftConfig = giftConfigService.get(giftConfigId);
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
                    goodsArr.add(goodsJson);
                }
                // 是否赠送商户资格
                String merchantQualification = giftCustomer.getMerchantQualification();
                if ("1".equals(merchantQualification)) {
                    // 赋予用户未审核商户权限
                    List<Role> roleList = currUser.getRoleList();
                    roleList.add(new Role("1000"));
                    systemService.saveUser(currUser);
                    for (GiftConfigCoupon giftConfigCoupon : giftConfig.getGiftConfigCouponList()) {
                        couponMerchantService.exchangeGiftGenCoupon(giftConfigCoupon, giftCustomerId);
                    }
                } else if ("0".equals(merchantQualification)) {
                    for (GiftConfigCoupon giftConfigCoupon : giftConfig.getGiftConfigCouponList()) {
                        couponCustomerService.exchangeGiftGenCoupon(giftConfigCoupon, giftCustomerId);
                    }
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
                String goodsRecommendId = "";
                if ("0".equals(orderType) && StringUtils.isBlank(shoppingCartId)) {
                    throw new ServiceException("不合法的订单信息");
                }
                if ("0".equals(orderType)) {
                    OrderShoppingCart orderShoppingCart = orderShoppingCartService.get(shoppingCartId);
                    goodsRecommendId = orderShoppingCart.getGoodsRecommendId();
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
                if (null == goodsStandard || null == goodsInfo || !goodsStandard.getGoodsId().equals(goodsInfo.getId())) {
                    throw new ServiceException("不合法的商品信息");
                }
                String merchantCode = goodsInfo.getMerchantId();
                OrderInfo orderInfo;
                String orderNo;
                double orderGoodsAmountTotal;
                double orderGoodsCount;
                double orderAmountTotal;
                double settlementsTotal;
                if (orderInfoMap.containsKey(merchantCode)) {
                    orderInfo = orderInfoMap.get(merchantCode);
                    orderGoodsAmountTotal = orderInfo.getGoodsAmountTotal();
                    orderGoodsCount = orderInfo.getGoodsCount();
                    orderGoodsList = orderInfo.getOrderGoodsList();
                    orderAmountTotal = orderInfo.getOrderAmountTotal();
                    settlementsTotal = orderInfo.getSettlementsAmount();
                } else {
                    orderNo = String.valueOf(idWorker.getId());
                    orderInfo = new OrderInfo();
                    orderInfo.setOrderNo(orderNo);
                    orderInfo.setMerchantCode(merchantCode);
                    orderInfo.setOrderStatus(orderStatus);
                    orderInfo.setOrderType(orderType);
                    orderInfo.setCustomerCode(customerCode);
                    orderInfo.setPaymentNo(paymentNo);
                    orderGoodsAmountTotal = 0.00;
                    orderGoodsCount = 0.00;
                    orderGoodsList = Lists.newArrayList();
                    orderAmountTotal = 0.00;
                    settlementsTotal = 0.00;

                    // 初始化物流信息
                    OrderLogistics orderLogistics = orderInfoService.genOrderLogistics(orderNo, merchantCode, memberDeliveryAddress);
                    orderInfo.setOrderLogistics(orderLogistics);

                    // 生成运费信息
                    orderInfo.setLogisticsFee(orderLogistics.getLogisticsFee());
                    orderAmountTotal += orderLogistics.getLogisticsFee();
                }
                orderGoodsCount += goodsCount;
                //商品单价
                double price = goodsStandard.getPrice();
                double settlementsAmount = goodsStandard.getSettlementsAmount();
                double goodsAmountTotal = Double.valueOf(df.format(price * goodsCount));
                //结算总价
                settlementsTotal += Double.valueOf(df.format(settlementsAmount * goodsCount));

                orderAmountTotal += goodsAmountTotal;

                orderGoodsAmountTotal += goodsAmountTotal;
                orderInfo.setGoodsAmountTotal(orderGoodsAmountTotal);
                orderInfo.setGoodsCount(orderGoodsCount);
                orderInfo.setOrderAmountTotal(orderAmountTotal);
                orderInfo.setSettlementsAmount(settlementsTotal);

                OrderGoods orderGoods = orderInfoService.genOrderGoods(goodsInfo);
                orderGoods.setOrderNo(orderInfo.getOrderNo());
                orderGoods.setCount(goodsCount);
                orderGoods.setSubtotal(goodsAmountTotal);
                orderGoods.setId("");
                orderGoods.setGoodsPrice(price);
                orderGoods.setGoodsStandard(goodsStandard.getId());
                orderGoods.setSettlementsAmount(settlementsAmount);
                orderGoods.setGoodsRecommendId(goodsRecommendId);
                orderGoodsList.add(orderGoods);

                orderInfo.setOrderGoodsList(orderGoodsList);
                orderInfoMap.put(merchantCode, orderInfo);

                // 删除购物车数据
                OrderShoppingCart deleteCondition = new OrderShoppingCart();
                deleteCondition.setId(shoppingCartId);
                orderShoppingCartService.delete(deleteCondition);
            }

            for (OrderInfo orderInfo : orderInfoMap.values()) {
                double orderDiscountAmountTotal = 0.00;
                String merchantCode = orderInfo.getMerchantCode();
                /*if (null != couponCustomer && merchantCode.equals(couponCustomer.getMerchantCode()) && "0".equals(orderType)) {
                    String couponType = couponCustomer.getCouponType();
                    if ("1".equals(couponType)) {
                        orderDiscountAmountTotal = couponCustomer.getDiscountAmount();
                    } else if ("0".equals(couponType)) {
                        orderDiscountAmountTotal = Double.valueOf(df.format(couponCustomer.getDiscountRate() * orderInfo.getOrderAmountTotal()));
                    } else if ("2".equals(couponType)) {
                        Double limitAmount = couponCustomer.getLimitAmount();
                        if (orderInfo.getOrderAmountTotal() >= limitAmount) {
                            orderDiscountAmountTotal = couponCustomer.getDiscountAmount();
                        } else {
                            throw new ServiceException("优惠券不可用");
                        }
                    } else {
                        throw new ServiceException("优惠券不可用");
                    }
                    orderInfo.setCouponCode(couponCode);
                    // 修改优惠券为已使用
                    couponCustomerService.updateCouponUsed(couponCustomer.getId());
                } else*/
                if ("1".equals(orderType)) {
                    orderInfo.setPayTime(new Date());
                }
                double orderAmountTotal = orderInfo.getOrderAmountTotal() - orderDiscountAmountTotal;
                if (orderAmountTotal < 0) {
                    orderAmountTotal = 0;
                }
                amountTotal += orderAmountTotal;
                orderInfo.setOrderAmountTotal(orderAmountTotal);
                orderInfo.setDiscountAmountTotal(orderDiscountAmountTotal);
                orderInfoService.save(orderInfo);
            }
            // 扣减优惠
            amountTotal -= discountAmountTotal;
            if ("0".equals(orderType)) {
                orderPaymentInfo.setAmountTotal(amountTotal);
            } else {
                orderPaymentInfo.setAmountTotal(0.00);
            }
            orderPaymentInfoService.save(orderPaymentInfo);
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
                //订单完成生成佣金信息 根据订单信息创建账单流水
                accountInfoService.createAccountFlow(orderInfo);
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
        String orderId = request.getParameter("orderId");
        try {
            if (StringUtils.isBlank(orderId)) {
                throw new ServiceException("未选择要查询的订单");
            }
            // todo logistics info query
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }
}
