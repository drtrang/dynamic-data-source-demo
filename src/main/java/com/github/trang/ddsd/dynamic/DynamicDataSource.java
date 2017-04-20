package com.github.trang.ddsd.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * 动态数据源
 *
 * @author trang
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource(Object defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        //使用DynamicDataSourceHolder保证线程安全
        String dataSource = DynamicDataSourceHolder.get();
        log.info("当前数据库: {}", dataSource);
        return dataSource;
    }
}