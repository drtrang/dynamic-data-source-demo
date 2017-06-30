package com.github.trang.dynamic.plugin;

import com.github.trang.dynamic.dynamic.DynamicDataSourceHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import javax.sql.DataSource;

/**
 * 自定义事务管理器
 * 约定：
 *   1. 默认/非事务/事务且只读走从库
 *   2. 事务且可写走主库
 *
 * @author trang
 */
public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager {

    private static final long serialVersionUID = 1L;

    public DynamicDataSourceTransactionManager(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 事务开始前将数据源手动设置进去
     */
    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        //若有 @Transaction 注解，并且不是只读，设置为主库
        if (!definition.isReadOnly()) {
            DynamicDataSourceHolder.routeMaster();
        }
        super.doBegin(transaction, definition);
    }

    /**
     * 事务完成后清除 ThreadLocal
     */
    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        DynamicDataSourceHolder.clear();
        super.doCleanupAfterCompletion(transaction);
    }

}