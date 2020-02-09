package com.chl.config;

import com.chl.sys.web.listener.AppListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chl
 * @Date: 2020/1/6 11:44
 * @Version 1.0
 *
 * 注册三大组件
 */
@Configuration
public class WebConfig {
    /**
     * 注册监听器
     */
    @Bean
    public AppListener appListener() {
        return new AppListener();
    }

    /**
     * 注册
     */
    @Bean
    public ServletListenerRegistrationBean<AppListener> servletListenerRegistrationBeanAppListener(AppListener listener) {
        ServletListenerRegistrationBean<AppListener> registrationBean = new ServletListenerRegistrationBean<>();

        //注入监听器
        registrationBean.setListener(listener);
        return registrationBean;
    }

}
