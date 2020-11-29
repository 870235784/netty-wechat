package com.tca.netty.wechat.server.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhoua
 * @Date 2020/11/22
 */
@Component
@Slf4j
public class WechatServer {

    @Autowired
    private WechatServerConfig wechatServerConfig;

    /**
     * boss线程组
     */
    private NioEventLoopGroup bossGroup;

    /**
     * worker线程组
     */
    private NioEventLoopGroup workerGroup;

    /**
     * 服务启动
     * @throws Exception
     */
    public void start() throws Exception {
        try {
            this.bossGroup = new NioEventLoopGroup(wechatServerConfig.getBossNum());
            this.workerGroup = new NioEventLoopGroup();

            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    // 装配线程组
                    .group(bossGroup, workerGroup)
                    // boss线程组配置
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, wechatServerConfig.getLogback())
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // worker线程组配置
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        /**
                         *  * 入栈出栈处理器
                         * 入栈: 消息头解码器 --> 字符串解码器 --> 心跳机制处理器 --> 业务处理器
                         * 出栈: 字符串编码器 --> 消息头编码器
                         */
                        @Override
                        protected void initChannel(SocketChannel client) throws Exception {
                            client.pipeline()
                            // 消息头解码器
                            .addLast(new LengthFieldBasedFrameDecoder(wechatServerConfig.getMessageMaxLength(),
                                    0, wechatServerConfig.getMessageHeadLength(), 0, wechatServerConfig.getMessageHeadLength()))
                            // 字符串解码器
                            .addLast(new StringDecoder())
                            // 消息头编码器
                            .addLast(new LengthFieldPrepender(wechatServerConfig.getMessageHeadLength()))
                            // 字符串编码器
                            .addLast(new StringEncoder())
                            // 心跳机制处理器
                            .addLast(new IdleStateHandler(wechatServerConfig.getIdleTime(), 0, 0))
                            // 业务处理器
                            .addLast(null);
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = serverBootstrap.bind(wechatServerConfig.getPort()).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    log.info("TCP服务启动完毕, port = {}", wechatServerConfig.getPort());
                } else {
                    log.error("TCP服务启动失败, cause = {}", future.cause());
                }
            });
            channelFuture.channel().closeFuture().sync();
        } finally {
            this.bossGroup.shutdownGracefully();
            this.workerGroup.shutdownGracefully();
        }


    }

}
