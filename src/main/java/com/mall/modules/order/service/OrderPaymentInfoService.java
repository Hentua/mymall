package com.mall.modules.order.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.order.dao.OrderPaymentInfoDao;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.order.entity.OrderPaymentInfo;
import com.sohu.idcenter.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 支付信息Service
 * @author wankang
 * @version 2018-10-19
 */
@Service
@Transactional(readOnly = true)
public class OrderPaymentInfoService extends CrudService<OrderPaymentInfoDao, OrderPaymentInfo> {

	private static IdWorker idWorker = new IdWorker();
	@Autowired
	private OrderPaymentInfoDao orderPaymentInfoDao;
	@Autowired
	private OrderInfoService orderInfoService;

	public OrderPaymentInfo get(String id) {
		return super.get(id);
	}

	public OrderPaymentInfo getByCondition(OrderPaymentInfo orderPaymentInfo) {
		return orderPaymentInfoDao.getByCondition(orderPaymentInfo);
	}
	
	public List<OrderPaymentInfo> findList(OrderPaymentInfo orderPaymentInfo) {
		return super.findList(orderPaymentInfo);
	}
	
	public Page<OrderPaymentInfo> findPage(Page<OrderPaymentInfo> page, OrderPaymentInfo orderPaymentInfo) {
		return super.findPage(page, orderPaymentInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderPaymentInfo orderPaymentInfo) {
		super.save(orderPaymentInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderPaymentInfo orderPaymentInfo) {
		super.delete(orderPaymentInfo);
	}

	public static OrderPaymentInfo genDefaultPaymentInfo(String orderType) {
		String paymentNo = String.valueOf(idWorker.getId());
		OrderPaymentInfo orderPaymentInfo = new OrderPaymentInfo();
		orderPaymentInfo.setPaymentNo(paymentNo);
		if("0".equals(orderType)) {
			orderPaymentInfo.setPaymentStatus("0");
		}else if("1".equals(orderType)) {
			orderPaymentInfo.setPaymentStatus("1");
		}
		orderPaymentInfo.setPaymentType(orderType);
		return orderPaymentInfo;
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public OrderPaymentInfo genAmountPaymentInfo(String payChannel, String paymentType, Double amountTotal, Double discountAmount) {
		OrderPaymentInfo orderPaymentInfo = genDefaultPaymentInfo(payChannel);
		orderPaymentInfo.setAmountTotal(amountTotal);
		orderPaymentInfo.setDiscountAmount(discountAmount);
		orderPaymentInfo.setPaymentType(paymentType);
		this.save(orderPaymentInfo);
		return orderPaymentInfo;
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void modifyPaymentInfoStatus(OrderPaymentInfo orderPaymentInfo) {
		orderPaymentInfoDao.modifyPaymentInfoStatus(orderPaymentInfo);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void normalOrderPaySuccess(OrderPaymentInfo orderPaymentInfo, Date payChannelTime) {
		String paymentNo = orderPaymentInfo.getPaymentNo();

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setPaymentNo(paymentNo);
		orderInfo.setPayChannelTime(payChannelTime);
		// 修改订单状态
		orderInfoService.paySuccessModifyOrderStatus(orderInfo);

		// 修改支付单状态
		orderPaymentInfo.setPaymentStatus("1");
		this.modifyPaymentInfoStatus(orderPaymentInfo);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void weixinPayResult(OrderPaymentInfo orderPaymentInfo) {
		orderPaymentInfoDao.weixinPayResult(orderPaymentInfo);
	}

}