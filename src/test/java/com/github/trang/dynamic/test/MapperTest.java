package com.github.trang.dynamic.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.trang.dynamic.domain.model.BaseCode;
import com.github.trang.dynamic.mapper.BaseCodeMapper;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class MapperTest extends SpringBootBaseTest {

    @Autowired
    private BaseCodeMapper baseCodeMapper;

    @Test
    public void select() {
        List<BaseCode> all = baseCodeMapper.selectAll();
        StreamEx.of(all).map(gson::toJson).forEach(log::info);
    }

    @Test
    public void page() {
        PageInfo<BaseCode> page = PageHelper.startPage(1, 5).doSelectPageInfo(
                () -> baseCodeMapper.selectAll()
        );
        StreamEx.of(page).map(gson::toJson).forEach(log::info);
        StreamEx.of(page).map(PageInfo::getList).flatMap(List::stream).map(gson::toJson).forEach(log::info);
    }

}