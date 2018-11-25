package com.mall.modules.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.notice.entity.IndexNotice;
import com.mall.modules.notice.dao.IndexNoticeDao;

/**
 * 首页公告Service
 * @author hub
 * @version 2018-11-25
 */
@Service
@Transactional(readOnly = true)
public class IndexNoticeService extends CrudService<IndexNoticeDao, IndexNotice> {

	public IndexNotice get(String id) {
		return super.get(id);
	}
	
	public List<IndexNotice> findList(IndexNotice indexNotice) {
		return super.findList(indexNotice);
	}
	
	public Page<IndexNotice> findPage(Page<IndexNotice> page, IndexNotice indexNotice) {
		return super.findPage(page, indexNotice);
	}
	
	@Transactional(readOnly = false)
	public void save(IndexNotice indexNotice) {
		super.save(indexNotice);
	}
	
	@Transactional(readOnly = false)
	public void delete(IndexNotice indexNotice) {
		super.delete(indexNotice);
	}
	
}