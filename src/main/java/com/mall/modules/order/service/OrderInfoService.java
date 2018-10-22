package com.mall.modules.order.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.utils.StringUtils;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.member.entity.MemberDeliveryAddress;
import com.mall.modules.member.entity.MemberLogisticsFee;
import com.mall.modules.member.service.MemberLogisticsFeeService;
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
import java.util.Map;

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
	@Autowired
	private MemberLogisticsFeeService memberLogisticsFeeService;
	@Autowired
	private OrderInfoDao orderInfoDao;
	
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

	/**
	 * 根据用户选择地址组装订单物流信息
	 *
	 * @param orderNo 订单号
	 * @param merchantCode 商户编号
	 * @param memberDeliveryAddress 地址实体信息
	 * @return 物流实体信息
	 */
	public OrderLogistics genOrderLogistics(String orderNo, String merchantCode, MemberDeliveryAddress memberDeliveryAddress) {
		String province = memberDeliveryAddress.getProvince();
		OrderLogistics orderLogistics = new OrderLogistics();
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderNo(orderNo);
		orderLogistics.setOrderNo(orderInfo);
		MemberLogisticsFee condition = new MemberLogisticsFee();
		condition.setMerchantCode(merchantCode);
		condition.setProvince(province);
		List<MemberLogisticsFee> memberLogisticsFees = memberLogisticsFeeService.findList(condition);
		Double logisticsFee;
		if(memberLogisticsFees.size() > 0) {
			MemberLogisticsFee memberLogisticsFee = memberLogisticsFees.get(0);
			logisticsFee = memberLogisticsFee.getLogisticsFee();
		}else {
			logisticsFee = 0.00;
		}
		orderLogistics.setLogisticsFee(logisticsFee);
		orderLogistics.setConsigneeAddress(memberDeliveryAddress.getProvinceName() + memberDeliveryAddress.getCityName() + memberDeliveryAddress.getAreaName() + memberDeliveryAddress.getDetailAddress());
		orderLogistics.setConsigneeTelphone(memberDeliveryAddress.getTelphone());
		orderLogistics.setConsigneeTelphoneBackup(memberDeliveryAddress.getTelphoneBak());
		orderLogistics.setConsigneeRealname(memberDeliveryAddress.getRealname());
		orderLogistics.setConsigneeZip(memberDeliveryAddress.getZip());
		return orderLogistics;
	}

	/**
	 * 统计订单状态数量
	 *
	 * @param customerCode 用户编号
	 * @return 订单状态数量
	 */
	public Map<String, String> orderCount(String customerCode) {
		return orderInfoDao.orderCount(customerCode);
	}
	
}