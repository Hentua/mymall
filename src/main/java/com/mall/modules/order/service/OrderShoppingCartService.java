package com.mall.modules.order.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.order.dao.OrderShoppingCartDao;
import com.mall.modules.order.entity.OrderShoppingCart;
import com.mall.modules.order.entity.OrderShoppingCartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 购物车Service
 * @author wankang
 * @version 2018-10-16
 */
@Service
@Transactional(readOnly = true)
public class OrderShoppingCartService extends CrudService<OrderShoppingCartDao, OrderShoppingCart> {

	@Autowired
	private OrderShoppingCartDao orderShoppingCartDao;

	public OrderShoppingCart get(String id) {
		return super.get(id);
	}

	public OrderShoppingCart getByCondition(OrderShoppingCart condition) {
		return orderShoppingCartDao.getByCondition(condition);
	}

	public List<OrderShoppingCart> findList(OrderShoppingCart orderShoppingCart) {
		return super.findList(orderShoppingCart);
	}
	
	public Page<OrderShoppingCart> findPage(Page<OrderShoppingCart> page, OrderShoppingCart orderShoppingCart) {
		return super.findPage(page, orderShoppingCart);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderShoppingCart orderShoppingCart) {
		super.save(orderShoppingCart);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderShoppingCart orderShoppingCart) {
		super.delete(orderShoppingCart);
	}

	public List<OrderShoppingCartVo> findShoppingCartDetailList(String customerCode) {
		return orderShoppingCartDao.findShoppingCartDetailList(customerCode);
	}
	
}