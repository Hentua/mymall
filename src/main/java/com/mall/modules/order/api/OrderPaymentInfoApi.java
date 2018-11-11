package com.mall.modules.order.api;

import com.mall.common.service.ServiceException;
import com.mall.common.utils.ResultStatus;
import com.mall.common.utils.api.ApiExceptionHandleUtil;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.account.service.AccountService;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.order.entity.OrderPaymentInfo;
import com.mall.modules.order.service.OrderInfoService;
import com.mall.modules.order.service.OrderPaymentInfoService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
     * 支付成功回调接口
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "paySuccessCallback")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void successCallback(HttpServletRequest request, HttpServletResponse response) {
        // todo pay success callback function
        String paymentNo = request.getParameter("paymentNo");
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
            // 支付成功
            orderPaymentInfoService.normalOrderPaySuccess(orderPaymentInfo);
            renderString(response, ResultGenerator.genSuccessResult());
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
            if(StringUtils.isBlank(cipherPayPassword)) {
                renderString(response, ResultGenerator.genFailResult("未设置支付密码").setStatus(ResultStatus.NULL_PAY_PASSWORD));
                return;
            }
            if(!memberInfoService.validatePayPassword(payPassword, userId)) {
                throw new ServletException("支付密码不正确");
            }
            if (StringUtils.isBlank(paymentNo)) {
                throw new ServiceException("未选择支付信息");
            }
            OrderPaymentInfo orderPaymentInfo = orderPaymentInfoService.get(paymentNo);
            if (null == orderPaymentInfo) {
                throw new ServiceException("不合法的支付信息");
            }
            Double amountTotal = orderPaymentInfo.getAmountTotal();
            accountService.consumption(amountTotal, paymentNo, userId);
            // 支付成功
            orderPaymentInfoService.normalOrderPaySuccess(orderPaymentInfo);
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
                orderPaymentInfo.setDiscountAmount(orderInfo.getDiscountAmountTotal());
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
