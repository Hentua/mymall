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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 订单信息Service
 *
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

    @Override
    public OrderInfo get(String id) {
        OrderInfo orderInfo = super.get(id);
        orderInfo.setOrderGoodsList(orderGoodsDao.findList(new OrderGoods(orderInfo.getOrderNo())));
        orderInfo.setOrderLogistics(orderLogisticsDao.get(new OrderLogistics(orderInfo.getOrderNo())));
        return orderInfo;
    }

    public OrderInfo getOrderBasicInfo(OrderInfo orderInfo) {
        return orderInfoDao.getOrderBasicInfo(orderInfo);
    }

    @Override
    public List<OrderInfo> findList(OrderInfo orderInfo) {
        return super.findList(orderInfo);
    }

    @Override
    public Page<OrderInfo> findPage(Page<OrderInfo> page, OrderInfo orderInfo) {
        return super.findPage(page, orderInfo);
    }

    public Page<OrderInfo> findOrderDetailList(Page<OrderInfo> page, OrderInfo orderInfo) {
        orderInfo.setPage(page);
        List<OrderInfo> orderInfos = orderInfoDao.findOrderDetailList(orderInfo);
        for (OrderInfo o : orderInfos) {
            o.setOrderGoodsList(orderGoodsDao.findList(new OrderGoods(o.getOrderNo())));
        }
        return page.setList(orderInfos);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void save(OrderInfo orderInfo) {
        super.save(orderInfo);
        for (OrderGoods orderGoods : orderInfo.getOrderGoodsList()) {
            if (orderGoods.getId() == null) {
                continue;
            }
            if (OrderGoods.DEL_FLAG_NORMAL.equals(orderGoods.getDelFlag())) {
                if (StringUtils.isBlank(orderGoods.getId())) {
                    orderGoods.setOrderNo(orderInfo.getOrderNo());
                    orderGoods.preInsert();
                    orderGoodsDao.insert(orderGoods);
                } else {
                    orderGoods.preUpdate();
                    orderGoodsDao.update(orderGoods);
                }
            } else {
                orderGoodsDao.delete(orderGoods);
            }
        }
        OrderLogistics orderLogistics = orderInfo.getOrderLogistics();
        if (orderLogistics.getId() != null) {
            if (OrderLogistics.DEL_FLAG_NORMAL.equals(orderLogistics.getDelFlag())) {
                if (StringUtils.isBlank(orderLogistics.getId())) {
                    orderLogistics.setOrderNo(orderInfo.getOrderNo());
                    orderLogistics.preInsert();
                    orderLogisticsDao.insert(orderLogistics);
                } else {
                    orderLogistics.preUpdate();
                    orderLogisticsDao.update(orderLogistics);
                }
            } else {
                orderLogisticsDao.delete(orderLogistics);
            }
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(OrderInfo orderInfo) {
        super.delete(orderInfo);
        orderGoodsDao.delete(new OrderGoods(orderInfo.getOrderNo()));
        orderLogisticsDao.delete(new OrderLogistics(orderInfo.getOrderNo()));
    }

    /**
     * 根据商品实体信息组装订单商品信息
     *
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
     * @param orderNo               订单号
     * @param merchantCode          商户编号
     * @param memberDeliveryAddress 地址实体信息
     * @return 物流实体信息
     */
    public OrderLogistics genOrderLogistics(String orderNo, String merchantCode, MemberDeliveryAddress memberDeliveryAddress) {
        String province = memberDeliveryAddress.getProvince();
        OrderLogistics orderLogistics = new OrderLogistics();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(orderNo);
        orderLogistics.setOrderNo(orderNo);
        MemberLogisticsFee condition = new MemberLogisticsFee();
        condition.setMerchantCode(merchantCode);
        condition.setProvince(province);
        List<MemberLogisticsFee> memberLogisticsFees = memberLogisticsFeeService.findList(condition);
        Double logisticsFee;
        if (memberLogisticsFees.size() > 0) {
            MemberLogisticsFee memberLogisticsFee = memberLogisticsFees.get(0);
            logisticsFee = memberLogisticsFee.getLogisticsFee();
        } else {
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

    /**
     * 删除用户订单数据 逻辑删除
     *
     * @param orderInfo 条件
     * @return 删除条目数
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int deleteByUser(OrderInfo orderInfo) {
        return orderInfoDao.deleteByUser(orderInfo);
    }

    /**
     * 支付成功后修改订单状态 条件为支付单号
     *
     * @param paymentNo 支付单号
     * @return 修改条目数
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int paySuccessModifyOrderStatus(String paymentNo) {
        return orderInfoDao.paySuccessModifyOrderStatus(paymentNo);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int orderCancel(OrderInfo orderInfo) {
        return orderInfoDao.orderCancel(orderInfo);
    }

}