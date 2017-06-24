package com.github.trang.dynamic.config;

import static com.github.trang.dynamic.dynamic.DynamicDataSourceHolder.MASTER_DATA_SOURCE;
import static com.github.trang.dynamic.dynamic.DynamicDataSourceHolder.SLAVE_DATA_SOURCE;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.github.trang.dynamic.dynamic.DynamicDataSource;
import com.google.common.collect.ImmutableMap;

import lombok.extern.slf4j.Slf4j;

/**
 * 动态数据源配置
 *
 * @author trang
 */
@Configuration
@Slf4j
public class SpringDataSourceConfig {

    private static final String MASTER_DATA_SOURCE_PREFIX = "dynamic-data-source.druid.master";
    private static final String SLAVE_DATA_SOURCE_PREFIX = "dynamic-data-source.druid.slave";
    private static final String DRUID_LOG_FILTER_PROPERTIES_PREFIX = "dynamic-data-source.druid.log";

    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(MASTER_DATA_SOURCE_PREFIX)
    public DruidDataSource masterDataSource() {
        log.info("------ 初始化 Druid 主数据源 ------");
        return new DruidDataSource();
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(SLAVE_DATA_SOURCE_PREFIX)
    public DruidDataSource slaveDataSource() {
        log.info("------ 初始化 Druid 从数据源 ------");
        return new DruidDataSource();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DruidDataSource masterDataSource, DruidDataSource slaveDataSource) {
        log.info("------ 初始化 Dynamic 数据源 ------");
        Map<String, DataSource> targetDataSources = ImmutableMap.<String, DataSource>builder()
                .put(MASTER_DATA_SOURCE, masterDataSource)
                .put(SLAVE_DATA_SOURCE, slaveDataSource)
                .build();
        return new DynamicDataSource(slaveDataSource, targetDataSources);
    }

    @Bean
    @ConfigurationProperties(DRUID_LOG_FILTER_PROPERTIES_PREFIX)
    public Slf4jLogFilter slf4jLogFilter() {
        log.info("------ 初始化 Druid 日志监控 ------");
        return new Slf4jLogFilter();
    }

}