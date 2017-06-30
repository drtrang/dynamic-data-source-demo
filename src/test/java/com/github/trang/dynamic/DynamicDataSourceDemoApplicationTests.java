package com.github.trang.dynamic;

import static com.github.trang.dynamic.domain.enums.EnumBaseCode.DROP_REASON;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.trang.dynamic.domain.model.BaseCode;
import com.github.trang.dynamic.service.BaseCodeService;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DynamicDataSourceDemoApplicationTests {

    @Autowired
    private BaseCodeService baseCodeService;
    @Autowired
    private Gson gson;

    @Test
    public void nonTransactionMaster() {
        Optional<List<BaseCode>> optional = baseCodeService.getListByCity(DROP_REASON, 0);
        optional.orElse(Collections.emptyList()).stream().map(gson::toJson).forEach(log::info);
    }

    @Test
    public void nonTransactionSlave() {
        Optional<List<BaseCode>> optional = baseCodeService.getListByCity(DROP_REASON, 0);
        optional.orElse(Collections.emptyList()).stream().map(gson::toJson).forEach(log::info);
    }

    @Test
    @Transactional
    public void transactionalMaster() {
        Optional<List<BaseCode>> optional = baseCodeService.getListByCity(DROP_REASON, 0);
        optional.orElse(Collections.emptyList()).stream().map(gson::toJson).forEach(log::info);
    }

    @Test
    @Transactional(readOnly = true)
    public void transactionalSlave() {
        Optional<List<BaseCode>> optional = baseCodeService.getListByCity(DROP_REASON, 0);
        optional.orElse(Collections.emptyList()).stream().map(gson::toJson).forEach(log::info);
    }
}
