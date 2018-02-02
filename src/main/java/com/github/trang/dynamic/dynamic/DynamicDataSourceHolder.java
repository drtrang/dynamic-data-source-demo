package com.github.trang.dynamic.dynamic;

import com.github.trang.dynamic.config.DynamicDataSourceConfig;

/**
 * 动态数据源容器
 *
 * @author trang
 */
public final class DynamicDataSourceHolder {

    private static final ThreadLocal<String> CONTAINER = ThreadLocal.withInitial(
            () -> DynamicDataSourceConfig.SLAVE
    );

    private static void set(String dataSource) {
        CONTAINER.set(dataSource);
    }

    public static void routeMaster() {
        set(DynamicDataSourceConfig.MASTER);
    }

    public static void routeSlave() {
        set(DynamicDataSourceConfig.SLAVE);
    }

    public static String get() {
        return CONTAINER.get();
    }

    public static void clear() {
        CONTAINER.remove();
    }

}