package com.github.trang.dynamic.service;

import com.github.trang.dynamic.base.service.BaseService;
import com.github.trang.dynamic.domain.enums.EnumBaseCode;
import com.github.trang.dynamic.domain.model.BaseCode;

import java.util.List;

/**
 * BaseCodeService
 *
 * @author mbg
 * @mbg.generated
 * @since 2018-2-1
 */
public interface BaseCodeService extends BaseService<BaseCode, Long> {

    /**
     * 获取列表
     *
     * @param type          type
     * @param officeAddress officeAddress
     * @return list
     */
    List<BaseCode> getListByCity(EnumBaseCode type, Integer officeAddress);

    /**
     * 分级别的返回 list，填充 sublist
     * 目前仅支持两级
     *
     * @param type          type
     * @param officeAddress officeAddress
     * @return list
     */
    List<BaseCode> getListClassifyByCity(EnumBaseCode type, Integer officeAddress);

    /**
     * 获取具体的 basecode
     *
     * @param type          type
     * @param officeAddress officeAddress
     * @return list
     */
    BaseCode getOneByCity(EnumBaseCode type, Integer officeAddress);

}