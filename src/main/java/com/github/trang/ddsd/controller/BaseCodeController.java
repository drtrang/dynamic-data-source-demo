package com.github.trang.ddsd.controller;

import com.github.trang.copiers.Copiers;
import com.github.trang.copiers.inter.Copier;
import com.github.trang.ddsd.domain.enums.EnumBaseCode;
import com.github.trang.ddsd.domain.model.BaseCode;
import com.github.trang.ddsd.dynamic.DynamicDataSourceHolder;
import com.github.trang.ddsd.service.BaseCodeService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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
    
    private Copier<EnumBaseCode, BaseCode> copier = COPIER;
    
    @Autowired
    private BaseCodeService baseCodeService;

    @GetMapping("/list")
    public ResponseEntity<List<BaseCode>> list() {
        return ResponseEntity.ok(Arrays.stream(EnumBaseCode.values()).map(copier::copy).collect(toList()));
    }

    /**
     * 没有事务，默认走从库，手动指定数据源使用主库
     */
    @GetMapping("/get/{master}/{code}/{officeAddress}")
    public ResponseEntity<List<BaseCode>> list(@PathVariable Boolean master,
                                               @PathVariable String code,
                                               @PathVariable Integer officeAddress) {
        if (master) DynamicDataSourceHolder.routeMaster();
        EnumBaseCode type = EnumBaseCode.getByCode(code);
        Preconditions.checkNotNull(type, "未找到符合的类型: " + code);
        Optional<List<BaseCode>> optional = baseCodeService.getListByCity(type, officeAddress);
        return ResponseEntity.ok(optional.orElseThrow(IllegalArgumentException::new));
    }

    /**
     * 有事务，默认realOnly=false，走主库
     */
    @GetMapping("/get/transaction/master/{code}/{officeAddress}")
    @Transactional
    public ResponseEntity<List<BaseCode>> listTransactionMaster(@PathVariable String code,
                                                                @PathVariable Integer officeAddress) {
        EnumBaseCode type = EnumBaseCode.getByCode(code);
        Preconditions.checkNotNull(type, "未找到符合的类型:" + code);
        Optional<List<BaseCode>> optional = baseCodeService.getListByCity(type, officeAddress);
        return ResponseEntity.ok(optional.orElseThrow(IllegalArgumentException::new));
    }

    /**
     * 有事务，指定realOnly=true，走从库
     */
    @GetMapping("/get/transaction/slave/{code}/{officeAddress}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<BaseCode>> listTransactionSlave(@PathVariable String code,
                                                               @PathVariable Integer officeAddress) {
        EnumBaseCode type = EnumBaseCode.getByCode(code);
        Preconditions.checkNotNull(type, "未找到符合的类型:" + code);
        Optional<List<BaseCode>> optional = baseCodeService.getListByCity(type, officeAddress);
        return ResponseEntity.ok(optional.orElseThrow(IllegalArgumentException::new));
    }
}
