package nnu.edu.back.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/24/9:53
 * @Description:
 */
public class UDPServer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private EventLoopGroup bossGroup;

    public void bind(int port) {
        bossGroup = new NioEventLoopGroup(20);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(bossGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UDPServerHandler());

            ChannelFuture f = bootstrap.bind(port).sync();
            if (f.isSuccess()) {
                log.info("UDPServer start...");
                f.channel().closeFuture().sync();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("UDPServer close...");
            bossGroup.shutdownGracefully();
        }
    }

    public void stop() {
        if (this.bossGroup != null) {
            this.bossGroup.shutdownGracefully();
        }
        log.info("UDPServer close...");
    }
}
