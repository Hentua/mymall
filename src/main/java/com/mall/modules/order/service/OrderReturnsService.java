package com.mall.modules.order.service;

import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.utils.StringUtils;
import com.mall.modules.account.service.AccountService;
import com.mall.modules.coupon.entity.CouponCustomer;
import com.mall.modules.coupon.service.CouponCustomerService;
import com.mall.modules.order.dao.OrderGoodsDao;
import com.mall.modules.order.dao.OrderInfoDao;
import com.mall.modules.order.dao.OrderReturnsDao;
import com.mall.modules.order.entity.OrderGoods;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.order.entity.OrderPaymentInfo;
import com.mall.modules.order.entity.OrderReturns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单售后申请Service
 *
 * @author wankang
 * @version 2018-11-15
 */
@Service
@Transactional(readOnly = true)
public class OrderReturnsService extends CrudService<OrderReturnsDao, OrderReturns> {

    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private OrderGoodsDao orderGoodsDao;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderPaymentInfoService orderPaymentInfoService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private OrderWeixinReturnResultService orderWeixinReturnResultService;
    @Autowired
    private CouponCustomerService couponCustomerService;

    public OrderReturns get(String id) {
        return super.get(id);
    }

    public List<OrderReturns> findList(OrderReturns orderReturns) {
        return super.findList(orderReturns);
    }

    public Page<OrderReturns> findPage(Page<OrderReturns> page, OrderReturns orderReturns) {
        return super.findPage(page, orderReturns);
    }

    @Transactional(readOnly = false)
    public void save(OrderReturns orderReturns) {
        super.save(orderReturns);
    }

    @Transactional(readOnly = false)
    public void delete(OrderReturns orderReturns) {
        super.delete(orderReturns);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void check(OrderReturns orderReturns) {
        if("4".equals(orderReturns.getStatus())) {
            OrderInfo orderInfo = orderInfoService.get(orderReturns.getOrderId());
            orderInfoService.orderCompleteReturns(orderInfo);
        }
        dao.check(orderReturns);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void handle(OrderReturns orderReturns) throws Exception {
        OrderInfo orderInfo = orderInfoService.get(orderReturns.getOrderId());
        if("0".equals(orderReturns.getHandlingWay())) {
            String paymentNo = orderInfo.getPaymentNo();
            OrderPaymentInfo queryCondition = new OrderPaymentInfo();
            queryCondition.setPaymentNo(paymentNo);
            OrderPaymentInfo orderPaymentInfo = orderPaymentInfoService.getByCondition(queryCondition);
            String payChannel = orderPaymentInfo.getPayChannel();
            if("0".equals(payChannel)) {
                WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
                wxPayRefundRequest.setOutTradeNo(paymentNo);
                wxPayRefundRequest.setOutRefundNo(orderReturns.getReturnsNo());
                wxPayRefundRequest.setTotalFee(BaseWxPayRequest.yuanToFen(String.valueOf(orderPaymentInfo.getAmountTotal())));
                wxPayRefundRequest.setRefundFee(BaseWxPayRequest.yuanToFen(String.valueOf(orderReturns.getReturnsAmount())));
                WxPayRefundResult wxPayRefundResult = wxPayService.refund(wxPayRefundRequest);
                orderWeixinReturnResultService.save(wxPayRefundResult);
                if(!"SUCCESS".equalsIgnoreCase(wxPayRefundResult.getReturnCode()) || !"SUCCESS".equalsIgnoreCase(wxPayRefundResult.getResultCode())) {
                    throw new WxPayException("微信退款失败：" + wxPayRefundResult.getReturnMsg());
                }
            }else if("3".equals(payChannel)) {
                // 余额支付退款
                accountService.createRefund(orderReturns.getCustomerCode(), orderReturns.getReturnsAmount(), orderInfo.getOrderNo());
            }
            // 退还优惠券
            if(StringUtils.isNotBlank(orderInfo.getCouponCode()) && orderInfo.getDiscountAmountTotal() > 0) {
                CouponCustomer couponCustomer = couponCustomerService.get(orderInfo.getCouponCode());
                couponCustomerService.saveCouponCustomerByPlatform(orderInfo.getDiscountAmountTotal(), couponCustomer.getCouponType(), orderInfo.getCustomerCode(), "退款返还", "7");
            }
        }
        orderInfoService.orderCompleteReturns(orderInfo);
        dao.handle(orderReturns);
        if("0".equals(orderReturns.getHandlingWay())) {
            this.complete(orderReturns);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void complete(OrderReturns orderReturns) {
        dao.complete(orderReturns);
    }

    public Page<OrderReturns> findOrderReturnList(Page<OrderReturns> page, OrderReturns orderReturns) {
        orderReturns.setPage(page);
        List<OrderReturns> orderReturnsList = dao.findList(orderReturns);
        for (OrderReturns o : orderReturnsList) {
            OrderInfo queryCondition = new OrderInfo();
            queryCondition.setId(o.getOrderId());
            OrderInfo orderInfo = orderInfoDao.findOrderDetailList(queryCondition).get(0);
            orderInfo.setOrderGoodsList(orderGoodsDao.findList(new OrderGoods(o.getOrderNo())));
            o.setOrderInfo(orderInfo);
        }
        return page.setList(orderReturnsList);
    }
}