package com.github.trang.dynamic.domain.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.google.common.base.MoreObjects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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

    /**
     * 下一级的code集合
     */
    @Transient
    private java.util.List<BaseCode> subList;


    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType == null ? null : codeType.trim();
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue == null ? null : codeValue.trim();
    }

    public Integer getCodeSort() {
        return codeSort;
    }

    public void setCodeSort(Integer codeSort) {
        this.codeSort = codeSort;
    }

    public Integer getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(Integer officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<BaseCode> getSubList() {
        return subList;
    }

    public void setSubList(List<BaseCode> subList) {
        this.subList = subList;
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
        .toString();
    }
}