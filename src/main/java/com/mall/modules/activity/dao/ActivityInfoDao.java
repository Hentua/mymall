package com.mall.modules.activity.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.activity.entity.ActivityInfo;

/**
 * 活动配置DAO接口
 *
 * @author wankang
 * @version 2018-11-14
 */
@MyBatisDao
public interface ActivityInfoDao extends CrudDao<ActivityInfo> {

    /**
     * 验证是否有时间重叠的活动
     *
     * @param activityInfo 查询条件
     * @return 重叠条数
     */
    int verifyActivityInfo(ActivityInfo activityInfo);

    /**
     * 获取当前生效的活动
     *
     * @param activityInfo 查询条件
     * @return 活动实体
     */
    ActivityInfo enabledActivityInfo(ActivityInfo activityInfo);

}