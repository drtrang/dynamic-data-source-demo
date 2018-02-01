package com.github.trang.dynamic.base.model;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * BaseModel，基础 Model 类，所有的数据库模型都必须继承本类
 *
 * @author trang
 */
@Getter
@Setter
public abstract class BaseModel<PK extends Serializable> {

    /**
     * 页码
     */
    @Transient
    private Integer page;

    /**
     * 分页数量
     */
    @Transient
    private Integer pageSize;

    /**
     * 排序字段
     */
    @Transient
    private String sortBy;

    /**
     * 排序方式 ASC | DESC
     */
    @Transient
    private String order;

    /**
     * 排序方式，包括 sortBy 和 order
     */
    @Transient
    private String orderByClause;

    /**
     * 指定需要查询的列
     */
    @Transient
    private String fields;

    /**
     * 获取主键
     *
     * @return pk 主键
     */
    public abstract PK getPk();

    @Override
    public String toString() {
        String s = MoreObjects.toStringHelper("BaseModel").omitNullValues()
                .add("page", page)
                .add("pageSize", pageSize)
                .add("sortBy", sortBy)
                .add("order", order)
                .add("fields", fields)
                .toString();
        return "BaseModel{}".equals(s) ? null : s;
    }

}