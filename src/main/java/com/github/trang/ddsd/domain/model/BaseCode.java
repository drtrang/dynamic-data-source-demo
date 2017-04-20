package com.github.trang.ddsd.domain.model;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取编码类型
     *
     * @return code_type - 编码类型
     */
    public String getCodeType() {
        return codeType;
    }

    /**
     * 设置编码类型
     *
     * @param codeType 编码类型
     */
    public void setCodeType(String codeType) {
        this.codeType = codeType == null ? null : codeType.trim();
    }

    /**
     * 获取父编码
     *
     * @return parent_code - 父编码
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * 设置父编码
     *
     * @param parentCode 父编码
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    /**
     * 获取编码
     *
     * @return code - 编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编码
     *
     * @param code 编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取编码值
     *
     * @return code_value - 编码值
     */
    public String getCodeValue() {
        return codeValue;
    }

    /**
     * 设置编码值
     *
     * @param codeValue 编码值
     */
    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue == null ? null : codeValue.trim();
    }

    /**
     * 获取排序
     *
     * @return code_sort - 排序
     */
    public Integer getCodeSort() {
        return codeSort;
    }

    /**
     * 设置排序
     *
     * @param codeSort 排序
     */
    public void setCodeSort(Integer codeSort) {
        this.codeSort = codeSort;
    }

    /**
     * 获取城市编码
     *
     * @return office_address - 城市编码
     */
    public Integer getOfficeAddress() {
        return officeAddress;
    }

    /**
     * 设置城市编码
     *
     * @param officeAddress 城市编码
     */
    public void setOfficeAddress(Integer officeAddress) {
        this.officeAddress = officeAddress;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
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