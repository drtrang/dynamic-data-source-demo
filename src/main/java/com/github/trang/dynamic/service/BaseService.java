package com.github.trang.dynamic.service;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 基础 Service 类
 *
 * @author trang
 */
public interface BaseService<T, PK extends Serializable> {
    // ------ C ------ //
    /*
     * 新增数据，值为 null 的 field 不会写入
     */
    int insert(T entity);

    /*
     * 新增数据，值为 null 的 field 也会写入
     */
    int insertUnchecked(T entity);

    int insertBatch(List<T> entityList);

    // ------ U ------ //
    /*
     * 更新数据，值为 null 的 field 不会写入
     */
    int update(T record);

    /*
     * 更新数据，值为 null 的 field 也会写入
     */
    int updateUnchecked(T record);

    // ------ D ------ //
    int delete(T record);

    int deleteByPk(PK pk);

    int deleteByIds(Iterable<? extends PK> ids);

    // ------ R ------ //
    List<T> select(T entity);

    T selectByPk(PK pk);

    T selectOne(T entity);

    List<T> selectByIds(Iterable<? extends PK> ids);

    int selectCount(T entity);

    /*
     * 分页查询，使用 RowBounds 方式，不会查询 count
     */
    PageInfo<T> selectPage(T entity, int pageNum, int pageSize);

    /*
     * 分页查询，使用 PageHelper.startPage()，查询 count
     * 若同时需要排序，可手动指定 PageHelper.orderBy()
     * {@link PageHelper}
     */
    PageInfo<T> selectPageAndCount(T entity, int pageNum, int pageSize);

}