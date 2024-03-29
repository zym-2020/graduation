package nnu.edu.back.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import nnu.edu.back.dao.manage.DeviceMapper;
import nnu.edu.back.dao.monitoring.MonitoringDataMapper;
import nnu.edu.back.service.SSEService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private SSEService sseService;
    private DeviceMapper deviceMapper;
    private MonitoringDataMapper monitoringDataMapper;

    public TCPServer(String config, SSEService sseService, DeviceMapper deviceMapper, MonitoringDataMapper monitoringDataMapper) {
        this.config = config;
        this.sseService = sseService;
        this.deviceMapper = deviceMapper;
        this.monitoringDataMapper = monitoringDataMapper;
    }

    public void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 64)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new BootNettyChannelInitializer<SocketChannel>(this.config, this.sseService, this.deviceMapper, this.monitoringDataMapper));
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
