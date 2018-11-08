package com.mall.modules.gift.service;

import java.text.DecimalFormat;
import java.util.List;

import com.mall.modules.gift.entity.GiftPurchaseLog;
import com.mall.modules.order.entity.OrderPaymentInfo;
import com.mall.modules.order.service.OrderPaymentInfoService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.gift.entity.GiftConfigCategory;
import com.mall.modules.gift.dao.GiftConfigCategoryDao;

/**
 * 礼包类别Service
 * @author wankang
 * @version 2018-11-06
 */
@Service
@Transactional(readOnly = true)
public class GiftConfigCategoryService extends CrudService<GiftConfigCategoryDao, GiftConfigCategory> {

	@Autowired
	private OrderPaymentInfoService orderPaymentInfoService;
	@Autowired
	private GiftPurchaseLogService giftPurchaseLogService;

	private static DecimalFormat df = new DecimalFormat("#.00");

	public GiftConfigCategory get(String id) {
		return super.get(id);
	}
	
	public List<GiftConfigCategory> findList(GiftConfigCategory giftConfigCategory) {
		return super.findList(giftConfigCategory);
	}
	
	public Page<GiftConfigCategory> findPage(Page<GiftConfigCategory> page, GiftConfigCategory giftConfigCategory) {
		return super.findPage(page, giftConfigCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(GiftConfigCategory giftConfigCategory) {
		super.save(giftConfigCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(GiftConfigCategory giftConfigCategory) {
		super.delete(giftConfigCategory);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public GiftPurchaseLog giftPurchase(GiftConfigCategory giftConfigCategory) {
		GiftPurchaseLog giftPurchaseLog = this.genPurchase(giftConfigCategory);
		giftPurchaseLogService.save(giftPurchaseLog);
		return giftPurchaseLog;
	}

	private GiftPurchaseLog genPurchase(GiftConfigCategory giftConfigCategory) {
		User currUser = UserUtils.getUser();
		GiftPurchaseLog giftPurchaseLog = new GiftPurchaseLog();
		giftPurchaseLog.setMerchantCode(currUser.getId());
		giftPurchaseLog.setGiftCategory(giftConfigCategory.getId());
		giftPurchaseLog.setGiftCount(giftConfigCategory.getBuyCount());
		giftPurchaseLog.setGiftPrice(giftConfigCategory.getGiftPrice());
		Double amountTotal = Double.valueOf(df.format(giftConfigCategory.getBuyCount() * giftConfigCategory.getGiftPrice()));
		giftPurchaseLog.setGiftAmountTotal(amountTotal);
		String payChannel = giftConfigCategory.getPayChannel();
		OrderPaymentInfo orderPaymentInfo = orderPaymentInfoService.genAmountPaymentInfo(payChannel, "1", amountTotal);
		giftPurchaseLog.setPaymentNo(orderPaymentInfo.getPaymentNo());
		giftPurchaseLog.setStatus("0");
		return giftPurchaseLog;
	}
}