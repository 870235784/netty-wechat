package com.tca.netty.wechat.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author zhoua
 * @Date 2020/11/22
 * 启动类
 */
@SpringBootApplication
@EnableConfigurationProperties
@Slf4j
public class WechatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatServerApplication.class, args);
    }
}
