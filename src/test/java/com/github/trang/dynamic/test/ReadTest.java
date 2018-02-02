package com.github.trang.dynamic.test;

import com.github.trang.dynamic.domain.model.BaseCode;
import com.github.trang.dynamic.dynamic.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.github.trang.dynamic.domain.enums.EnumBaseCode.DROP_REASON;

@Slf4j
public class ReadTest extends SpringBootBaseTest {

    @Test
    public void nonTransactionMaster() {
        DynamicDataSourceHolder.routeMaster();
        List<BaseCode> baseCodes = baseCodeService.getListByCity(DROP_REASON, 0);
        StreamEx.of(baseCodes).map(gson::toJson).forEach(log::info);
    }

    @Test
    public void nonTransactionSlave() {
        List<BaseCode> baseCodes = baseCodeService.getListByCity(DROP_REASON, 0);
        StreamEx.of(baseCodes).map(gson::toJson).forEach(log::info);
    }

    @Test
    public void multi() {
        DynamicDataSourceHolder.routeMaster();
        List<BaseCode> master = baseCodeService.getListByCity(DROP_REASON, 0);
        StreamEx.of(master).map(gson::toJson).forEach(log::info);
        DynamicDataSourceHolder.routeSlave();
        List<BaseCode> slave = baseCodeService.getListByCity(DROP_REASON, 0);
        StreamEx.of(slave).map(gson::toJson).forEach(log::info);
    }

    @Test
    @Transactional
    public void transactionalMaster() {
        List<BaseCode> baseCodes = baseCodeService.getListByCity(DROP_REASON, 0);
        StreamEx.of(baseCodes).map(gson::toJson).forEach(log::info);
    }

    @Test
    @Transactional(readOnly = true)
    public void transactionalSlave() {
        List<BaseCode> baseCodes = baseCodeService.getListByCity(DROP_REASON, 0);
        StreamEx.of(baseCodes).map(gson::toJson).forEach(log::info);
    }

}