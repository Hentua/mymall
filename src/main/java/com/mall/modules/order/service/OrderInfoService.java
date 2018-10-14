package com.mall.modules.order.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.utils.StringUtils;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.order.dao.OrderGoodsDao;
import com.mall.modules.order.dao.OrderInfoDao;
import com.mall.modules.order.dao.OrderLogisticsDao;
import com.mall.modules.order.entity.OrderGoods;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.order.entity.OrderLogistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单信息Service
 * @author wankang
 * @version 2018-10-12
 */
@Service
@Transactional(readOnly = true)
public class OrderInfoService extends CrudService<OrderInfoDao, OrderInfo> {

	@Autowired
	private OrderGoodsDao orderGoodsDao;
	@Autowired
	private OrderLogisticsDao orderLogisticsDao;
	
	public OrderInfo get(String id) {
		OrderInfo orderInfo = super.get(id);
		orderInfo.setOrderGoodsList(orderGoodsDao.findList(new OrderGoods(orderInfo)));
		orderInfo.setOrderLogisticsList(orderLogisticsDao.findList(new OrderLogistics(orderInfo)));
		return orderInfo;
	}
	
	public List<OrderInfo> findList(OrderInfo orderInfo) {
		return super.findList(orderInfo);
	}
	
	public Page<OrderInfo> findPage(Page<OrderInfo> page, OrderInfo orderInfo) {
		return super.findPage(page, orderInfo);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void save(OrderInfo orderInfo) {
		super.save(orderInfo);
		for (OrderGoods orderGoods : orderInfo.getOrderGoodsList()){
			if (orderGoods.getId() == null){
				continue;
			}
			if (OrderGoods.DEL_FLAG_NORMAL.equals(orderGoods.getDelFlag())){
				if (StringUtils.isBlank(orderGoods.getId())){
					orderGoods.setOrderNo(orderInfo);
					orderGoods.preInsert();
					orderGoodsDao.insert(orderGoods);
				}else{
					orderGoods.preUpdate();
					orderGoodsDao.update(orderGoods);
				}
			}else{
				orderGoodsDao.delete(orderGoods);
			}
		}
		for (OrderLogistics orderLogistics : orderInfo.getOrderLogisticsList()){
			if (orderLogistics.getId() == null){
				continue;
			}
			if (OrderLogistics.DEL_FLAG_NORMAL.equals(orderLogistics.getDelFlag())){
				if (StringUtils.isBlank(orderLogistics.getId())){
					orderLogistics.setOrderNo(orderInfo);
					orderLogistics.preInsert();
					orderLogisticsDao.insert(orderLogistics);
				}else{
					orderLogistics.preUpdate();
					orderLogisticsDao.update(orderLogistics);
				}
			}else{
				orderLogisticsDao.delete(orderLogistics);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderInfo orderInfo) {
		super.delete(orderInfo);
		orderGoodsDao.delete(new OrderGoods(orderInfo));
		orderLogisticsDao.delete(new OrderLogistics(orderInfo));
	}

	/**
	 * 根据商品实体信息组装订单商品信息
	 * @param goodsInfo 商品实体
	 * @return 订单实体
	 */
	public OrderGoods genOrderGoods(GoodsInfo goodsInfo) {
		OrderGoods orderGoods = new OrderGoods();
		orderGoods.setGoodsBarcode(goodsInfo.getGoodsBarcode());
		orderGoods.setGoodsId(goodsInfo.getId());
		orderGoods.setGoodsCategoryId(goodsInfo.getGoodsCategoryId());
		orderGoods.setGoodsTitle(goodsInfo.getGoodsTitle());
		orderGoods.setGoodsType(goodsInfo.getGoodsType());
		orderGoods.setUnit(goodsInfo.getUnit());
		orderGoods.setImage(goodsInfo.getImage());
		orderGoods.setGoodsDesp(goodsInfo.getGoodsDesp());
		orderGoods.setGoodsPrice(goodsInfo.getGoodsPrice());
		orderGoods.setGoodsName(goodsInfo.getGoodsName());
		return orderGoods;
	}
	
}