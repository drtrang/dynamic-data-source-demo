package com.github.trang.dynamic.service.impl;

import com.github.trang.dynamic.domain.enums.EnumBaseCode;
import com.github.trang.dynamic.domain.model.BaseCode;
import com.github.trang.dynamic.mapper.BaseCodeMapper;
import com.github.trang.dynamic.service.BaseCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

@Service("baseCodeService")
public class BaseCodeServiceImpl extends BaseServiceImpl<BaseCode, Long> implements BaseCodeService {

    @Autowired
    private BaseCodeMapper mapper;

    private static Optional<List<BaseCode>> checkList(List<BaseCode> all, Integer officeAddress) {
        if (CollectionUtils.isEmpty(all)) {
            return Optional.empty();
        }
        //将查询结果按照 office-address 分组，office-address 不能为 null，默认值为 0
        Map<Integer, List<BaseCode>> map = all.stream().collect(groupingBy(BaseCode::getOfficeAddress));
        //查找当前 office-address 的 value，若没有，则返回默认值，0 为默认值，若没有默认值则返回 Optional.empty()
        return map.containsKey(officeAddress) ? Optional.of(map.get(officeAddress)) : Optional.ofNullable(map.get(0));
    }

    @Override
    public Optional<List<BaseCode>> getListByCity(EnumBaseCode type, Integer officeAddress) {
        BaseCode selectParam = new BaseCode();
        selectParam.setCodeType(type.getCode());
        List<BaseCode> all = mapper.select(selectParam);
        return checkList(all, officeAddress);
    }

    @Override
    @Transactional
    public Optional<List<BaseCode>> getListClassifyByCity(EnumBaseCode type, Integer officeAddress) {
        BaseCode selectParam = new BaseCode();
        selectParam.setCodeType(type.getCode());
        List<BaseCode> all = mapper.select(selectParam);
        //将查询结果按照 parent-code 分组
        Map<String, List<BaseCode>> map = all.stream().collect(groupingBy(BaseCode::getParentCode));
        //获取一级 base-code
        List<BaseCode> parents = map.get("0");
        //遍历一级 base-code 并将其对应的二级 base-code 设置进去
        parents.forEach(parent -> parent.setSubList(map.get(parent.getCode())));
        return checkList(parents, officeAddress);
    }

    @Override
    public Optional<BaseCode> getOneByCity(EnumBaseCode type, Integer officeAddress) {
        BaseCode selectParam = new BaseCode();
        selectParam.setCodeType(type.getCode());
        List<BaseCode> all = mapper.select(selectParam);
        Optional<List<BaseCode>> optional = checkList(all, officeAddress);
        return optional.map(list -> list.get(0));
    }

}