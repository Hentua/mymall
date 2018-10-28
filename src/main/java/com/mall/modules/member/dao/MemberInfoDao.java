package com.mall.modules.member.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.member.entity.MemberInfo;

/**
 * 用户信息DAO接口
 * @author wankang
 * @version 2018-10-10
 */
@MyBatisDao
public interface MemberInfoDao extends CrudDao<MemberInfo> {

    /**
     * 会员审核
     *
     * @param memberInfo 会员实体
     */
    void memberCheck(MemberInfo memberInfo);
	
}