package com.github.trang.dynamic.base.service;

import com.github.pagehelper.PageInfo;
import com.github.trang.dynamic.base.model.BaseModel;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

/**
 * BaseService，包含常用的单表操作
 *
 * @author trang
 */
public interface BaseService<T extends BaseModel<PK>, PK extends Serializable> {

    // ------ C ------ //

    /**
     * 新增数据，值为 null 的字段不会保存（可以保留字段的默认值）
     *
     * 注意：
     *   1.支持回写主键的数据库可以从 record 中获取返回的主键
     *
     * SQL 示例:
     *   INSERT INTO sh_housedel_log(id,log_type,business_id) VALUES(?,?,?)
     *
     * @param record 待保存的数据
     * @return int 影响行数
     */
    int insertSelective(T record);

    /**
     * 新增数据，值为 null 的字段也会保存
     *
     * 注意：
     *   1.支持回写主键的数据库可以从 record 中获取返回的主键
     *   2.底层使用 #{@link tk.mybatis.mapper.common.base.insert.InsertMapper#insert(Object)} 方法，
     *     与 #{@link tk.mybatis.mapper.common.MySqlMapper#insertUseGeneratedKeys(Object)} 的区别是
     *     适用于所有数据库，且可以直接插入主键，insertUseGeneratedKeys 要求主键必须为自增且字段名称为 id
     *
     * SQL 示例:
     *   INSERT INTO sh_housedel_log(id,log_type,business_id,operate_type,operator_ucid,operator_name,
     *   operator_ip,operation_reason,created_time,brand,log_content) VALUES
     *   (?,?,?,?,?,?,?,?,?,?,?)
     *
     * @param record 待保存的数据
     * @return int 影响行数
     */
    int insert(T record);

    /**
     * 批量新增数据，值为 null 的字段不会保存（可以保留字段的默认值）
     *
     * 注意：
     *   1.支持回写主键的数据库可以从 records 中获取返回的主键（MySQL 支持多主键回写）
     *
     * SQL 示例:
     *   INSERT INTO sh_housedel_log(id,log_type,business_id) VALUES(?,?,?), (?,?,?)
     *
     * @param records 待保存的数据集合
     * @return int 影响行数
     */
    int insertList(Iterable<T> records);

    /**
     * 批量新增数据，值为 null 的字段也会保存
     *
     * 注意：
     *   1.支持回写主键的数据库可以从 records 中获取返回的主键（MySQL 支持多主键回写）
     *
     * SQL 示例:
     *   INSERT INTO sh_housedel_log(log_type,business_id,operate_type,operator_ucid,operator_name,
     *   operator_ip,operation_reason,created_time,brand,log_content) VALUES
     *   (?,?,?,?,?,?,?,?,?,?), (?,?,?,?,?,?,?,?,?,?)
     *
     * @param records 待保存的数据集合
     * @return int 影响行数
     *
     * int insertListSelective(Iterable<T> records);
     */

    // ------ U ------ //

    /**
     * 根据主键更新数据，值为 null 的字段不会更新
     *
     * 注意：
     *   1.主键为 @Id 修饰的字段，业务方需要保证该字段不能为 null，如果为 null SQL 会执行成功但不会更新任何数据
     *
     * SQL 示例:
     *   UPDATE sh_housedel_log SET created_time = ? WHERE id = ?
     *
     * @param record 待更新的数据
     * @return int 影响行数
     */
    int update(T record);

    /**
     * 根据主键更新数据，值为 null 的字段会更新为 null
     *
     * 注意：
     *   1.主键为 @Id 修饰的字段，业务方需要保证该字段不能为 null，如果为 null SQL 会执行成功但不会更新任何数据
     *
     * SQL 示例:
     *   UPDATE sh_housedel_log SET log_type = ?,business_id = ?,operate_type = ?,operator_ucid = ?,
     *   operator_name = ?,operator_ip = ?,operation_reason = ?,created_time = ?,brand = ?,log_content = ?
     *   WHERE id = ?
     *
     * @param record 待更新的数据
     * @return int 影响行数
     */
    int updateUnchecked(T record);

    /**
     * 根据 Example 条件更新数据，值为 null 的字段不会更新
     *
     * SQL 示例:
     *   UPDATE sh_housedel_log SET created_time = ? WHERE business_id = ?
     *
     * @param record  待更新的数据
     * @param example 更新条件
     * @return int 影响行数
     */
    int updateByExample(T record, Example example);

    /**
     * 根据 Example 条件更新数据，值为 null 的字段会更新为 null
     *
     * SQL 示例:
     *   UPDATE sh_housedel_log SET log_type = ?,business_id = ?,operate_type = ?,operator_ucid = ?,
     *   operator_name = ?,operator_ip = ?,operation_reason = ?,created_time = ?,brand = ?,log_content = ?
     *   WHERE business_id = ?
     *
     * @param record  待更新的数据
     * @param example 更新条件
     * @return int 影响行数
     */
    int updateUncheckedByExample(T record, Example example);

    // ------ D ------ //

    /**
     * 根据主键删除数据
     *
     * SQL 示例:
     *   DELETE FROM sh_housedel_log WHERE id = ?
     *
     * @param pk     主键
     * @return int 影响行数
     */
    int deleteByPk(PK pk);

    /**
     * 根据主键集合删除数据
     *
     * 注意：
     *   1.实体类中只能存在一个带有 @Id 注解的字段
     *   2.pks 不能为 null 且不能为空
     *
     * SQL 示例:
     *   DELETE FROM sh_housedel_log WHERE id IN (?,?,?)
     *
     * @param pks    主键集合
     * @return int 影响行数
     */
    int deleteByPks(Iterable<? extends PK> pks);

    /**
     * 根据 `=` 条件删除数据
     *
     * SQL 示例:
     *   DELETE FROM sh_housedel_log WHERE business_id = ?
     *
     * @param param  删除条件
     * @return int 影响行数
     */
    int delete(T param);

    /**
     * 删除全部数据
     *
     * SQL 示例:
     *   DELETE FROM sh_housedel_log
     *
     * @return int 影响行数
     */
    int deleteAll();

    /**
     * 根据 Example 条件删除数据
     *
     * SQL 示例:
     *   DELETE FROM sh_housedel_log WHERE business_id IN (?,?,?)
     *
     * @param example 删除条件
     * @return int 影响行数
     */
    int deleteByExample(Example example);

    // ------ R ------ //

    /**
     * 根据主键查询数据
     *
     * SQL 示例:
     *   SELECT id,log_type,business_id,operate_type,operator_ucid,operator_name,operator_ip,
     *   operation_reason,created_time,brand,log_content FROM sh_housedel_log
     *   WHERE id = ?
     *
     * @param pk     主键
     * @return T 实体
     */
    T selectByPk(PK pk);

    /**
     * 根据主键集合查询数据
     *
     * 注意：
     *   1.实体类中只能存在一个带有 @Id 注解的字段
     *   2.pks 不能为 null 且不能为空
     *
     * SQL 示例:
     *   SELECT id,log_type,business_id,operate_type,operator_ucid,operator_name,operator_ip,
     *   operation_reason,created_time,brand,log_content FROM sh_housedel_log
     *   WHERE id IN (?,?,?)
     *
     * @param pks    主键集合
     * @return List<T> 实体集合
     */
    List<T> selectByPks(Iterable<? extends PK> pks);

    /**
     * 根据 `=` 条件查询数据集合
     *
     * SQL 示例:
     *   SELECT id,log_type,business_id,operate_type,operator_ucid,operator_name,operator_ip,
     *   operation_reason,created_time,brand,log_content FROM sh_housedel_log
     *   WHERE business_id = ?
     *
     * @param param  查询条件
     * @return List<T> 实体集合
     */
    List<T> select(T param);

    /**
     * 查询全部数据
     *
     * SQL 示例:
     *   SELECT id,log_type,business_id,operate_type,operator_ucid,operator_name,operator_ip,
     *   operation_reason,created_time,brand,log_content FROM sh_housedel_log
     *
     * @return List<T> 实体集合
     */
    List<T> selectAll();

    /**
     * 根据 Example 条件查询数据集合
     *
     * SQL 示例:
     *   SELECT id,log_type,business_id,operate_type,operator_ucid,operator_name,operator_ip,
     *   operation_reason,created_time,brand,log_content FROM sh_housedel_log
     *   WHERE business_id IN (?,?,?)
     *
     * @param example 查询条件
     * @return List<T> 实体集合
     */
    List<T> selectByExample(Example example);

    /**
     * 根据 `=` 条件查询单条数据
     *
     * 注意：
     *   1.该 SQL 只能有一个返回值，否则会抛出异常
     *
     * SQL 示例:
     *   SELECT id,log_type,business_id,operate_type,operator_ucid,operator_name,operator_ip,
     *   operation_reason,created_time,brand,log_content FROM sh_housedel_log
     *   WHERE business_id = ?
     *
     * @param param  查询条件
     * @return T 实体
     */
    T selectOne(T param);

    /**
     * 根据 `=` 条件查询单条数据
     *
     * 注意：
     *   1.该 SQL 会取第一条数据返回
     *
     * SQL 示例:
     *   SELECT id,log_type,business_id,operate_type,operator_ucid,operator_name,operator_ip,
     *   operation_reason,created_time,brand,log_content FROM sh_housedel_log
     *   WHERE business_id = ? LIMIT 1
     *
     * @param param  查询条件
     * @return T 实体
     */
    T selectLimitOne(T param);

    /**
     * 根据 Example 条件查询单条数据
     *
     * 注意：
     *   1.该 SQL 只能有一个返回值，否则会抛出异常
     *
     * SQL 示例:
     *   SELECT id,log_type,business_id,operate_type,operator_ucid,operator_name,operator_ip,
     *   operation_reason,created_time,brand,log_content FROM sh_housedel_log
     *   WHERE business_id IN (?)
     *
     * @param example 查询条件
     * @return T 实体
     */
    T selectOneByExample(Example example);

    /**
     * 根据 Example 条件查询单条数据
     *
     * 注意：
     *   1.该 SQL 只能有一个返回值，否则会抛出异常
     *
     * SQL 示例:
     *   SELECT id,log_type,business_id,operate_type,operator_ucid,operator_name,operator_ip,
     *   operation_reason,created_time,brand,log_content FROM sh_housedel_log
     *   WHERE business_id IN (?) LIMIT 1
     *
     * @param example 查询条件
     * @return T 实体
     */
    T selectLimitOneByExample(Example example);

    /**
     * 根据 `=` 条件查询数据数量
     *
     * SQL 示例:
     *   SELECT COUNT(id) FROM sh_housedel_log WHERE business_id = ?
     *
     * @param param  查询条件
     * @return int 数据量
     */
    long selectCount(T param);

    /**
     * 根据 Example 条件查询数据数量
     *
     * SQL 示例:
     *   SELECT COUNT(id) FROM sh_housedel_log WHERE business_id IN (?,?,?)
     *
     * @param example 查询条件
     * @return int 数据量
     */
    long selectCountByExample(Example example);

    /**
     * 根据 `=` 条件分页查询，不会查询 count
     *
     * SQL 示例:
     *   SELECT id,log_type,business_id,operate_type,operator_ucid,operator_name,operator_ip,
     *   operation_reason,created_time,brand,log_content FROM sh_housedel_log
     *   WHERE business_id = ? LIMIT ?,?
     *
     * @param param    查询条件
     * @return List<T> 分页实体
     */
    List<T> selectPage(T param);

    /**
     * 根据 Example 条件分页查询，不会查询 count
     *
     * SQL 示例:
     *   SELECT id,log_type,business_id,operate_type,operator_ucid,operator_name,operator_ip,
     *   operation_reason,created_time,brand,log_content FROM sh_housedel_log
     *   WHERE business_id IN (?,?,?) LIMIT ?,?
     *
     * @param example  查询条件
     * @param pageNum  分页页码
     * @param pageSize 分页数量
     * @return List<T> 分页实体
     */
    List<T> selectPageByExample(Example example, int pageNum, int pageSize);

    /**
     * 根据 `=` 条件分页查询，同时查询 count
     * 若同时需要排序，可手动指定PageHelper.orderBy()
     *
     * SQL 示例:
     *   SELECT id,log_type,business_id,operate_type,operator_ucid,operator_name,operator_ip,
     *   operation_reason,created_time,brand,log_content FROM sh_housedel_log
     *   WHERE business_id = ? LIMIT ?,?
     *   SELECT COUNT(id) FROM sh_housedel_log WHERE business_id = ?
     *
     * @param param    查询条件
     * @return PageInfo<T> 分页实体
     */
    PageInfo<T> selectPageAndCount(T param);

    /**
     * 根据 Example 条件分页查询，同时查询 count
     *
     * SQL 示例:
     *   SELECT id,log_type,business_id,operate_type,operator_ucid,operator_name,operator_ip,
     *   operation_reason,created_time,brand,log_content FROM sh_housedel_log
     *   WHERE business_id IN (?,?,?) LIMIT ?,?
     *   SELECT COUNT(id) FROM sh_housedel_log WHERE business_id IN (?,?,?)
     *
     * @param example  查询条件
     * @param pageNum  分页页码
     * @param pageSize 分页数量
     * @return PageInfo<T> 分页实体
     */
    PageInfo<T> selectPageAndCountByExample(Example example, int pageNum, int pageSize);

}