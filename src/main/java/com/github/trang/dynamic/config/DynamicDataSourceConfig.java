package com.github.trang.dynamic.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.trang.dynamic.dynamic.DynamicDataSource;
import com.github.trang.dynamic.plugin.DynamicDataSourceTransactionManager;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.transaction.PlatformTransactionManagerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * 动态数据源配置
 *
 * @author trang
 */
@Configuration
@Slf4j
public class DynamicDataSourceConfig {

    public static final String MASTER = "master";
    public static final String SLAVE = "slave";

    /**
     * 动态数据源
     *
     * @param dataSources 各个原始数据源
     * @return dataSource
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(List<DruidDataSource> dataSources) {
        log.info("------ 初始化 dynamic 数据源 ------");
        // 将 List 转换为 Map，以 name 属性作为 key
        Map<String, DataSource> dataSourceMap = dataSources.stream()
                .collect(toMap(DruidDataSource::getName, Function.identity()));
        // 默认使用从数据源
        return new DynamicDataSource(dataSourceMap.get(SLAVE), dataSourceMap);
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager(DynamicDataSource dataSource) {
        log.info("------ 初始化 DynamicDataSourceTransactionManager ------");
        return new DynamicDataSourceTransactionManager(dataSource);
    }

}