package com.mall.modules.order.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.order.dao.OrderPaymentInfoDao;
import com.mall.modules.order.entity.OrderPaymentInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 支付信息Service
 * @author wankang
 * @version 2018-10-19
 */
@Service
@Transactional(readOnly = true)
public class OrderPaymentInfoService extends CrudService<OrderPaymentInfoDao, OrderPaymentInfo> {

	public OrderPaymentInfo get(String id) {
		return super.get(id);
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
	
}