package com.mall.modules.billboard.dao;

import com.mall.common.persistence.CrudDao;
import com.mall.common.persistence.annotation.MyBatisDao;
import com.mall.modules.billboard.entity.IndexBillboard;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 首页广告位DAO接口
 * @author hub
 * @version 2018-10-14
 */
@MyBatisDao
public interface IndexBillboardDao extends CrudDao<IndexBillboard> {

    public void insertGoodsInfo(Map<String,Object> map);

    public void delIndexGoodsInfo(@Param("billboardId") String billboardId);
	
}