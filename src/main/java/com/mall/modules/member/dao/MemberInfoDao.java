package com.mall.modules.member.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.member.entity.MemberInfo;

import java.util.List;

/**
 * 用户信息DAO接口
 *
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

    /**
     * 获取支付密码
     *
     * @param id 用户ID
     * @return 支付密码
     */
    String getPayPassword(String id);

    /**
     * 保存支付密码
     *
     * @param memberInfo 会员实体
     */
    void savePayPassword(MemberInfo memberInfo);

    /**
     * 修改商户信息
     *
     * @param memberInfo 会员实体
     */
    void updateMerchantData(MemberInfo memberInfo);

    /**
     * 修改用户归属运营
     *
     * @param memberInfo 会员实体
     */
    void modifyMemberOperator(MemberInfo memberInfo);

    /**
     * 用户绑定微信
     *
     * @param memberInfo 会员实体
     */
    void bindWechat(MemberInfo memberInfo);

    /**
     * 修改用户头像
     *
     * @param memberInfo 会员实体
     */
    void modifyAvatar(MemberInfo memberInfo);

    /**
     * 修改用户推荐商户
     *
     * @param memberInfo 会员实体
     */
    void modifyMerchantRefereeId(MemberInfo memberInfo);


    List<MemberInfo> findCollectionList(MemberInfo memberInfo);

    /**
     * 修改用户状态为未审核
     *
     * @param id 会员ID
     */
    void uncheckStatus(String id);

    /**
     * 根据权限查询商户
     *
     * @param memberInfo 查询条件
     * @return 商户列表
     */
    List<MemberInfo> findListByPower(MemberInfo memberInfo);

}