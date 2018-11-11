package com.mall.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.order.entity.OrderSettlement;
import com.mall.modules.order.dao.OrderSettlementDao;

/**
 * 订单结算Service
 * @author hub
 * @version 2018-11-11
 */
@Service
@Transactional(readOnly = true)
public class OrderSettlementService extends CrudService<OrderSettlementDao, OrderSettlement> {

	public OrderSettlement get(String id) {
		return super.get(id);
	}
	
	public List<OrderSettlement> findList(OrderSettlement orderSettlement) {
		return super.findList(orderSettlement);
	}
	
	public Page<OrderSettlement> findPage(Page<OrderSettlement> page, OrderSettlement orderSettlement) {
		return super.findPage(page, orderSettlement);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderSettlement orderSettlement) {
		super.save(orderSettlement);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderSettlement orderSettlement) {
		super.delete(orderSettlement);
	}
	
}