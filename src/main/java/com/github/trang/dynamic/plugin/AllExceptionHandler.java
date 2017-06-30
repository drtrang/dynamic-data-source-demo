package com.github.trang.dynamic.plugin;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 全局异常处理
 *
 * @author trang
 */
@RestControllerAdvice
@Slf4j
public class AllExceptionHandler {

    @Autowired
    private Gson gson;

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
        log.error("handle:{}, params:{}", e.getClass().getName(), gson.toJson(map), e);
    }

}