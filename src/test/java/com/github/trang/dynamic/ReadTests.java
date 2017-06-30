package com.github.trang.dynamic;

import com.github.trang.dynamic.domain.model.BaseCode;
import com.github.trang.dynamic.dynamic.DynamicDataSourceHolder;
import com.github.trang.dynamic.service.BaseCodeService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.github.trang.dynamic.domain.enums.EnumBaseCode.DROP_REASON;
import static java.util.Collections.emptyList;

@Slf4j
public class ReadTests extends SpringBootBaseTest {

    @Autowired
    private BaseCodeService baseCodeService;
    @Autowired
    private Gson gson;

    @Test
    @Transactional
    public void nonTransactionMaster() {
        DynamicDataSourceHolder.route1();
        Optional<List<BaseCode>> optional0 = baseCodeService.getListByCity(DROP_REASON, 0);
        optional0.orElse(emptyList()).stream().map(new Gson()::toJson).forEach(log::info);
        DynamicDataSourceHolder.route2();
        Optional<List<BaseCode>> optional1 = baseCodeService.getListByCity(DROP_REASON, 0);
        optional1.orElse(emptyList()).stream().map(new Gson()::toJson).forEach(log::info);
    }

    @Test
    public void nonTransactionSlave() {
        Optional<List<BaseCode>> optional = baseCodeService.getListByCity(DROP_REASON, 0);
        optional.orElse(emptyList()).stream().map(gson::toJson).forEach(log::info);
    }

    @Test
    @Transactional
    public void transactionalMaster() {
        Optional<List<BaseCode>> optional = baseCodeService.getListByCity(DROP_REASON, 0);
        optional.orElse(emptyList()).stream().map(gson::toJson).forEach(log::info);
    }

    @Test
    @Transactional(readOnly = true)
    public void transactionalSlave() {
        Optional<List<BaseCode>> optional = baseCodeService.getListByCity(DROP_REASON, 0);
        optional.orElse(emptyList()).stream().map(gson::toJson).forEach(log::info);
    }

}