package com.mall.modules.activity.service;

import com.mall.common.utils.SpringContextHolder;
import com.mall.modules.activity.entity.ActivityInfo;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 活动定时器
 *
 * @author wankang
 * @date 2018-11-26
 */
@Slf4j
@Service
@Lazy(false)
public class ActivityHandleTimer {

    private static Logger logger = LoggerFactory.getLogger(ActivityHandleTimer.class);

    /**
     * 定时修改活动状态
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void handleActivityStatus() {
        Date now = new Date();
        ActivityInfoService activityInfoService = SpringContextHolder.getBean(ActivityInfoService.class);
        logger.info("==============扫描活动列表开始=============");
        ActivityInfo queryCondition = new ActivityInfo();
        queryCondition.setStatus("2");
        // 扫描预上线活动
        List<ActivityInfo> activityInfo1 = activityInfoService.findList(queryCondition);
        // 扫描线上活动
        queryCondition.setStatus("1");
        int i = 0;
        int j = 0;
        int k = 0;
        List<ActivityInfo> activityInfo2 = activityInfoService.findList(queryCondition);
        for (ActivityInfo activityInfo : activityInfo2) {
            if(activityInfo.getStartDate().getTime() > now.getTime()) {
                activityInfo.setStatus("2");
                i++;
            }else if(activityInfo.getEndDate().getTime() < now.getTime()) {
                activityInfo.setStatus("0");
                j++;
            }
            activityInfoService.save(activityInfo);
        }
        for (ActivityInfo activityInfo : activityInfo1) {
            if (activityInfo.getStartDate().getTime() > now.getTime() && activityInfo.getEndDate().getTime() < now.getTime()) {
                activityInfo.setStatus("1");
                k++;
            }else if(activityInfo.getEndDate().getTime() < now.getTime()) {
                activityInfo.setStatus("0");
                j++;
            }
            activityInfoService.save(activityInfo);
        }
        logger.info("==============扫描活动列表结束，预上线活动{}，上线活动{}，下线活动{}=============", i, k, j);
    }
}
