package com.mall.modules.order.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mall.common.persistence.Page;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.ApiExceptionHandleUtil;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.goods.service.GoodsInfoService;
import com.mall.modules.member.entity.MemberDeliveryAddress;
import com.mall.modules.member.service.MemberDeliveryAddressService;
import com.mall.modules.order.entity.*;
import com.mall.modules.order.service.OrderInfoService;
import com.mall.modules.order.service.OrderPaymentInfoService;
import com.mall.modules.order.service.OrderShoppingCartService;
import com.mall.modules.sys.entity.User;
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

    /**
     * 提交订单 订单30分钟内需要支付 否则关闭订单
     *
     * @param request 请求体
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
        String paymentNo = String.valueOf(idWorker.getId());
        try {
            User currUser = UserUtils.getUser();
            if (null == currUser) {
                throw new ServiceException("用户未登录");
            }
            if(StringUtils.isBlank(addressId)) {
                throw new ServiceException("未选择收货地址");
            }
            String customerCode = currUser.getId();

            MemberDeliveryAddress memberDeliveryAddress = memberDeliveryAddressService.get(addressId);
            if(null == memberDeliveryAddress) {
                throw new ServiceException("所选地址不合法");
            }
            // todo coupon discount

            // 组装合并订单支付信息
            OrderPaymentInfo orderPaymentInfo = new OrderPaymentInfo();
            orderPaymentInfo.setPaymentNo(paymentNo);
            orderPaymentInfo.setPayChannel(orderType);

            // 订单Collection 根据店铺进行订单拆单
            Map<String, OrderInfo> orderInfoMap = Maps.newHashMap();
            // 获取前端传来的商品列表
            JSONArray goodsArr = JSON.parseArray(goodsList);
            // 获取商品信息
            List<OrderGoods> orderGoodsList;
            for (int i = 0; i < goodsArr.size(); i++) {

                JSONObject goodsInfoJson = goodsArr.getJSONObject(i);
                String goodsId = goodsInfoJson.getString("goodsId");
                String shoppingCartId = goodsInfoJson.getString("shoppingCartId");
                double goodsCount = goodsInfoJson.getDouble("goodsCount");
                // 验证数据正确性
                if(StringUtils.isBlank(goodsId)) {
                    throw new ServiceException("所选商品无效");
                }
                if(goodsCount <= 0) {
                    throw new ServiceException("商品数量不合法");
                }
                GoodsInfo goodsInfo = goodsInfoService.get(goodsId);
                String merchantCode = goodsInfo.getMerchantId();
                OrderInfo orderInfo;
                String orderNo;
                double orderGoodsAmountTotal;
                double orderGoodsCount;
                double orderAmountTotal;
                if(orderInfoMap.containsKey(merchantCode)) {
                    orderInfo = orderInfoMap.get(merchantCode);
                    orderGoodsAmountTotal = orderInfo.getGoodsAmountTotal();
                    orderGoodsCount = orderInfo.getGoodsCount();
                    orderGoodsList = orderInfo.getOrderGoodsList();
                    orderAmountTotal = orderInfo.getOrderAmountTotal();
                }else {
                    orderNo = String.valueOf(idWorker.getId());
                    orderInfo = new OrderInfo();
                    orderInfo.setCouponCode(couponCode);
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

                    // 初始化物流信息
                    OrderLogistics orderLogistics = orderInfoService.genOrderLogistics(orderNo, merchantCode, memberDeliveryAddress);
                    orderInfo.setOrderLogistics(orderLogistics);

                    // 生成运费信息
                    orderInfo.setLogisticsFee(orderLogistics.getLogisticsFee());
                    orderAmountTotal += orderLogistics.getLogisticsFee();
                }
                orderGoodsCount += goodsCount;
                double price = goodsInfo.getGoodsPrice();
                double goodsAmountTotal = Double.valueOf(df.format(price * goodsCount));

                orderAmountTotal += goodsAmountTotal;

                orderGoodsAmountTotal += goodsAmountTotal;
                orderInfo.setGoodsAmountTotal(orderGoodsAmountTotal);
                orderInfo.setGoodsCount(orderGoodsCount);
                orderInfo.setOrderAmountTotal(orderAmountTotal);

                OrderGoods orderGoods = orderInfoService.genOrderGoods(goodsInfo);
                orderGoods.setOrderNo(orderInfo.getOrderNo());
                orderGoods.setCount(goodsCount);
                orderGoods.setSubtotal(goodsAmountTotal);
                orderGoods.setId("");
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
                double orderAmountTotal = orderInfo.getOrderAmountTotal() - orderDiscountAmountTotal;
                amountTotal += orderAmountTotal;
                orderInfo.setOrderAmountTotal(orderAmountTotal);
                orderInfo.setDiscountAmountTotal(orderDiscountAmountTotal);
                orderInfoService.save(orderInfo);
            }
            // 扣减优惠
            amountTotal -= discountAmountTotal;
            orderPaymentInfo.setAmountTotal(amountTotal);
            orderPaymentInfoService.save(orderPaymentInfo);
            renderString(response, ResultGenerator.genSuccessResult(orderPaymentInfo));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 订单状态统计
     *
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "orderCount", method = RequestMethod.POST)
    public void orderCount(HttpServletRequest request, HttpServletResponse response) {
        try {
            User currUser = UserUtils.getUser();
            String customerCode = currUser.getId();
            renderString(response, ResultGenerator.genSuccessResult(orderInfoService.orderCount(customerCode)));
        }catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 根据订单状态条件查询订单列表
     *
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "orderList", method = RequestMethod.POST)
    public void orderList(HttpServletRequest request, HttpServletResponse response) {
        String orderStatus = request.getParameter("orderStatus");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        User currUser = UserUtils.getUser();
        try {
            if(StringUtils.isBlank(pageNo)) {
                pageNo = "0";
            }
            if (StringUtils.isBlank(pageSize)) {
                pageSize = "10";
            }
            Page<OrderInfo> page = new Page<OrderInfo>();
            page.setPageNo(Integer.valueOf(pageNo));
            page.setPageSize(Integer.valueOf(pageSize));
            String customerCode = currUser.getId();
            OrderInfo queryCondition = new OrderInfo();
            queryCondition.setOrderStatus(orderStatus);
            queryCondition.setCustomerCode(customerCode);
            renderString(response, ResultGenerator.genSuccessResult(orderInfoService.findOrderDetailList(page, queryCondition).getList()));
        }catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 删除订单 逻辑删除 仅让用户前端查询不到
     *
     * @param request 请求体
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
            if(result > 0) {
                renderString(response, ResultGenerator.genSuccessResult());
            }else {
                renderString(response, ResultGenerator.genFailResult("删除失败，订单有误"));
            }
        }catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }
}
