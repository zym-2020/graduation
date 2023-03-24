package nnu.edu.back.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/23/22:33
 * @Description:
 */

public class TCPServer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Channel currentChannel;
    private String config;

    public TCPServer(String config) {
        this.config = config;
    }

    public void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap = serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap = serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap = serverBootstrap.option(ChannelOption.SO_BACKLOG, 64);
            serverBootstrap = serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            serverBootstrap = serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap = serverBootstrap.childHandler(new BootNettyChannelInitializer<SocketChannel>(this.config));
            ChannelFuture f = serverBootstrap.bind(port).sync();
            currentChannel = f.channel();
            if (f.isSuccess()) {
                log.info("netty server start success!");

                /**
                 * 等待服务器监听端口关闭
                 */
                f.channel().closeFuture().sync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }
    }

    public void stop() {
        if (currentChannel != null) {
            log.info("关闭服务");

            currentChannel.close();
            currentChannel = null;
        }
    }
}
