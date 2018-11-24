package com.mall.modules.member.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 店铺收藏Entity
 * @author hub
 * @version 2018-11-24
 */
public class MerchantCollectionInfo extends DataEntity<MerchantCollectionInfo> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 收藏人ID
	private String merchantId;		// 店铺ID
	
	public MerchantCollectionInfo() {
		super();
	}

	public MerchantCollectionInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="收藏人ID长度必须介于 0 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="店铺ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
}