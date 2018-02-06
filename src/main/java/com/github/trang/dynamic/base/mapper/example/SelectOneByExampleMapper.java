package com.github.trang.dynamic.base.mapper.example;

import org.apache.ibatis.annotations.SelectProvider;

/**
 * 通用 Mapper 接口，Example 查询
 *
 * @param <T> 不能为空
 * @author liuzh
 */
public interface SelectOneByExampleMapper<T> {

    /**
     * 根据 Example 条件进行查询
     *
     * @param example 查询条件
     * @return 查询结果
     */
    @SelectProvider(type = SelectOneByExampleProvider.class, method = "dynamicSQL")
    T selectOneByExample(Object example);

}