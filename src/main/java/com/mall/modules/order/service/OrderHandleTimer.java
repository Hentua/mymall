package com.mall.modules.order.service;

import com.mall.common.utils.SpringContextHolder;
import com.mall.modules.order.entity.OrderInfo;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 订单定时任务
 *
 * @author wankang
 * @date 2018-11-26
 */
@Slf4j
@Service
@Lazy(false)
public class OrderHandleTimer {

    private static Logger logger = LoggerFactory.getLogger(OrderHandleTimer.class);

    /**
     * 自动处理订单定时器
     * 现在为每分钟执行
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void autoHandleOrder() {
        Date now = new Date();
        OrderInfoService orderInfoService = SpringContextHolder.getBean(OrderInfoService.class);
        logger.info("==============扫描订单开始=============");
        // 查询未付款订单
        OrderInfo queryCondition = new OrderInfo();
        queryCondition.setOrderStatus("0");
        List<OrderInfo> pendingPayList = orderInfoService.findList(queryCondition);
        queryCondition.setOrderStatus("2");
        List<OrderInfo> pendingTakeList = orderInfoService.findList(queryCondition);
        int cancelCount = 0;
        int completeCount = 0;
        for (OrderInfo o : pendingPayList) {
            cancelCount++;
            Calendar calendar = Calendar.getInstance();
            Date createDate = o.getCreateDate();
            calendar.setTime(createDate);
            calendar.add(Calendar.MINUTE, 30);
            if(now.getTime() < calendar.getTimeInMillis()) {
                orderInfoService.autoOrderCancel(o.getId());
            }
        }
        for (OrderInfo o : pendingTakeList) {
            completeCount++;
            Calendar calendar = Calendar.getInstance();
            Date deliveryTime = o.getDeliveryTime();
            calendar.setTime(deliveryTime);
            calendar.add(Calendar.DATE, 7);
            if(now.getTime() < calendar.getTimeInMillis()) {
                orderInfoService.autoOrderComplete(o.getId());
            }
        }
        logger.info("==============扫描订单结束，自动关闭订单数：{}，自动完成订单数：{}=============", cancelCount, completeCount);
    }
}
