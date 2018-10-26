package com.mall.modules.commission.service;

import java.util.List;

import com.mall.common.utils.StringUtils;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.commission.entity.CommissionConfig;
import com.mall.modules.commission.dao.CommissionConfigDao;

/**
 * 佣金比例配置信息Service
 * @author hub
 * @version 2018-10-21
 */
@Service
@Transactional(readOnly = true)
public class CommissionConfigService extends CrudService<CommissionConfigDao, CommissionConfig> {

	public CommissionConfig get(String id) {
		return super.get(id);
	}
	
	public List<CommissionConfig> findList(CommissionConfig commissionConfig) {
		return super.findList(commissionConfig);
	}
	
	public Page<CommissionConfig> findPage(Page<CommissionConfig> page, CommissionConfig commissionConfig) {
		return super.findPage(page, commissionConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(CommissionConfig commissionConfig) {
		super.save(commissionConfig);
	}
	
	@Transactional(readOnly = false)
	public void delete(CommissionConfig commissionConfig) {
		super.delete(commissionConfig);
	}

	private List<CommissionConfig> configs;


	/**
	 * 按返佣类型 交易金额 获取交易佣金
	 * @param type 佣金类型 （1：推荐用户消费返佣 2：推荐商家销售返佣 3：推荐商家入驻返佣 4：推荐商家送出礼包返佣 5：商家送出礼包返佣）
	 * @param amountTotal 交易总额
	 * @return 佣金
	 */
	public Double getCommissionAmount(String type,Double amountTotal){
		if(StringUtils.isEmpty(type)){
			logger.error("获取佣金失败：参数佣金类型为空");
			return 0.0;
		}
		if(null == this.getConfigs()){
			this.setConfigs(this.findList(new CommissionConfig()));
		}
		for (CommissionConfig config: this.getConfigs()) {
			if(type.equals(config.getType())){
				//按固定金额计算
				if("1".equals(config.getMode())){
					return config.getNumber();
				}
				//按百分比计算
				if("2".equals(config.getMode())){
					if(null == amountTotal || amountTotal <= 0.0){
						return 0.0;
					}
					//按交易金额 计算百分比+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					return config.getNumber()/amountTotal*100;
				}
			}
		}
		logger.info("获取佣金失败：参数无效 type:"+type+" amountTotal"+amountTotal);
		return  0.0;
	}

	public List<CommissionConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(List<CommissionConfig> configs) {
		this.configs = configs;
	}
}