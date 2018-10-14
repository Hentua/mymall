package com.mall.common.utils;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by brant on 2018-4-13.
 */
public class TreeNode<E  extends TreeNodeData<E>>  {

    /**
     * 所有数据
     */
    public List<E> allList = null;

    /**
     * 初始化构造函数
     * @param allList 所有Node
     */
    public TreeNode(List<E> allList){
        if(null == allList)
            this.allList = new ArrayList<E>();
        else
            this.allList=allList;
    }
    private TreeNode(){};

    /**
     * 获取父级对象
     * @param t
     * @return
     */
    public E getParent(E t){
        for (E obj: allList) {
            if(obj.getId().equals(t.getParentCode())){
                return t;
            }
        }
        return null;
    }

    /**
     * 获取子集合
     * @param t
     * @return
     */
    public List<E> getChildrens(E t){
        List<E> list = new ArrayList<E>();
        for (E obj: allList) {
            if(obj.getParentCode().equals(t.getId())){
                list.add(obj);
            }
        }
        sort(list);
        return list;
    }

    /**
     * 按级别获取集合
     * @param level
     * @return
     */
    public List<E> getListByLevel(Integer level){
        List<E> list = new ArrayList<E>();
        for (E obj: allList) {
            if(level.equals(obj.getTreeLevel())){
                list.add(obj);
            }
        }
        sort(list);
        return list;
    }

    /**
     * 排序方法
     * @param list
     */
    private void sort(List<E> list){
        //排序
        Collections.sort(list, new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                if(null == o1.getTreeSort() || null == o2.getTreeSort()){
                    return 0;
                }
                long i = o1.getTreeSort() - o2.getTreeSort();
                return (int)i;
            }
        });
    }

    /**
     * 获取json类型树结构
     * @return
     */
    public JSONArray getJsonTree(){
//        List<E> retList = getListByLevel(0);
        return JSONArray.parseArray(JSONUtils.toJSONString(TreeFormat()));

    }

    /**
     * 获取树结构数据
     * @return
     */
    public List<E> TreeFormat(){
        List<E> retList = getListByLevel(0);
        installTree(retList);
        return retList;
    }

    /**
     * 递归组装树
     * @param list
     */
    public void installTree(List<E> list){
        for (E e: list) {
            List<E> childrens = getChildrens(e);
            e.setChildrens(childrens);
            installTree(e.getChildrens());
        }
    }






}
