package com.github.trang.dynamic.domain.model;

import com.google.common.base.MoreObjects;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@Table(name = "m_base_code")
public class BaseCode implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 编码类型
     */
    @Column(name = "code_type")
    private String codeType;

    /**
     * 父编码
     */
    @Column(name = "parent_code")
    private String parentCode;

    /**
     * 编码
     */
    private String code;

    /**
     * 编码值
     */
    @Column(name = "code_value")
    private String codeValue;

    /**
     * 排序
     */
    @Column(name = "code_sort")
    private Integer codeSort;

    /**
     * 城市编码
     */
    @Column(name = "office_address")
    private Integer officeAddress;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    private List<BaseCode> subList;

    private static final long serialVersionUID = 1L;

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
                .toString();
    }

}