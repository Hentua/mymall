package com.mall.common.utils;

import java.util.List;

/**
 *
 * Created by brant on 2018-4-13.
 */
public interface TreeNodeData<E>  {

    /**
     * 父ID
     * @return
     */
    public String getParentCode();

    /**
     * 当前ID
     * @return
     */
    public String getId();

    /**
     * 层级
     * @return
     */
    public Integer getTreeLevel();

    /**
     * 设置子集合
     * @param childrens
     */
    public void setChildrens(List<E> childrens);

    /**
     * 获取子集合
     * @return
     */
    public List<E> getChildrens();


    /**
     * 排序标识
     * @return
     */
    public Long getTreeSort();
}
