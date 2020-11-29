package com.tca.netty.wechat.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author zhouan
 * @Date 2020/7/16
 */
@Component
public class WechatServerStarter implements CommandLineRunner {

    @Autowired
    private WechatServer wechatServer;

    @Override
    public void run(String... args) throws Exception {
        wechatServer.start();
    }
}
