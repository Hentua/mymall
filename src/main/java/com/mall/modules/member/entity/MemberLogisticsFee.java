package com.mall.modules.member.entity;

import com.mall.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 运费定义Entity
 * @author wankang
 * @version 2018-10-19
 */
public class MemberLogisticsFee extends DataEntity<MemberLogisticsFee> {
	
	private static final long serialVersionUID = 1L;
	private String merchantCode;		// 商铺ID
	private String province;		// 省份ID
	private Double logisticsFee;		// 运费
	
	public MemberLogisticsFee() {
		super();
	}

	public MemberLogisticsFee(String id){
		super(id);
	}

	@Length(min=1, max=64, message="商铺ID长度必须介于 1 和 64 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	@NotNull(message="省份ID不能为空")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@NotNull(message="运费不能为空")
	public Double getLogisticsFee() {
		return logisticsFee;
	}

	public void setLogisticsFee(Double logisticsFee) {
		this.logisticsFee = logisticsFee;
	}
	
}