package com.github.trang.dynamic.plugin;

import com.google.common.base.Objects;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import lombok.Getter;
import org.springframework.cache.interceptor.KeyGenerator;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 自定义缓存key生成规则
 *
 * @author trang
 */
public class CacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return new CacheKey(target, method, params).getKey();
    }

    class CacheKey implements Serializable {

        private static final long serialVersionUID = -2497773528195443863L;

        private final String className;
        private final String methodName;
        private final Object[] params;
        private final int hashCode;
        @Getter
        private final String key;

        CacheKey(Object target, Method method, Object... params) {
            this.className = target.getClass().getName();
            this.methodName = getMethodName(method);
            this.params = new Object[params.length];
            System.arraycopy(params, 0, this.params, 0, params.length);
            this.hashCode = generateHashCode();
            this.key = generateKey();
        }

        private String getMethodName(Method method) {
            StringBuilder builder = new StringBuilder(method.getName());
            Class<?>[] types = method.getParameterTypes();
            builder.append("(");
            if (types.length != 0) {
                for (Class<?> type : types) {
                    String name = type.getSimpleName();
                    builder.append(name).append(",");
                    builder.delete(builder.length() - 1, builder.length());
                }
            }
            builder.append(")");
            return builder.toString();
        }

        private int generateHashCode() {
            final int prime = 31;
            int result = Objects.hashCode(className, methodName);
            result = prime * result + Arrays.deepHashCode(params);
            return result;
        }

        /**
         * 生成 Redis 的 key
         *
         * 当参数没有重写 hashcode() 时，缓存将永不命中，即使参数相同。为解决该问题，这里使用 toString()
         * 作为 redis key，但随之而来的是 key 长度过长的风险，故而继续优化，取 toString() 后的值再 hash
         * 得到最终结果。
         *
         * 仍然不可避免的风险是 hash 碰撞，所以如果要求较高，请自定义 key
         */
        private String generateKey() {
            // 获取所有参数的 toString()
            String str = Arrays.deepToString(params);
            // 将 toString() 作 hash
            HashCode hash = Hashing.murmur3_128().hashString(str, StandardCharsets.UTF_8);
            // 类名.方法名(参数类型...)|参数toString()的加密值
            return this.className + "." + methodName + "|" + hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CacheKey cacheKey = (CacheKey) o;
            return Objects.equal(className, cacheKey.className) &&
                    Objects.equal(methodName, cacheKey.methodName) &&
                    Arrays.deepEquals(params, cacheKey.params);
        }

        @Override
        public int hashCode() {
            return this.hashCode;
        }

        @Override
        public String toString() {
            return key;
        }

    }

}
