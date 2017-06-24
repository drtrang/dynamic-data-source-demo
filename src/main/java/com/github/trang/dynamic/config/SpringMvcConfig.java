package com.github.trang.dynamic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonFactoryBean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC 配置
 *
 * @author trang
 */
@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 设置路径匹配
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer
                //设置是否是后缀模式匹配，如"/user"是否匹配"/user.*"，默认true
                .setUseSuffixPatternMatch(false)
                //设置是否自动后缀路径模式匹配，如"/user"是否匹配"/user/"，默认true
                .setUseTrailingSlashMatch(true);
    }

    /**
     * 将对于静态资源的请求转发到 Servlet 容器的默认处理静态资源的 Servlet
     * 因为将 Spring 的拦截模式设置为 "/" 时会对静态资源进行拦截
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 将无需后端处理的 url 重定向
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/base-code/list");
    }

    @Bean
    public GsonFactoryBean gson() {
        GsonFactoryBean factory = new GsonFactoryBean();
        factory.setDateFormatPattern("yyyy-MM-dd HH:mm:ss");
        factory.setDisableHtmlEscaping(true);
        factory.setPrettyPrinting(true);
        factory.setBase64EncodeByteArrays(true);
        return factory;
    }

}