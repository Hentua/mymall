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
    /**
     * 重置用户默认地址
     *
     * @param customerCode 用户ID
     */
	void restoreDefaultDeliveryAddress(String customerCode);

    /**
     * 设置用户默认地址
     *
     * @param id 地址ID
     */
	void setDefaultDeliveryAddress(String id);

    /**
     * 删除收货地址 不能删除默认收货地址
     * @param id 收货地址ID
     * @return 修改的条目数
     */
	int deleteAddress(String id);
}