package com.github.trang.dynamic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.trang.dynamic.service.BaseService;
import com.github.trang.dynamic.util.BaseMapper;
import com.google.common.base.Joiner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * BaseService 实现类
 *
 * @author trang
 */
public abstract class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

    @Autowired
    private BaseMapper<T> mapper;

    /**
     * 选择性保存，若属性为 null 则不会保存，使用数据库默认值
     */
    @Override
    @Transactional
    public int insert(T entity) {
        return mapper.insertSelective(entity);
    }

    /**
     * 直接保存，属性为 null 也会保存
     */
    @Override
    @Transactional
    public int insertUnchecked(T entity) {
        return mapper.insert(entity);
    }

    /**
     * 批量保存，要求 <T> 必须包含 id 属性，且 id 为自增；不检查是否为 null
     */
    @Override
    @Transactional
    public int insertBatch(List<T> entityList) {
        return mapper.insertList(entityList);
    }

    /**
     * 根据主键删除单条数据
     */
    @Override
    @Transactional
    public int deleteByPk(PK pk) {
        return mapper.deleteByPrimaryKey(pk);
    }

    /**
     * 根据条件删除数据
     */
    @Override
    @Transactional
    public int delete(T record) {
        return mapper.delete(record);
    }

    /**
     * 根据主键批量删除数据
     */
    @Override
    @Transactional
    public int deleteByIds(Iterable<? extends PK> ids) {
        String _ids = Joiner.on(',').skipNulls().join(ids);
        return mapper.deleteByIds(_ids);
    }

    /**
     * 根据条件更改数据，若属性为 null 则不保存
     */
    @Override
    @Transactional
    //@CacheEvict(key="#root.targetClass.getSimpleName() + '.' + #record.cacheKey")
    public int update(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据条件更改数据，若属性为 null 依然保存
     */
    @Override
    @Transactional
    public int updateUnchecked(T record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public List<T> select(T record) {
        return mapper.select(record);
    }

    @Override
    public T selectByPk(PK pk) {
        return mapper.selectByPrimaryKey(pk);
    }

    @Override
    public T selectOne(T record) {
        PageHelper.offsetPage(0, 1, false);
        return mapper.selectOne(record);
    }

    @Override
    public List<T> selectByIds(Iterable<? extends PK> ids) {
        String _ids = Joiner.on(',').skipNulls().join(ids);
        return mapper.selectByIds(_ids);
    }

    @Override
    public int selectCount(T record) {
        return mapper.selectCount(record);
    }

    @Override
    public PageInfo<T> selectPage(T record, int pageNum, int pageSize) {
        List<T> selectList = mapper.selectByRowBounds(record, new RowBounds(pageNum, pageSize));
        return new PageInfo<>(selectList);
    }

    @Override
    public PageInfo<T> selectPageAndCount(T record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> selectList = mapper.select(record);
        return new PageInfo<>(selectList);
    }

}