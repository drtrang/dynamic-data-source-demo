package com.github.trang.dynamic.plugin;

import com.github.trang.dynamic.base.response.Message;
import com.github.trang.dynamic.domain.enums.EnumRst;
import com.github.trang.dynamic.exception.DynamicException;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 *
 * @author trang
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private Gson gson;

    @ExceptionHandler(DynamicException.class)
    public Message<?> handleRuntimeException(HttpServletRequest request, DynamicException e) {
        logErrorMsg(request, e);
        return Message.error(e.getStatus(), e.getMsg());
    }

    @ExceptionHandler(Throwable.class)
    public Message<?> handleRemainException(HttpServletRequest request, Throwable e) {
        logStackTrace(request, e);
        return Message.error(EnumRst.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    /**
     * 打印异常信息
     */
    private void logErrorMsg(Throwable e) {
        Map<String, Object> logMap = new HashMap<>(4);
        logMap.put("handle", e.getClass().getName());
        logMap.put("message", e.getMessage());
        log.error(gson.toJson(logMap));
    }

    /**
     * 打印异常信息和入参
     */
    private void logErrorMsg(HttpServletRequest request, Throwable e) {
        boolean isRestful = false;
        Object pathVariables = request.getAttribute(View.PATH_VARIABLES);
        if (pathVariables != null && pathVariables instanceof Map) {
            isRestful = true;
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> params = isRestful
                ? new HashMap<>((Map<String, Object>) pathVariables)
                : Maps.transformValues(request.getParameterMap(), arr -> arr.length == 0 ? null : arr.length == 1 ? arr[0] : arr);
        Map<String, Object> logMap = new HashMap<>(8);
        logMap.put("path", request.getRequestURI());
        logMap.put("from", request.getRemoteAddr());
        logMap.put("handle", e.getClass().getName());
        logMap.put("message", e.getMessage());
        logMap.put("params", params);
        log.error(gson.toJson(logMap));
    }

    /**
     * 打印堆栈和入参
     */
    private void logStackTrace(HttpServletRequest request, Throwable e) {
        boolean isRestful = false;
        Object pathVariables = request.getAttribute(View.PATH_VARIABLES);
        if (pathVariables != null && pathVariables instanceof Map) {
            isRestful = true;
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> params = isRestful
                ? new HashMap<>((Map<String, Object>) pathVariables)
                : Maps.transformValues(request.getParameterMap(), arr -> arr.length == 0 ? null : arr.length == 1 ? arr[0] : arr);
        Map<String, Object> logMap = new HashMap<>(8);
        logMap.put("path", request.getRequestURI());
        logMap.put("from", request.getRemoteAddr());
        logMap.put("handle", e.getClass().getName());
        logMap.put("message", e.getMessage());
        logMap.put("params", params);
        log.error(gson.toJson(logMap), e);
    }

}