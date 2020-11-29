package com.tca.netty.wechat.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhoua
 * @Date 2020/11/22
 */
@ConfigurationProperties(prefix = "wechat.server")
@Component
@Data
public class WechatServerConfig {

    /**
     * 服务监听端口
     */
    private Integer port;

    /**
     * boss线程数
     */
    private Integer bossNum;

    /**
     * 连接线程等待数
     */
    private Integer logback;

    /**
     * 接收消息的最大长度(字节)
     */
    private Integer messageMaxLength;

    /**
     * 消息头长度(用int表示, 长度为4)
     */
    private Integer messageHeadLength;

    /**
     * 心跳间隔事件
     */
    private Integer heartbeatTime;

    /**
     * 判断离线的时间
     */
    private Integer idleTime;

}
