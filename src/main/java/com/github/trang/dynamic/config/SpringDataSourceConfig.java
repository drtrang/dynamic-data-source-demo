package com.github.trang.dynamic.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.github.trang.dynamic.dynamic.DynamicDataSource;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
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

    private static final String MASTER_DATA_SOURCE_PREFIX = "dynamic-data-source.druid.master";
    private static final String SLAVE_DATA_SOURCE_PREFIX = "dynamic-data-source.druid.slave";

    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(MASTER_DATA_SOURCE_PREFIX)
    public DruidXADataSource masterDataSource() {
        return new DruidXADataSource();
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public AtomikosDataSourceBean firstDataSource(DruidXADataSource masterDataSource) {
        log.info("------ 初始化 1 数据源 ------");
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setXaDataSource(masterDataSource);
        return bean;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(SLAVE_DATA_SOURCE_PREFIX)
    public DruidXADataSource slaveDataSource() {
        return new DruidXADataSource();
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public AtomikosDataSourceBean secondDataSource(DruidXADataSource slaveDataSource) {
        log.info("------ 初始化 2 数据源 ------");
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setXaDataSource(slaveDataSource);
        return bean;
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(AtomikosDataSourceBean firstDataSource,
                                        AtomikosDataSourceBean secondDataSource) {
        log.info("------ 初始化 Dynamic 数据源 ------");
        Map<String, DataSource> targetDataSources = ImmutableMap.<String, DataSource>builder()
                .put(MASTER_DATA_SOURCE, firstDataSource)
                .put(SLAVE_DATA_SOURCE, secondDataSource)
                .build();
        return new DynamicDataSource(secondDataSource, targetDataSources);
    }

}