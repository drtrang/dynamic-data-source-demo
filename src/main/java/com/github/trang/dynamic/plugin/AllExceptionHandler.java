package com.github.trang.dynamic.plugin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.common.collect.Maps;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 *
 * @author trang
 */
@RestControllerAdvice
@Slf4j
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(HttpServletRequest request, Exception e) {
        printStackTrace(request, e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private void printStackTrace(HttpServletRequest request, Throwable e) {
        Map<String, String[]> params = request.getParameterMap();
        Map<String, Object> map = Maps.transformValues(
                params, arr -> (arr != null ? arr.length : 0) == 0 ? null : arr.length == 1 ? arr[0] : arr
        );
        log.error("handle:{}, params:{}", e.getClass().getName(), new Gson().toJson(map), e);
    }
}
