package com.github.trang.ddsd;

import com.github.trang.ddsd.domain.model.BaseCode;
import com.github.trang.ddsd.service.BaseCodeService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.github.trang.ddsd.domain.enums.EnumBaseCode.DROP_REASON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Slf4j
public class DynamicDataSourceDemoApplicationTests {
    @Autowired
    private BaseCodeService baseCodeService;

    @Test
    public void defaultTest() {
        Optional<List<BaseCode>> optional = baseCodeService.getListByCity(DROP_REASON, 0);
        optional.orElse(Collections.emptyList()).stream().map(new Gson()::toJson).forEach(log::info);
    }

    @Test
    @Transactional
    public void transactionalTest() {
        Optional<List<BaseCode>> optional = baseCodeService.getListByCity(DROP_REASON, 0);
        optional.orElse(Collections.emptyList()).stream().map(new Gson()::toJson).forEach(log::info);
    }
}
