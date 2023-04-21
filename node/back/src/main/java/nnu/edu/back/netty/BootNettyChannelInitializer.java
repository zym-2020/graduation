package nnu.edu.back.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import nnu.edu.back.dao.manage.DeviceMapper;
import nnu.edu.back.dao.monitoring.MonitoringDataMapper;
import nnu.edu.back.service.SSEService;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/23/22:38
 * @Description:
 */
@ChannelHandler.Sharable
public class BootNettyChannelInitializer<SocketChannel> extends ChannelInitializer<Channel> {
    public static long READ_TIME_OUT = 60;

    public static long WRITE_TIME_OUT = 60;

    public static long ALL_TIME_OUT = 60;

    private String config;
    private SSEService sseService;
    private DeviceMapper deviceMapper;
    private MonitoringDataMapper monitoringDataMapper;

    public BootNettyChannelInitializer(String config, SSEService sseService, DeviceMapper deviceMapper, MonitoringDataMapper monitoringDataMapper) {
        this.config = config;
        this.sseService = sseService;
        this.deviceMapper = deviceMapper;
        this.monitoringDataMapper = monitoringDataMapper;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {

        /**
         * 下面这段代码设置连接等待时间，注释掉的话服务端就一直连接，不主动断掉
         */
//        channel.pipeline().addLast(new IdleStateHandler(READ_TIME_OUT, WRITE_TIME_OUT, ALL_TIME_OUT, TimeUnit.SECONDS));
        /**
         * 设置ByteBuf大小
         */
        channel.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(1024 * 512));
        //带编码
//        channel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
//        channel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));

        //自定义ChannelInboundHandlerAdapter
        channel.pipeline().addLast(new BootNettyChannelInboundHandlerAdapter(this.config, this.sseService, this.deviceMapper, this.monitoringDataMapper));
        channel.pipeline().addLast(new ActionChannelInboundHandlerAdapter(this.config));
    }
}
