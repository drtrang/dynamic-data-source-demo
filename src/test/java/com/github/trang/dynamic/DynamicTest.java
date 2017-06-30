package com.github.trang.dynamic;

import com.github.trang.dynamic.domain.model.BaseCode;
import com.github.trang.dynamic.domain.model.Copy;
import com.github.trang.dynamic.dynamic.DynamicDataSourceHolder;
import com.github.trang.dynamic.service.CopyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author trang
 */
@Slf4j
public class DynamicTest extends SpringBootBaseTest {

    @Test
    public void update() {
        DynamicDataSourceHolder.route1();
        baseCodeService.update(new BaseCode().id(1L).parentCode("T"));
        DynamicDataSourceHolder.route2();
        baseCodeService.update(new BaseCode().id(1L).parentCode("T"));
    }

    @Test
    @Transactional
    public void updateTransactional() {
        DynamicDataSourceHolder.route1();
        baseCodeService.update(new BaseCode().id(1L).parentCode("T"));
        DynamicDataSourceHolder.route2();
        baseCodeService.update(null);
    }

    @Autowired
    private CopyService copyService;

    @Test
    public void test() {
        baseCodeService.update(new BaseCode().id(1L).parentCode("T"));
        copyService.update(new Copy().id(1L).parentCode("T"));
    }

    @Test
    @Transactional
    public void test2() {
        baseCodeService.update(new BaseCode().id(1L).parentCode("T"));
        copyService.update(null);
    }
}