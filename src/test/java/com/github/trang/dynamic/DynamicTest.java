package com.github.trang.dynamic;

import com.github.trang.dynamic.domain.model.BaseCode;
import com.github.trang.dynamic.dynamic.DynamicDataSourceHolder;
import com.github.trang.dynamic.service.BaseCodeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author trang
 */
public class DynamicTest extends SpringBootBaseTest {

    @Autowired
    private BaseCodeService baseCodeService;

    @Test
    @Transactional
    @Rollback(false)
    public void test() {
        baseCodeService.update(new BaseCode().id(1L).parentCode("T"));
        DynamicDataSourceHolder.routeSlave();
        baseCodeService.update(new BaseCode().id(1L).parentCode("T"));
    }

}