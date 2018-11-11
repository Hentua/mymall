package com.mall.modules.account.service;

import com.mall.common.utils.SpringContextHolder;
import com.mall.modules.commission.entity.CommissionInfo;
import com.mall.modules.commission.service.CommissionInfoService;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.order.service.OrderInfoService;
import com.mall.modules.sys.utils.DictUtils;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 账单定时任务
 */
@Slf4j
@Service
@Lazy(false)
public class CheckAccountTimer {

    private AccountInfoService accountInfoService;

    private OrderInfoService orderInfoService;


    private CommissionInfoService commissionInfoService;

    private Logger logger =  LoggerFactory.getLogger(CheckAccountTimer.class);

    /**
     * 每分钟执行
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void execute() {
        accountInfoService = SpringContextHolder.getBean(AccountInfoService.class);
        orderInfoService = SpringContextHolder.getBean(OrderInfoService.class);
        commissionInfoService = SpringContextHolder.getBean(CommissionInfoService.class);
        logger.info("==================执行清算定时器开始========================");
        //查询所有已完成 未清算的订单
        OrderInfo o = new OrderInfo();
        o.setOrderStatus("3");
        o.setSetFlag("0");
        List<OrderInfo> orderInfos = orderInfoService.findList(o);
        //当前时间
        Date date = new Date();
        //可退货时间 毫秒
        Long dayTime = Long.parseLong(DictUtils.getDictValue("account_day_time","account_day_time","1"));
        for (OrderInfo orderInfo : orderInfos) {
            if("3".equals(orderInfo.getOrderStatus())){
                //当前时间 减去大 完成时间 于结算时间
                Date completedTime = orderInfo.getCompletedTime();
                if(null == completedTime){
                    completedTime = orderInfo.getAutoCompletedTime();
                }
                if((date.getTime() - completedTime.getTime())>dayTime){
                    //清算
                    logger.info("清算订单号："+orderInfo.getOrderNo());
                    accountInfoService.toAccount(orderInfo.getId());
                    CommissionInfo c = new CommissionInfo();
                    c.setUnionId(orderInfo.getId());
                    List<CommissionInfo> commissionInfos = commissionInfoService.findList(c);
                    for (CommissionInfo ci: commissionInfos) {
                        commissionInfoService.editStatus(ci);
                    }
                }
            }
        }
        logger.info("==================执行清算定时器结束========================");
    }
}
