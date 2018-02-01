package com.github.trang.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author trang
 */
@SpringBootApplication
@Slf4j
public class DynamicDataSourceDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        System.setProperty("druid.logType", "slf4j");
        SpringApplication.run(DynamicDataSourceDemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("dynamic-data-source-demo is running...");
    }

    @RestController
    public static class WelcomeController {

        @GetMapping("/")
        public String welcome() {
            return "Welcome to Dynamic DataSource Demo!";
        }

    }

}