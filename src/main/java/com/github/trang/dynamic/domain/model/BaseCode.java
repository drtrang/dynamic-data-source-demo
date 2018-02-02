package com.github.trang.dynamic.domain.model;

import com.github.trang.dynamic.base.model.BaseModel;
import com.google.common.base.MoreObjects;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * m_base_code 表数据模型
 *
 * @author mbg
 * @mbg.generated
 * @since 2018-2-1
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@Table(name = "m_base_code")
public class BaseCode extends BaseModel<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     *
     * @mbg.generated
     * @since 2018-2-1
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;


    /**
     * 编码类型
     *
     * @mbg.generated
     * @since 2018-2-1
     */
    @Column(name = "code_type")
    private String codeType;

    /**
     * 父编码
     *
     * @mbg.generated
     * @since 2018-2-1
     */
    @Column(name = "parent_code")
    private String parentCode;

    /**
     * 编码
     *
     * @mbg.generated
     * @since 2018-2-1
     */
    @Column(name = "code")
    private String code;

    /**
     * 编码值
     *
     * @mbg.generated
     * @since 2018-2-1
     */
    @Column(name = "code_value")
    private String codeValue;

    /**
     * 排序
     *
     * @mbg.generated
     * @since 2018-2-1
     */
    @Column(name = "code_sort")
    private Integer codeSort;

    /**
     * 城市编码
     *
     * @mbg.generated
     * @since 2018-2-1
     */
    @Column(name = "office_address")
    private Integer officeAddress;

    /**
     * 备注
     *
     * @mbg.generated
     * @since 2018-2-1
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建时间
     *
     * @mbg.generated
     * @since 2018-2-1
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbg.generated
     * @since 2018-2-1
     */
    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    private List<BaseCode> subList;

    @Override
    @Transient
    public Long getPk() {
        return id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("id", id)
                .add("codeType", codeType)
                .add("parentCode", parentCode)
                .add("code", code)
                .add("codeValue", codeValue)
                .add("codeSort", codeSort)
                .add("officeAddress", officeAddress)
                .add("remark", remark)
                .add("createTime", createTime)
                .add("updateTime", updateTime)
                .add("subList", subList)
                .add("super", super.toString())
                .toString();
    }

}
