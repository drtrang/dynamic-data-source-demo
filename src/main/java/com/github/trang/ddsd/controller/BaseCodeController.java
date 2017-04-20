package com.github.trang.ddsd.controller;

import com.github.trang.copiers.Copiers;
import com.github.trang.copiers.inter.Copier;
import com.github.trang.ddsd.domain.enums.EnumBaseCode;
import com.github.trang.ddsd.domain.model.BaseCode;
import com.github.trang.ddsd.service.BaseCodeService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private BaseCodeService baseCodeService;

    private static final Copier<EnumBaseCode, BaseCode> copier =
            Copiers.createMapper(EnumBaseCode.class, BaseCode.class)
                    .field("code", "codeType")
                    .field("desc", "codeValue")
                    .register();

    @GetMapping("/list")
    public ResponseEntity<List<BaseCode>> list() {
        return ResponseEntity.ok(
                Arrays.stream(EnumBaseCode.values()).map(copier::copy).collect(toList())
        );
    }

    /**
     * 没有事务，默认走从库
     */
    @GetMapping("/get/{code}/{officeAddress}")
    public ResponseEntity<List<BaseCode>> list(@PathVariable String code, @PathVariable Integer
            officeAddress) {
        EnumBaseCode type = EnumBaseCode.getByCode(code);
        Preconditions.checkNotNull(type, "未找到符合的类型:" + code);
        Optional<List<BaseCode>> optional = baseCodeService.getListByCity(type, officeAddress);
        return ResponseEntity.ok(optional.orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/get/transaction/{code}/{officeAddress}")
    @Transactional
    public ResponseEntity<List<BaseCode>> listTransaction(@PathVariable String code, @PathVariable Integer
            officeAddress) {
        EnumBaseCode type = EnumBaseCode.getByCode(code);
        Preconditions.checkNotNull(type, "未找到符合的类型:" + code);
        Optional<List<BaseCode>> optional = baseCodeService.getListByCity(type, officeAddress);
        return ResponseEntity.ok(optional.orElseThrow(IllegalArgumentException::new));
    }

    @PostMapping("/add")
    public ResponseEntity<Integer> add(BaseCode baseCode) {
        return ResponseEntity.ok(baseCodeService.insert(baseCode));
    }

    @PostMapping("/put")
    public ResponseEntity<Integer> put(BaseCode baseCode) {
        Preconditions.checkNotNull(baseCode.getId(), "主键不能为空");
        return ResponseEntity.ok(baseCodeService.update(baseCode));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Long id) {
        return ResponseEntity.ok(baseCodeService.deleteByPk(id));
    }
}
