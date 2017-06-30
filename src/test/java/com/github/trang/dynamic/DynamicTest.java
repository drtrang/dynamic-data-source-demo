package com.github.trang.dynamic;

import com.github.trang.dynamic.domain.model.BaseCode;
import com.github.trang.dynamic.dynamic.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author trang
 */
@Slf4j
public class DynamicTest extends SpringBootBaseTest {

    @Test
    public void test() {
        DynamicDataSourceHolder.routeMaster();
        baseCodeService.update(new BaseCode().id(1L).parentCode("T"));
        DynamicDataSourceHolder.routeSlave();
        baseCodeService.update(new BaseCode().id(1L).parentCode("T"));
    }
}
