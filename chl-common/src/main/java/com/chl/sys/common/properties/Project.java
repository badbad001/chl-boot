package com.chl.sys.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chl
 * @Date: 2020/1/6 11:05
 * @Version 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "project")
public class Project {

    private String code;

    private String name; //项目名字

    private String fullName; //完整名字

    private String superAdmins;

    /**
     * 版本号
     */
    private String version;

    /**
     * 浏览器图标
     */
    private String icon;

    //登录背景图
    private String loginBackImg;


}
