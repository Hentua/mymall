package com.mall.modules.gift.service;

import com.google.common.collect.Lists;
import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.StringUtils;
import com.mall.modules.gift.dao.GiftMerchantDao;
import com.mall.modules.gift.dao.GiftMerchantGoodsDao;
import com.mall.modules.gift.entity.*;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 礼包列表Service
 * @author wankang
 * @version 2018-10-28
 */
@Service
@Transactional(readOnly = true)
public class GiftMerchantService extends CrudService<GiftMerchantDao, GiftMerchant> {

	@Autowired
	private GiftMerchantGoodsDao giftMerchantGoodsDao;
	@Autowired
	private GiftCustomerService giftCustomerService;
	@Autowired
	private GiftGiveLogService giftGiveLogService;
	@Autowired
	private GiftMerchantDao giftMerchantDao;
	
	public GiftMerchant get(String id) {

		GiftMerchant giftMerchant = super.get(id);
		giftMerchant.setGiftMerchantGoodsList(giftMerchantGoodsDao.findList(new GiftMerchantGoods(giftMerchant)));
		return giftMerchant;
	}
	
	public List<GiftMerchant> findList(GiftMerchant giftMerchant) {
		return super.findList(giftMerchant);
	}
	
	public Page<GiftMerchant> findPage(Page<GiftMerchant> page, GiftMerchant giftMerchant) {
		return super.findPage(page, giftMerchant);
	}
	
	@Transactional(readOnly = false)
	public void save(GiftMerchant giftMerchant) {
		super.save(giftMerchant);
		for (GiftMerchantGoods giftMerchantGoods : giftMerchant.getGiftMerchantGoodsList()){
			if (giftMerchantGoods.getId() == null){
				continue;
			}
			if (GiftMerchantGoods.DEL_FLAG_NORMAL.equals(giftMerchantGoods.getDelFlag())){
				if (StringUtils.isBlank(giftMerchantGoods.getId())){
					giftMerchantGoods.setGiftMerchantId(giftMerchant.getId());
					giftMerchantGoods.preInsert();
					giftMerchantGoodsDao.insert(giftMerchantGoods);
				}else{
					giftMerchantGoods.preUpdate();
					giftMerchantGoodsDao.update(giftMerchantGoods);
				}
			}else{
				giftMerchantGoodsDao.delete(giftMerchantGoods);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(GiftMerchant giftMerchant) {
		super.delete(giftMerchant);
		giftMerchantGoodsDao.delete(new GiftMerchantGoods(giftMerchant));
	}

	public List<GiftMerchantGoods> translateConfigGoods(List<GiftConfigGoods> configGoods) {
		List<GiftMerchantGoods> merchantGoods = Lists.newArrayList();
		for (GiftConfigGoods g : configGoods) {
			GiftMerchantGoods giftMerchantGoods = new GiftMerchantGoods();
			giftMerchantGoods.setId("");
			giftMerchantGoods.setGoodsCount(g.getGoodsCount());
			giftMerchantGoods.setGoodsId(g.getGoodsId());
			giftMerchantGoods.setMerchantCode(g.getMerchantCode());
			merchantGoods.add(giftMerchantGoods);
		}
		return merchantGoods;
	}

	public List<GiftCustomerGoods> translateMerchantGoods(List<GiftMerchantGoods> merchantGoods) {
		List<GiftCustomerGoods> customerGoods = Lists.newArrayList();
		for (GiftMerchantGoods g : merchantGoods) {
			GiftCustomerGoods giftCustomerGoods = new GiftCustomerGoods();
			giftCustomerGoods.setId("");
			giftCustomerGoods.setGoodsCount(g.getGoodsCount());
			giftCustomerGoods.setGoodsId(g.getGoodsId());
			giftCustomerGoods.setMerchantCode(g.getMerchantCode());
			customerGoods.add(giftCustomerGoods);
		}
		return customerGoods;
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void memberGiftGive(String memberId, String giftMerchantId) throws Exception {
		GiftMerchant giftMerchant = this.get(giftMerchantId);
		if(null == giftMerchant) {
			throw new ServiceException("礼包不存在");
		}
		int giftCount = giftMerchant.getGiftCount();
		if(giftCount <= 0) {
			throw new ServiceException("礼包数量不足");
		}
		// 组装客户礼包
		GiftCustomer giftCustomer = new GiftCustomer();
		giftCustomer.setGiftMerchantId(giftMerchantId);
		giftCustomer.setGiftName(giftMerchant.getGiftName());
		giftCustomer.setOriginalPrice(giftMerchant.getOriginalPrice());
		giftCustomer.setGiftPrice(giftMerchant.getGiftPrice());
		giftCustomer.setGoodsCount(giftMerchant.getGoodsCount());
		giftCustomer.setMerchantCode(UserUtils.getUser().getId());
		giftCustomer.setCustomerCode(memberId);
		giftCustomer.setGiftCount(1);
		giftCustomer.setGiftCustomerGoodsList(this.translateMerchantGoods(giftMerchant.getGiftMerchantGoodsList()));
		giftCustomerService.save(giftCustomer);
		String giftCustomerId = giftCustomer.getId();
		// 保存赠送记录
		GiftGiveLog giftGiveLog = new GiftGiveLog();
		giftGiveLog.setGiftConfigId(giftMerchant.getGiftConfigId());
		giftGiveLog.setGiftMerchantId(giftMerchantId);
		giftGiveLog.setGiftCustomerId(giftCustomerId);
		giftGiveLog.setMerchantCode(UserUtils.getUser().getId());
		giftGiveLog.setCustomerCode(memberId);
		giftGiveLog.setGiftGoodsCount(giftMerchant.getGoodsCount());
		giftGiveLog.setGiftCount(1);
		giftGiveLog.setGiftName(giftMerchant.getGiftName());
		giftGiveLogService.save(giftGiveLog);
		// 库存扣减
		giftMerchant.setGiftCount(giftMerchant.getGiftCount() - 1);
		giftMerchantDao.stockModify(giftMerchant);
	}
	
}