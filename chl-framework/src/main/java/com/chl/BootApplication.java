package com.chl;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chl
 * @Date: 2020/1/4 14:19
 * @Version 1.0
 */
@SpringBootApplication
@Slf4j
@RestController
@MapperScan("com.chl.*.mapper")
public class BootApplication {

    public static void main(String[] args) {
        log.info("chl-boot开始启动...");
        SpringApplication.run(BootApplication.class, args);
    }

    @GetMapping("hello")
    public Map<String,String> hello(){
        Map<String,String> map = new HashMap<>();
        map.put("name","chl-boot");
        map.put("version","0.0.1-SNAPSHOT");
        return  map;
    }
}
