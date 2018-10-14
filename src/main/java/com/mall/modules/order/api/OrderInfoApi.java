package com.mall.modules.order.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.ApiExceptionHandleUtil;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.goods.service.GoodsInfoService;
import com.mall.modules.order.entity.OrderGoods;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.order.service.OrderInfoService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import com.sohu.idcenter.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "submitOrder", method = RequestMethod.POST)
    public void submitOrder(HttpServletRequest request, HttpServletResponse response) {

        // 基础数据初始化及基本工具定义
        DecimalFormat df = new DecimalFormat("#.00");
        String orderType = "0";
        String orderStatus = "0";
        double amountTotal = 0.00;
        try {
            User currUser = UserUtils.getUser();
            if (null == currUser) {
                throw new ServiceException("用户未登录");
            }
            String customerCode = currUser.getId();

            // 开始获取表单数据
            String couponCode = request.getParameter("couponCode");
            String goodsList = request.getParameter("goodsList");
            String addressId = request.getParameter("addressId");

            // todo coupon discount and logistics info

            // 订单Collection 根据店铺进行订单拆单
            Map<String, OrderInfo> orderInfoMap = Maps.newHashMap();
            // 获取前端传来的商品列表
            JSONArray goodsArr = JSON.parseArray(goodsList);
            // 获取商品信息
            for (int i = 0; i < goodsArr.size(); i++) {

                // todo 返佣金

                JSONObject goodsInfoJson = goodsArr.getJSONObject(i);
                String goodsId = goodsInfoJson.getString("goodsId");
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
                List<OrderGoods> orderGoodsList;
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
                    orderGoodsAmountTotal = 0.00;
                    orderGoodsCount = 0.00;
                    orderGoodsList = Lists.newArrayList();
                    orderAmountTotal = 0.00;
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
                orderGoods.setOrderNo(orderInfo);
                orderGoods.setCount(goodsCount);
                orderGoods.setSubtotal(goodsAmountTotal);
                orderGoodsList.add(orderGoods);

                orderInfo.setOrderGoodsList(orderGoodsList);
                orderInfoMap.put(merchantCode, orderInfo);
                amountTotal += orderAmountTotal;
            }

            for (OrderInfo orderInfo : orderInfoMap.values()) {
                orderInfoService.save(orderInfo);
            }
            renderString(response, ResultGenerator.genSuccessResult(amountTotal));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }
}
