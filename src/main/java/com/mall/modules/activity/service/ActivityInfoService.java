package com.mall.modules.activity.service;

import java.util.List;

import com.mall.modules.act.entity.Act;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.modules.activity.entity.ActivityInfo;
import com.mall.modules.activity.dao.ActivityInfoDao;

/**
 * 活动配置Service
 * @author wankang
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class ActivityInfoService extends CrudService<ActivityInfoDao, ActivityInfo> {

	public ActivityInfo get(String id) {
		return super.get(id);
	}
	
	public List<ActivityInfo> findList(ActivityInfo activityInfo) {
		return super.findList(activityInfo);
	}
	
	public Page<ActivityInfo> findPage(Page<ActivityInfo> page, ActivityInfo activityInfo) {
		return super.findPage(page, activityInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ActivityInfo activityInfo) {
		super.save(activityInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ActivityInfo activityInfo) {
		super.delete(activityInfo);
	}

	public int verifyActivityInfo(ActivityInfo activityInfo) {
		return dao.verifyActivityInfo(activityInfo);
	}

	public ActivityInfo enabledActivityInfo(ActivityInfo activityInfo) {
		return dao.enabledActivityInfo(activityInfo);
	}
	
}