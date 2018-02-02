package com.github.trang.dynamic.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.experimental.UtilityClass;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Spring Expression Language 解析
 *
 * @author trang
 */
@UtilityClass
public class SpelParser {

    private static final Cache<String, Expression> EXPRESSION_CACHE = CacheBuilder.newBuilder()
            .maximumSize(8192)
            .initialCapacity(512)
            .expireAfterWrite(1L, TimeUnit.HOURS)
            .build();

    private static final ExpressionParser SPEL_PARSER = new SpelExpressionParser();

    public static String parse(String expression, JoinPoint point) {
        String[] params = ((MethodSignature) point.getSignature()).getParameterNames();
        Object[] args = point.getArgs();
        Map<String, Object> map = new HashMap<>(args.length);
        for (int i = 0; i < params.length; i++) {
            map.put(params[i], args[i]);
        }
        return parse(expression, String.class, map);
    }

    public static <T> T parse(String expression, Class<T> clazz, JoinPoint point) {
        String[] params = ((MethodSignature) point.getSignature()).getParameterNames();
        Object[] args = point.getArgs();
        Map<String, Object> map = new HashMap<>(args.length);
        for (int i = 0; i < params.length; i++) {
            map.put(params[i], args[i]);
        }
        return parse(expression, clazz, map);
    }

    public static String parse(String expression, Map<String, Object> param) {
        return parse(expression, String.class, param);
    }

    public static <T> T parse(String expression, Class<T> clazz, Map<String, Object> param) {
        try {
            Expression e = EXPRESSION_CACHE.get(expression, () -> SPEL_PARSER.parseExpression(expression));
            EvaluationContext context = new StandardEvaluationContext();
            for (Entry<String, Object> entry : param.entrySet()) {
                context.setVariable(entry.getKey(), entry.getValue());
            }
            return e.getValue(context, clazz);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}