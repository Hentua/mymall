package com.mall.modules.commission.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.commission.entity.CommissionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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


	public Map<String,Object> merchantIndexSts(@Param("userId") String userId);

    public List<Map<String,Object>> areaList(Map<String,Object> paramMap);
}