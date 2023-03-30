package nnu.edu.back.netty;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import nnu.edu.back.common.aspect.PushNotice;
import nnu.edu.back.common.utils.HandleRealTimeDataUtil;
import nnu.edu.back.common.utils.XmlUtil;
import nnu.edu.back.dao.manage.DeviceMapper;
import nnu.edu.back.pojo.config.DeviceConfig;
import nnu.edu.back.pojo.config.Push;
import nnu.edu.back.service.SSEService;

import java.io.File;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/23/22:39
 * @Description:
 */
@ChannelHandler.Sharable
public class BootNettyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {

    private String config;
    private SSEService sseService;
    private DeviceMapper deviceMapper;
    private static String datagramPath = "D:/zhuomian/毕业/node-manage/datagram/";

    public BootNettyChannelInboundHandlerAdapter(String config, SSEService sseService, DeviceMapper deviceMapper) {
        this.config = config;
        this.sseService = sseService;
        this.deviceMapper = deviceMapper;
    }

    private void handleMethod(String data, String clientAddress, String clientPort) throws Exception {
        File file = new File(this.config);
        if (!file.exists()) {
            throw new Exception();
        }
        DeviceConfig deviceConfig = XmlUtil.fromXml(file, DeviceConfig.class);
        if (deviceConfig.getPush() != null) {
            Push push = deviceConfig.getPush();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            HandleRealTimeDataUtil.normalHandle(datagramPath + deviceConfig.getId() + "/" + dateFormat.format(new Date()) + ".xml", deviceConfig.getId(), push.getPort(), push.getProtocol(), clientAddress, clientPort, data);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", deviceConfig.getId());
            jsonObject.put("time", format.format(new Date()));
            jsonObject.put("name", deviceConfig.getDeviceConfigAttribute().getName());
            sseService.broadcastAll(JSON.toJSONString(jsonObject));
            sseService.message("detail", deviceConfig.getId(), dateFormat.format(new Date()));
            deviceMapper.updateLastUpdate(deviceConfig.getId());
        }
    }

    /**
     * 注册时执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println("--channelRegistered--" + ctx.channel().id().toString());
    }

    /**
     * 离线时执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        System.out.println("--channelUnregistered--" + ctx.channel().id().toString());
    }

    /**
     * 从客户端收到新的数据时，这个方法会在收到消息时被调用
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg == null) return;
        String data = (String) msg;
        String channelId = ctx.channel().id().toString();
        System.out.println("channelId=" + channelId + "data=" + data);
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        handleMethod(data, inSocket.getAddress().toString().replace("/", ""), String.valueOf(inSocket.getPort()));
    }


    /**
     * 从客户端收到新的数据，读取完成后调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    /**
     * 当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);

    }

    /**
     * 客户端与服务端第一次建立连接时 执行
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.channel().read();
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = inSocket.getAddress().getHostAddress();
        System.out.println("channelActive:" + clientIp + ":" + inSocket.getPort());
    }


    /**
     * 客户端与服务端 断连时 执行
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = inSocket.getAddress().getHostAddress();
        System.out.println("channelInactive:" + clientIp + ":" + inSocket.getPort());
        ctx.close();
    }

    /**
     * 服务端当read超时, 会调用这个方法
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = inSocket.getAddress().getHostAddress();

        ctx.close();//超时时断开连接
        System.out.println("userEventTriggered:" + clientIp + ":" + inSocket.getPort());
    }
}
