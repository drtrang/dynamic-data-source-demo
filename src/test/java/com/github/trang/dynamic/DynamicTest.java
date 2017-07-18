package com.github.trang.dynamic;

import com.github.trang.dynamic.domain.model.BaseCode;
import com.github.trang.dynamic.domain.model.BaseCode.BaseCodeBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author trang
 */
@Slf4j
public class DynamicTest extends SpringBootBaseTest {

    @Test
    public void test() {
        BaseCodeBuilder builder = BaseCode.builder();
        baseCodeService.update(builder.id(1L).parentCode("T").build());
    }

    @Test
    @Transactional
    public void test2() {
        BaseCodeBuilder builder = BaseCode.builder();
        baseCodeService.update(builder.id(1L).parentCode("T").build());
    }

}