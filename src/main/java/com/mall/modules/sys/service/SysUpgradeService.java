package com.mall.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.sys.entity.SysUpgrade;
import com.mall.modules.sys.dao.SysUpgradeDao;

/**
 * app升级配置Service
 * @author hub
 * @version 2018-12-04
 */
@Service
@Transactional(readOnly = true)
public class SysUpgradeService extends CrudService<SysUpgradeDao, SysUpgrade> {

	public SysUpgrade get(String id) {
		return super.get(id);
	}
	
	public List<SysUpgrade> findList(SysUpgrade sysUpgrade) {
		return super.findList(sysUpgrade);
	}
	
	public Page<SysUpgrade> findPage(Page<SysUpgrade> page, SysUpgrade sysUpgrade) {
		return super.findPage(page, sysUpgrade);
	}
	
	@Transactional(readOnly = false)
	public void save(SysUpgrade sysUpgrade) {
		super.save(sysUpgrade);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysUpgrade sysUpgrade) {
		super.delete(sysUpgrade);
	}
	
}