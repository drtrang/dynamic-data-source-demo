package com.github.trang.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DynamicDataSourceDemoApplication {

    public static void main(String[] args) {
        System.setProperty("druid.logType", "slf4j");
        SpringApplication.run(DynamicDataSourceDemoApplication.class, args);
        log.info("dynamic-data-source-demo is running...");
    }

}