package com.mall.modules.order.web;

import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayNativeOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import com.mall.common.config.Global;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.IpUtil;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.order.entity.OrderPaymentInfo;
import com.mall.modules.order.service.OrderPaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * 支付Controller
 *
 * @author wank
 * @date 2018-11-25
 */
@Controller
@RequestMapping("${adminPath}/payment/")
public class OrderPaymentController extends BaseController {

    @Autowired
    private OrderPaymentInfoService orderPaymentInfoService;
    @Autowired
    private WxPayService wxPayService;

    @RequestMapping(value = "weixinPay")
    public String wexinPay(OrderPaymentInfo orderPaymentInfo, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws Exception {
        Date now = new Date();
        String paymentNo = orderPaymentInfo.getPaymentNo();
        String callbackUrl = request.getParameter("callbackUrl");

        if (StringUtils.isBlank(paymentNo)) {
            throw new ServiceException("支付单号不能为空");
        }
        OrderPaymentInfo queryCondition = new OrderPaymentInfo();
        queryCondition.setPaymentNo(paymentNo);
        orderPaymentInfo = orderPaymentInfoService.getByCondition(queryCondition);
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
        orderRequest.setTradeType(WxPayConstants.TradeType.NATIVE);
        orderRequest.setProductId(paymentNo);
        WxPayNativeOrderResult result = wxPayService.createOrder(orderRequest);
        orderPaymentInfo.setPayChannel("0");
        orderPaymentInfo.setCodeUrl(result.getCodeUrl());
        orderPaymentInfoService.modifyPaymentInfoStatus(orderPaymentInfo);
        model.addAttribute("orderPaymentInfo", orderPaymentInfo);
        model.addAttribute("callbackUrl", callbackUrl);
        return "modules/order/wechatPay";
    }

    @RequestMapping(value = "balancePayForm")
    public String balancePayForm(OrderPaymentInfo orderPaymentInfo, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws Exception {
        Date now = new Date();
        String paymentNo = orderPaymentInfo.getPaymentNo();
        String callbackUrl = request.getParameter("callbackUrl");

        if (StringUtils.isBlank(paymentNo)) {
            throw new ServiceException("支付单号不能为空");
        }
        OrderPaymentInfo queryCondition = new OrderPaymentInfo();
        queryCondition.setPaymentNo(paymentNo);
        orderPaymentInfo = orderPaymentInfoService.getByCondition(queryCondition);
        if (null == orderPaymentInfo) {
            throw new ServiceException("获取支付信息失败");
        }
        if (!"0".equals(orderPaymentInfo.getPaymentStatus())) {
            throw new ServiceException("支付信息不合法，订单已支付或关闭");
        }
        orderPaymentInfo.setPayChannel("3");
        orderPaymentInfoService.modifyPaymentInfoStatus(orderPaymentInfo);
        model.addAttribute("orderPaymentInfo", orderPaymentInfo);
        model.addAttribute("callbackUrl", callbackUrl);
        return "modules/order/balancePay";
    }

    @RequestMapping(value = "genQRCode")
    public void genQRCode(HttpServletRequest request, HttpServletResponse response) {
        String codeUrl = request.getParameter("codeUrl");
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            byte[] codeByte = wxPayService.createScanPayQrcodeMode2(codeUrl, null, null);
            outputStream.write(codeByte);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "queryPaymentInfo")
    public void queryPaymentInfo(HttpServletRequest request, HttpServletResponse response) {
        String paymentId = request.getParameter("id");
        renderString(response, orderPaymentInfoService.get(paymentId));
    }
}
