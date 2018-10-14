package com.mall.modules.sys.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.sys.dao.SysSmsLogDao;
import com.mall.modules.sys.entity.SysSmsLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 短信发送记录Service
 * @author wankang
 * @version 2018-10-11
 */
@Service
@Transactional(readOnly = true)
public class SysSmsLogService extends CrudService<SysSmsLogDao, SysSmsLog> {

	public SysSmsLog get(String id) {
		return super.get(id);
	}
	
	public List<SysSmsLog> findList(SysSmsLog sysSmsLog) {
		return super.findList(sysSmsLog);
	}
	
	public Page<SysSmsLog> findPage(Page<SysSmsLog> page, SysSmsLog sysSmsLog) {
		return super.findPage(page, sysSmsLog);
	}
	
	@Transactional(readOnly = false)
	public void save(SysSmsLog sysSmsLog) {
		super.save(sysSmsLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysSmsLog sysSmsLog) {
		super.delete(sysSmsLog);
	}
	
}