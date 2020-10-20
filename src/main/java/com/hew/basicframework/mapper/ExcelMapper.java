package com.hew.basicframework.mapper;


import java.util.List;

/**
 * @author HeXiaoWei
 * @date 2020/10/17 22:29
 */
public interface ExcelMapper<T> {
    /**
     * 把Excel数据保存至数据库对应表里面
     * @param data 数据
     * @return
     */
    public Integer save(List<T> data);
}
