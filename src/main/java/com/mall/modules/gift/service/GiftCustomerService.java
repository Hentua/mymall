package com.mall.modules.gift.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.utils.StringUtils;
import com.mall.modules.gift.dao.GiftCustomerDao;
import com.mall.modules.gift.dao.GiftCustomerGoodsDao;
import com.mall.modules.gift.entity.GiftCustomer;
import com.mall.modules.gift.entity.GiftCustomerGoods;
import com.mall.modules.gift.entity.GiftCustomerGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 会员礼包Service
 * @author wankang
 * @version 2018-10-28
 */
@Service
@Transactional(readOnly = true)
public class GiftCustomerService extends CrudService<GiftCustomerDao, GiftCustomer> {

	@Autowired
	private GiftCustomerGoodsDao giftCustomerGoodsDao;

	@Autowired
	private GiftCustomerDao giftCustomerDao;
	
	public GiftCustomer get(String id) {
		GiftCustomer giftCustomer = super.get(id);
		giftCustomer.setGiftCustomerGoodsList(giftCustomerGoodsDao.findList(new GiftCustomerGoods(giftCustomer)));
		return giftCustomer;
	}
	
	public List<GiftCustomer> findList(GiftCustomer giftCustomer) {
		return super.findList(giftCustomer);
	}
	
	public Page<GiftCustomer> findPage(Page<GiftCustomer> page, GiftCustomer giftCustomer) {
		return super.findPage(page, giftCustomer);
	}
	
	@Transactional(readOnly = false)
	public void save(GiftCustomer giftCustomer) {
		super.save(giftCustomer);
		for (GiftCustomerGoods giftCustomerGoods : giftCustomer.getGiftCustomerGoodsList()){
			if (giftCustomerGoods.getId() == null){
				continue;
			}
			if (GiftCustomerGoods.DEL_FLAG_NORMAL.equals(giftCustomerGoods.getDelFlag())){
				if (StringUtils.isBlank(giftCustomerGoods.getId())){
					giftCustomerGoods.setGiftCustomerId(giftCustomer.getId());
					giftCustomerGoods.preInsert();
					giftCustomerGoodsDao.insert(giftCustomerGoods);
				}else{
					giftCustomerGoods.preUpdate();
					giftCustomerGoodsDao.update(giftCustomerGoods);
				}
			}else{
				giftCustomerGoodsDao.delete(giftCustomerGoods);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(GiftCustomer giftCustomer) {
		super.delete(giftCustomer);
		giftCustomerGoodsDao.delete(new GiftCustomerGoods(giftCustomer));
	}

	public Map<String, String> enabledGiftCount(String customerCode) {
		return giftCustomerDao.enabledGiftCount(customerCode);
	}

	public List<GiftCustomerGoodsVo> findGiftCustomerGoodsDetail(String giftCustomerId) {
		return giftCustomerGoodsDao.findGiftCustomerGoodsDetail(giftCustomerId);
	}
	
}