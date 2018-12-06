package com.mall.modules.order.api;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import com.mall.common.config.Global;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.*;
import com.mall.common.utils.api.ApiExceptionHandleUtil;
import com.mall.common.web.BaseController;
import com.mall.modules.account.service.AccountService;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.order.entity.OrderPaymentInfo;
import com.mall.modules.order.service.OrderInfoService;
import com.mall.modules.order.service.OrderPaymentInfoService;
import com.mall.modules.order.service.OrderPaymentWeixinCallbackService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

/**
 * 订单支付API
 *
 * @author wankang
 * @since 2018-10-23
 */
@RestController
@RequestMapping(value = "${adminPath}/api")
public class OrderPaymentInfoApi extends BaseController {

    @Autowired
    private OrderPaymentInfoService orderPaymentInfoService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private OrderPaymentWeixinCallbackService orderPaymentWeixinCallbackService;

    /**
     * 支付成功回调接口
     */
    @RequestMapping(value = "paySuccessCallback")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String successCallback(@RequestBody String xmlData) {
        try {
            final WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
            // 保存回调信息
            orderPaymentWeixinCallbackService.save(notifyResult);
            String paymentNo = notifyResult.getOutTradeNo();
            if (StringUtils.isBlank(paymentNo)) {
                throw new ServiceException("支付单号不能为空");
            }
            OrderPaymentInfo queryCondition = new OrderPaymentInfo();
            queryCondition.setPaymentNo(paymentNo);
            OrderPaymentInfo orderPaymentInfo = orderPaymentInfoService.getByCondition(queryCondition);
            if (null == orderPaymentInfo) {
                throw new ServiceException("获取支付信息失败");
            } else if (!"0".equals(orderPaymentInfo.getPaymentStatus())) {
//                wxPayService.closeOrder(paymentNo);
                return WxPayNotifyResponse.success("成功");
            }
            int totalAmount = notifyResult.getTotalFee();
            if (!String.valueOf(totalAmount).equals(String.valueOf(BaseWxPayRequest.yuanToFen(String.valueOf(orderPaymentInfo.getAmountTotal()))))) {
                orderPaymentInfo.setPaymentStatus("2");
            }
            if (!"SUCCESS".equalsIgnoreCase(notifyResult.getReturnCode()) || !"SUCCESS".equalsIgnoreCase(notifyResult.getResultCode())) {
                orderPaymentInfo.setPaymentStatus("2");
            } else {
                orderPaymentInfo.setPaymentStatus("1");
            }
            orderPaymentInfo.setDeviceInfo(notifyResult.getDeviceInfo());
            orderPaymentInfo.setReturnCode(notifyResult.getReturnCode());
            orderPaymentInfo.setReturnMsg(notifyResult.getReturnMsg());
            orderPaymentInfo.setErrCode(notifyResult.getErrCode());
            orderPaymentInfo.setErrCodeDes(notifyResult.getErrCodeDes());
            orderPaymentInfo.setTradeType(notifyResult.getTradeType());
            orderPaymentInfo.setResultCode(notifyResult.getResultCode());
            orderPaymentInfoService.weixinPayResult(orderPaymentInfo);
            // 支付成功
            if ("1".equalsIgnoreCase(orderPaymentInfo.getPaymentStatus())) {
                String paymentType = orderPaymentInfo.getPaymentType();
                // 普通订单
                if ("0".equalsIgnoreCase(paymentType)) {
                    orderPaymentInfoService.normalOrderPaySuccess(orderPaymentInfo, DateUtils.parseDate(notifyResult.getTimeEnd(), "yyyyMMddHHmmss"));
                }
                // 充值订单
                else if ("2".equalsIgnoreCase(paymentType)) {
                    orderPaymentInfoService.rechargeOrderPaySuccess(orderPaymentInfo);
                }
                // 礼包购买
                else if ("1".equalsIgnoreCase(paymentType)) {
                    orderPaymentInfoService.giftOrderPaySuccess(orderPaymentInfo);
                }
            } else {
                wxPayService.closeOrder(paymentNo);
            }
            return WxPayNotifyResponse.success("成功");
        } catch (Exception e) {
            e.printStackTrace();
            return WxPayNotifyResponse.fail("失败");
        }
    }

    /**
     * APP微信统一下单
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "wxPayCreateOrder", method = RequestMethod.POST)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void wxPayCreateOrder(HttpServletRequest request, HttpServletResponse response) {
        String paymentNo = request.getParameter("paymentNo");
        Date now = new Date();
        try {
            if (StringUtils.isBlank(paymentNo)) {
                throw new ServiceException("支付单号不能为空");
            }
            OrderPaymentInfo queryCondition = new OrderPaymentInfo();
            queryCondition.setPaymentNo(paymentNo);
            OrderPaymentInfo orderPaymentInfo = orderPaymentInfoService.getByCondition(queryCondition);
            if (null == orderPaymentInfo) {
                throw new ServiceException("获取支付信息失败");
            }
            if (!"0".equals(orderPaymentInfo.getPaymentStatus())) {
                throw new ServiceException("支付信息不合法，订单已支付或关闭");
            }
            // 调用微信统一下单
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setBody("美易优选-支付单号" + paymentNo);
            orderRequest.setOutTradeNo(paymentNo);
            orderRequest.setTotalFee(BaseWxPayRequest.yuanToFen(String.valueOf(orderPaymentInfo.getAmountTotal())));
            orderRequest.setSpbillCreateIp(IpUtil.getIpAddress(request));
            orderRequest.setTimeStart(DateUtils.formatDate(now, "yyyyMMddHHmmss"));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.MINUTE, 20);
            orderRequest.setTimeExpire(DateUtils.formatDate(calendar.getTime(), "yyyyMMddHHmmss"));
            orderRequest.setNotifyUrl(Global.getConfig("server.baseUrl") + Global.getConfig("adminPath") + "/api/paySuccessCallback");
            orderRequest.setTradeType(WxPayConstants.TradeType.APP);
            if (wxPayService.getConfig().isUseSandboxEnv()) {
                orderRequest.setSignType(WxPayConstants.SignType.MD5);
                orderRequest.setSign(wxPayService.getSandboxSignKey());
            }
            WxPayAppOrderResult result = wxPayService.createOrder(orderRequest);
            orderPaymentInfo.setPayChannel("0");
            orderPaymentInfo.setPrepayId(result.getPrepayId());
            orderPaymentInfoService.modifyPaymentInfoStatus(orderPaymentInfo);
            renderString(response, ResultGenerator.genSuccessResult(result));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 余额支付
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "balancePay", method = RequestMethod.POST)
    public void balancePay(HttpServletRequest request, HttpServletResponse response) {
        String paymentNo = request.getParameter("paymentNo");
        String payPassword = request.getParameter("payPassword");
        User currUser = UserUtils.getUser();
        String userId = currUser.getId();
        try {
            String cipherPayPassword = memberInfoService.getPayPassword(userId);
            if (StringUtils.isBlank(cipherPayPassword)) {
                renderString(response, ResultGenerator.genFailResult("未设置支付密码").setStatus(ResultStatus.NULL_PAY_PASSWORD));
                return;
            }
            if (!memberInfoService.validatePayPassword(payPassword, userId)) {
                throw new ServiceException("支付密码不正确");
            }
            if (StringUtils.isBlank(paymentNo)) {
                throw new ServiceException("未选择支付信息");
            }
            OrderPaymentInfo queryCondition = new OrderPaymentInfo();
            queryCondition.setPaymentNo(paymentNo);
            OrderPaymentInfo orderPaymentInfo = orderPaymentInfoService.getByCondition(queryCondition);
            if (null == orderPaymentInfo || !"0".equals(orderPaymentInfo.getPaymentStatus())) {
                throw new ServiceException("订单不存在或已支付");
            }
            Double amountTotal = orderPaymentInfo.getAmountTotal();
            accountService.consumption(amountTotal, paymentNo, userId, orderPaymentInfo.getPaymentType());
            orderPaymentInfo.setPayChannel("3");
            // 支付成功
            String paymentType = orderPaymentInfo.getPaymentType();
            // 普通订单
            if ("0".equalsIgnoreCase(paymentType)) {
                orderPaymentInfoService.normalOrderPaySuccess(orderPaymentInfo, new Date());
            }
            // 礼包购买
            else if ("1".equalsIgnoreCase(paymentType)) {
                orderPaymentInfoService.giftOrderPaySuccess(orderPaymentInfo);
            }
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 根据订单生成新的账单
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "genPaymentInfo", method = RequestMethod.POST)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void genPaymentInfo(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        String orderId = request.getParameter("orderId");
        try {
            // 数据验证
            if (StringUtils.isBlank(orderId)) {
                throw new ServiceException("未选择要付款的订单");
            }
            OrderInfo condition = new OrderInfo();
            condition.setId(orderId);
            condition.setCustomerCode(customerCode);
            OrderInfo orderInfo = orderInfoService.getOrderBasicInfo(condition);
            if (null == orderInfo) {
                throw new ServiceException("订单不存在");
            }
            if (!"0".equals(orderInfo.getOrderStatus())) {
                throw new ServiceException("订单支付信息已失效");
            }
            String oldPaymentNo = orderInfo.getPaymentNo();
            OrderPaymentInfo paymentCondition = new OrderPaymentInfo();
            paymentCondition.setPaymentNo(oldPaymentNo);
            OrderPaymentInfo oldPaymentInfo = orderPaymentInfoService.getByCondition(paymentCondition);
            if (null != oldPaymentInfo && "1".equals(oldPaymentInfo.getPaymentStatus())) {
                throw new ServiceException("订单已支付");
            }

            // 生成新的支付信息并保存
            String orderType = orderInfo.getOrderType();
            OrderPaymentInfo orderPaymentInfo = OrderPaymentInfoService.genDefaultPaymentInfo(orderType);
            if ("0".equals(orderType)) {
                orderPaymentInfo.setAmountTotal(orderInfo.getOrderAmountTotal());
                orderPaymentInfo.setDiscountAmount(orderInfo.getDiscountAmountTotal() + orderInfo.getActivityDiscountAmount());
            } else if ("1".equals(orderType)) {
                orderPaymentInfo.setAmountTotal(0.00);
                orderPaymentInfo.setDiscountAmount(0.00);
            }
            orderPaymentInfoService.save(orderPaymentInfo);

            // 修改订单支付信息
            orderInfo.setPaymentNo(orderPaymentInfo.getPaymentNo());
            orderInfoService.save(orderInfo);

            // 返回新的订单支付信息
            renderString(response, ResultGenerator.genSuccessResult(orderPaymentInfo));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }
}
