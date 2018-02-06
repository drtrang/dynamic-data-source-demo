package com.github.trang.dynamic.config;

import com.github.trang.dynamic.dynamic.DynamicDataSource;
import com.github.trang.dynamic.plugin.DynamicDataSourceTransactionManager;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源配置
 *
 * @author trang
 */
@Configuration
@MapperScan("com.github.trang.dynamic.mapper")
@Slf4j
public class DynamicDataSourceConfig {

    public static final String MASTER = "master";
    public static final String SLAVE = "slave";

    /**
     * 动态数据源
     *
     * @param dataSourceMap 各个原始数据源
     * @return dataSource
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(Map<String, DataSource> dataSourceMap) {
        log.info("------ 初始化 dynamic 数据源 ------");
        // 默认使用从数据源
        return new DynamicDataSource(dataSourceMap.get(SLAVE), dataSourceMap);
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager(DynamicDataSource dataSource) {
        log.info("------ 初始化 DynamicDataSourceTransactionManager ------");
        return new DynamicDataSourceTransactionManager(dataSource);
    }

}