package com.github.trang.ddsd.config;

import com.github.trang.ddsd.dynamic.DynamicDataSource;
import com.github.trang.ddsd.plugin.DynamicDataSourceTransactionManager;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@MapperScan("com.github.trang.ddsd.mapper")
@EnableTransactionManagement(proxyTargetClass = true)
@Slf4j
public class SpringDaoConfig implements TransactionManagementConfigurer {

    @Autowired
    private DynamicDataSource dataSource;

    @Override
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        log.info("------ 初始化 DynamicDataSourceTransactionManager ------");
        return new DynamicDataSourceTransactionManager(dataSource);
    }
}