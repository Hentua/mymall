package com.mall.modules.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.member.entity.MerchantCollectionInfo;
import com.mall.modules.member.dao.MerchantCollectionInfoDao;

/**
 * 店铺收藏Service
 * @author hub
 * @version 2018-11-24
 */
@Service
@Transactional(readOnly = true)
public class MerchantCollectionInfoService extends CrudService<MerchantCollectionInfoDao, MerchantCollectionInfo> {

	public MerchantCollectionInfo get(String id) {
		return super.get(id);
	}
	
	public List<MerchantCollectionInfo> findList(MerchantCollectionInfo merchantCollectionInfo) {
		return super.findList(merchantCollectionInfo);
	}
	
	public Page<MerchantCollectionInfo> findPage(Page<MerchantCollectionInfo> page, MerchantCollectionInfo merchantCollectionInfo) {
		return super.findPage(page, merchantCollectionInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantCollectionInfo merchantCollectionInfo) {
		super.save(merchantCollectionInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantCollectionInfo merchantCollectionInfo) {
		super.delete(merchantCollectionInfo);
	}
	
}