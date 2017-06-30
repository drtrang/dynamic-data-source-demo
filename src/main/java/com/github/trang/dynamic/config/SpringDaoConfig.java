package com.github.trang.dynamic.config;

import com.atomikos.icatch.jta.UserTransactionFactory;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.github.trang.dynamic.dynamic.DynamicDataSource;
import com.github.trang.dynamic.plugin.DynamicDataSourceTransactionManager;
import lombok.Getter.AnyAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * Spring Dao 层配置
 *
 * @author trang
 */
@Configuration
@MapperScan("com.github.trang.dynamic.mapper")
@EnableTransactionManagement(proxyTargetClass = true)
@Slf4j
public class SpringDaoConfig implements TransactionManagementConfigurer {

    @Autowired
    private UserTransaction userTransaction;
    @Autowired
    private TransactionManager transactionManager;

    @Bean
    public UserTransaction userTransaction() {
        return new UserTransactionImp();
    }

    @Bean
    public TransactionManager transactionManager() {
        UserTransactionManager manager = new UserTransactionManager();
        manager.setForceShutdown(false);
        return manager;
    }

    @Override
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        log.info("------ 初始化 JtaTransactionManager ------");
        return new JtaTransactionManager(userTransaction, transactionManager);
    }

}