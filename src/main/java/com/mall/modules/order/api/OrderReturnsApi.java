package com.mall.modules.order.api;

import com.mall.common.persistence.Page;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.api.ApiExceptionHandleUtil;
import com.mall.common.utils.api.ApiPageEntityHandleUtil;
import com.mall.common.web.BaseController;
import com.mall.modules.member.entity.MemberDeliveryAddress;
import com.mall.modules.member.service.MemberDeliveryAddressService;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.order.entity.OrderReturns;
import com.mall.modules.order.service.OrderInfoService;
import com.mall.modules.order.service.OrderReturnsService;
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
import java.util.Date;

/**
 * 订单售后API
 *
 * @author wankang
 * @date 2018-11-15
 */
@RestController
@RequestMapping(value = "${adminPath}/api")
public class OrderReturnsApi extends BaseController {

    private static IdWorker idWorker = new IdWorker();
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderReturnsService orderReturnsService;
    @Autowired
    private MemberDeliveryAddressService memberDeliveryAddressService;

    /**
     * 申请订单售后
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "submitOrderReturns", method = RequestMethod.POST)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void submitOrderReturns(HttpServletRequest request, HttpServletResponse response) {
        String orderId = request.getParameter("orderId");
        String returnType = "0";
        Date now = new Date();
        String status = "0";
        String reason = request.getParameter("reason");
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        String handlingWay = request.getParameter("handlingWay");
        String addressId = request.getParameter("addressId");
        String consigneeAddress = "";
        String consigneeName = "";
        String consigneeTelphone = "";
        String consigneeZip = "";
        try {
            if (StringUtils.isBlank(orderId)) {
                throw new ServiceException("未选择要申请售后的订单");
            }
            if (StringUtils.isBlank(handlingWay)) {
                throw new ServiceException("未选择要申请的服务类型");
            }
            if ("1".equals(handlingWay) && StringUtils.isBlank(addressId)) {
                throw new ServiceException("未选择收货地址");
            } else if ("1".equals(handlingWay)) {
                MemberDeliveryAddress memberDeliveryAddress = memberDeliveryAddressService.get(addressId);
                if (null == memberDeliveryAddress) {
                    throw new ServiceException("所选地址不合法");
                }
                consigneeAddress = memberDeliveryAddress.getProvinceName() + memberDeliveryAddress.getCityName() + memberDeliveryAddress.getAreaName() + memberDeliveryAddress.getDetailAddress();
                consigneeName = memberDeliveryAddress.getRealname();
                consigneeTelphone = memberDeliveryAddress.getTelphone();
                consigneeZip = memberDeliveryAddress.getZip();
            }
            OrderInfo queryCondition = new OrderInfo();
            queryCondition.setId(orderId);
            queryCondition.setCustomerCode(customerCode);
            OrderInfo orderInfo = orderInfoService.getOrderBasicInfo(queryCondition);
            if (null == orderInfo) {
                throw new ServiceException("订单信息不合法");
            }
            if (!"3".equals(orderInfo.getOrderStatus()) || !"0".equals(orderInfo.getSetFlag())) {
                throw new ServiceException("当前订单不可申请售后，如有需要请联系客服");
            }
            if (StringUtils.isBlank(reason)) {
                throw new ServiceException("请填写售后原因");
            } else if (reason.length() > 500) {
                throw new ServiceException("您填写的售后原因过长，最大只支持500字");
            }
            String returnsNo = String.valueOf(idWorker.getId());
            OrderReturns orderReturns = new OrderReturns();
            orderReturns.setReturnsNo(returnsNo);
            orderReturns.setOrderNo(orderInfo.getOrderNo());
            orderReturns.setReturnsType(returnType);
            if ("0".equals(handlingWay)) {
                orderReturns.setReturnsAmount(orderInfo.getOrderAmountTotal());
            } else if ("1".equals(handlingWay)) {
                orderReturns.setReturnsAmount(0.00);
            } else {
                throw new ServiceException("所选服务类型不合法");
            }
            orderReturns.setHandlingWay(handlingWay);
            orderReturns.setReturnSubmitTime(now);
            orderReturns.setStatus(status);
            orderReturns.setReason(reason);
            orderReturns.setOrderId(orderId);
            orderReturns.setCustomerCode(customerCode);
            orderReturns.setConsigneeZip(consigneeZip);
            orderReturns.setConsigneeRealname(consigneeName);
            orderReturns.setConsigneeTelphone(consigneeTelphone);
            orderReturns.setConsigneeAddress(consigneeAddress);
            orderReturnsService.save(orderReturns);
            orderInfoService.orderSubmitReturns(queryCondition);
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 获取用户售后记录列表
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "orderReturnsList", method = RequestMethod.POST)
    public void orderReturnsList(HttpServletRequest request, HttpServletResponse response) {
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            Page<OrderReturns> page = ApiPageEntityHandleUtil.packagePage(new Page<OrderReturns>(), pageNo, pageSize);
            OrderReturns queryCondition = new OrderReturns();
            queryCondition.setCustomerCode(customerCode);
            renderString(response, ResultGenerator.genSuccessResult(orderReturnsService.findOrderReturnList(page, queryCondition).getList()));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 售后完成 确认收货
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "orderReturnsComplete", method = RequestMethod.POST)
    public void orderReturnsComplete(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        try {
            if (StringUtils.isBlank(id)) {
                throw new ServiceException("未选择确认收货的售后单");
            }
            OrderReturns orderReturns = orderReturnsService.get(id);
            if (null == orderReturns) {
                throw new ServiceException("未查询到售后记录");
            }
            if (!"2".equals(orderReturns.getStatus())) {
                throw new ServiceException("该售后单不需要确认");
            }
            if (!"1".equals(orderReturns.getHandlingWay())) {
                throw new ServiceException("该售后单不可执行此操作");
            }
            orderReturnsService.complete(orderReturns);
            // todo 订单清算
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

}
