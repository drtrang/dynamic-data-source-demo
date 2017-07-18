package com.github.trang.dynamic.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.trang.druid.datasource.DruidMultiDataSource;
import com.github.trang.dynamic.dynamic.DynamicDataSource;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Map;

import static com.github.trang.dynamic.dynamic.DynamicDataSourceHolder.MASTER_DATA_SOURCE;
import static com.github.trang.dynamic.dynamic.DynamicDataSourceHolder.SLAVE_DATA_SOURCE;

/**
 * 动态数据源配置
 *
 * @author trang
 */
@Configuration
@Slf4j
public class SpringDataSourceConfig {

    private static final String MASTER_DATA_SOURCE_PREFIX = "spring.datasource.druid.master";
    private static final String SLAVE_DATA_SOURCE_PREFIX = "spring.datasource.druid.slave";

    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(MASTER_DATA_SOURCE_PREFIX)
    public DruidDataSource masterDataSource() {
        log.info("------ 初始化 Druid 主数据源 ------");
        return new DruidMultiDataSource();
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(SLAVE_DATA_SOURCE_PREFIX)
    public DruidDataSource slaveDataSource() {
        log.info("------ 初始化 Druid 从数据源 ------");
        return new DruidMultiDataSource();
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

}