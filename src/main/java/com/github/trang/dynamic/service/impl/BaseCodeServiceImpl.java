package com.github.trang.dynamic.service.impl;

import com.github.trang.dynamic.base.service.impl.BaseServiceImpl;
import com.github.trang.dynamic.domain.enums.EnumBaseCode;
import com.github.trang.dynamic.domain.model.BaseCode;
import com.github.trang.dynamic.mapper.BaseCodeMapper;
import com.github.trang.dynamic.service.BaseCodeService;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * BaseCodeServiceImpl
 *
 * @author mbg
 * @mbg.generated
 * @since 2018-2-1
 */
@Service("baseCodeService")
public class BaseCodeServiceImpl extends BaseServiceImpl<BaseCode, Long> implements BaseCodeService {

    @Autowired
    private BaseCodeMapper baseCodeMapper;

    @Override
    public List<BaseCode> getListByCity(EnumBaseCode type, Integer officeAddress) {
        BaseCode param = BaseCode.builder()
                .codeType(type.getCode())
                .build();
        List<BaseCode> all = baseCodeMapper.select(param);
        return checkList(all, officeAddress);
    }

    @Override
    public List<BaseCode> getListClassifyByCity(EnumBaseCode type, Integer officeAddress) {
        BaseCode param = BaseCode.builder()
                .codeType(type.getCode())
                .build();
        List<BaseCode> all = baseCodeMapper.select(param);
        // 将查询结果按照 parent-code 分组
        Map<String, List<BaseCode>> map = StreamEx.of(all).groupingBy(BaseCode::getParentCode);
        // 获取一级 base-code
        List<BaseCode> parents = map.get("0");
        // 遍历一级 base-code 并将其对应的二级 base-code 设置进去
        parents.forEach(parent -> parent.setSubList(map.get(parent.getCode())));
        return checkList(parents, officeAddress);
    }

    @Override
    public BaseCode getOneByCity(EnumBaseCode type, Integer officeAddress) {
        BaseCode param = BaseCode.builder()
                .codeType(type.getCode())
                .officeAddress(officeAddress)
                .build();
        return baseCodeMapper.selectOne(param);
    }

    private static List<BaseCode> checkList(List<BaseCode> all, Integer officeAddress) {
        if (isEmpty(all)) {
            return emptyList();
        }
        // 将查询结果按照 office-address 分组，office-address 不能为 null，默认值为 0
        Map<Integer, List<BaseCode>> map = StreamEx.of(all).groupingBy(BaseCode::getOfficeAddress);
        // 查找当前 office-address 的 value，若没有，则返回默认值，0 为默认值，若没有默认值则返回 Optional.empty()
        return map.containsKey(officeAddress) ? map.get(officeAddress) : map.get(0);
    }

}