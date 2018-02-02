package com.github.trang.dynamic.controller;

import com.github.trang.copiers.Copiers;
import com.github.trang.copiers.inter.Copier;
import com.github.trang.dynamic.base.response.Message;
import com.github.trang.dynamic.domain.enums.EnumBaseCode;
import com.github.trang.dynamic.domain.model.BaseCode;
import com.github.trang.dynamic.dynamic.DynamicDataSourceHolder;
import com.github.trang.dynamic.service.BaseCodeService;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * BaseCode 控制器
 *
 * @author trang
 */
@RestController
@RequestMapping("/base-code")
public class BaseCodeController {

    private static final Copier<EnumBaseCode, BaseCode> COPIER =
            Copiers.createMapper(EnumBaseCode.class, BaseCode.class)
                    .field("code", "codeType")
                    .field("desc", "codeValue")
                    .register();

    @Autowired
    private BaseCodeService baseCodeService;

    @GetMapping("/list")
    public Message<List<BaseCode>> list() {
        List<BaseCode> baseCodes = StreamEx.of(EnumBaseCode.values()).map(COPIER::copy).toList();
        return Message.ok(baseCodes);
    }

    /**
     * 没有事务，默认走从库
     */
    @GetMapping("/get/slave/{code}/{officeAddress}")
    public Message<List<BaseCode>> listSlave(@PathVariable String code, @PathVariable Integer officeAddress) {
        EnumBaseCode type = EnumBaseCode.getByCode(code);
        List<BaseCode> baseCodes = baseCodeService.getListByCity(type, officeAddress);
        return Message.ok(baseCodes);
    }

    /**
     * 没有事务，手动指定数据源使用主库
     */
    @GetMapping("/get/master/{code}/{officeAddress}")
    public Message<List<BaseCode>> listMaster(@PathVariable String code, @PathVariable Integer officeAddress) {
        DynamicDataSourceHolder.routeMaster();
        EnumBaseCode type = EnumBaseCode.getByCode(code);
        List<BaseCode> baseCodes = baseCodeService.getListByCity(type, officeAddress);
        return Message.ok(baseCodes);
    }

    /**
     * 有事务，默认 realOnly=false，走主库
     */
    @GetMapping("/get/transaction/master/{code}/{officeAddress}")
    @Transactional(rollbackFor = Throwable.class)
    public Message<List<BaseCode>> listTransactionMaster(@PathVariable String code, @PathVariable Integer officeAddress) {
        EnumBaseCode type = EnumBaseCode.getByCode(code);
        List<BaseCode> baseCodes = baseCodeService.getListByCity(type, officeAddress);
        return Message.ok(baseCodes);
    }

    /**
     * 有事务，指定 realOnly=true，走从库
     */
    @GetMapping("/get/transaction/slave/{code}/{officeAddress}")
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public Message<List<BaseCode>> listTransactionSlave(@PathVariable String code, @PathVariable Integer officeAddress) {
        EnumBaseCode type = EnumBaseCode.getByCode(code);
        List<BaseCode> baseCodes = baseCodeService.getListByCity(type, officeAddress);
        return Message.ok(baseCodes);
    }

}