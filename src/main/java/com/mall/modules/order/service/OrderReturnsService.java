package com.mall.modules.order.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.order.dao.OrderReturnsDao;
import com.mall.modules.order.entity.OrderReturns;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单售后申请Service
 * @author wankang
 * @version 2018-11-15
 */
@Service
@Transactional(readOnly = true)
public class OrderReturnsService extends CrudService<OrderReturnsDao, OrderReturns> {

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
	
}