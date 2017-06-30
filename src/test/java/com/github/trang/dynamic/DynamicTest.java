package com.github.trang.dynamic;

import com.github.trang.dynamic.domain.model.BaseCode;
import com.github.trang.dynamic.domain.model.Copy;
import com.github.trang.dynamic.service.CopyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author trang
 */
@Slf4j
public class DynamicTest extends SpringBootBaseTest {

    @Autowired
    private CopyService copyService;

    @Test
    public void test() {
        baseCodeService.update(new BaseCode().id(1L).parentCode("T"));
        copyService.update(new Copy().id(1L).parentCode("T"));
    }

    @Test
    public void test2() {
        baseCodeService.update(new BaseCode().id(1L).parentCode("T"));
        copyService.update(null);
    }

}