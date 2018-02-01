package com.github.trang.dynamic.base.mapper;

import com.github.trang.dynamic.base.mapper.example.SelectOneByExampleMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 通用 Mapper
 *
 * @param <T>
 * @author trang
 */
public interface BaseMapper<T> extends
        tk.mybatis.mapper.common.BaseMapper<T>,
        IdsMapper<T>,
        ExampleMapper<T>,
        SelectOneByExampleMapper<T>,
        InsertListMapper<T> {

}