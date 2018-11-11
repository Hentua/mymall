package com.mall.modules.commission.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.commission.entity.CommissionInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 佣金明细DAO接口
 * @author hub
 * @version 2018-10-21
 */
@MyBatisDao
public interface CommissionInfoDao extends CrudDao<CommissionInfo> {


    public void editStatus(@Param("id") String id);


    public void editOrderStatus(@Param("orderId") String orderId);


    public void accumulation(CommissionInfo commissionInfo);


	
}