package com.mall.modules.goods.entity;

import com.mall.common.persistence.DataEntity;
import com.mall.modules.billboard.entity.IndexBillboard;

/**
 * 广告位关联商品实体
 */
public class BillboardGoods  extends DataEntity<GoodsCategory> {

    //广告位信息
    private IndexBillboard billboard;
    //商品信息
    private GoodsInfo goods;

    public IndexBillboard getBillboard() {
        return billboard;
    }

    public void setBillboard(IndexBillboard billboard) {
        this.billboard = billboard;
    }

    public GoodsInfo getGoods() {
        return goods;
    }

    public void setGoods(GoodsInfo goods) {
        this.goods = goods;
    }
}
