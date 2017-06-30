package com.github.trang.dynamic.dynamic;

/**
 * 动态数据源容器
 *
 * @author trang
 */
public final class DynamicDataSourceHolder {
    public static final String DB_1 = "1";
    public static final String DB_2 = "2";

    private static final ThreadLocal<String> CONTAINER = new ThreadLocal<>();

    private static void set(String dataSource) {
        CONTAINER.set(dataSource);
    }

    public static void route1() {
        set(DB_1);
    }

    public static void route2() {
        set(DB_2);
    }

    public static String get() {
        return CONTAINER.get();
    }

    public static void clear() {
        CONTAINER.remove();
    }
}