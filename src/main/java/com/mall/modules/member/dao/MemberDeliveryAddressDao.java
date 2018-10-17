package com.mall.modules.member.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.member.entity.MemberDeliveryAddress;

/**
 * 收货地址DAO接口
 * @author wankang
 * @version 2018-10-17
 */
@MyBatisDao
public interface MemberDeliveryAddressDao extends CrudDao<MemberDeliveryAddress> {
	
}