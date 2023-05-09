package nnu.edu.back.netty;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.SneakyThrows;
import nnu.edu.back.common.utils.HandleRealTimeDataUtil;
import nnu.edu.back.common.utils.ProcessUtil;
import nnu.edu.back.common.utils.ScriptUtil;
import nnu.edu.back.common.utils.XmlUtil;
import nnu.edu.back.dao.manage.DeviceMapper;
import nnu.edu.back.dao.monitoring.MonitoringDataMapper;
import nnu.edu.back.pojo.config.Action;
import nnu.edu.back.pojo.config.ActionStep;
import nnu.edu.back.pojo.config.DeviceConfig;
import nnu.edu.back.pojo.config.Push;
import nnu.edu.back.pojo.scriptConfig.ScriptConfig;
import nnu.edu.back.service.SSEService;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private MonitoringDataMapper monitoringDataMapper;


    public BootNettyChannelInboundHandlerAdapter(String config, SSEService sseService, DeviceMapper deviceMapper, MonitoringDataMapper monitoringDataMapper) {
        this.config = config;
        this.sseService = sseService;
        this.deviceMapper = deviceMapper;
        this.monitoringDataMapper = monitoringDataMapper;
    }

    private void handleMethod(byte[] bytes) throws Exception {
        File file = new File(this.config);
        if (!file.exists()) {
            throw new Exception();
        }
        DeviceConfig deviceConfig = XmlUtil.fromXml(file, DeviceConfig.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (deviceConfig.getPush() != null) {
            String tableName;
            LocalDateTime currentDate = LocalDateTime.now();
            int year = currentDate.getYear();
            int monthValue = currentDate.getMonthValue();
            if (monthValue >= 1 && monthValue <= 3) {
                tableName = "t" + year + "13";
            } else if (monthValue >= 4 && monthValue <= 6) {
                tableName = "t" + year + "46";
            } else if (monthValue >= 7 && monthValue <= 9) {
                tableName = "t" + year + "79";
            } else {
                tableName = "t" + year + "1012";
            }
            HandleRealTimeDataUtil.normalHandle(deviceConfig.getId(), bytes, this.monitoringDataMapper, tableName);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", deviceConfig.getId());
        jsonObject.put("time", format.format(new Date()));
        jsonObject.put("name", deviceConfig.getDeviceConfigAttribute().getName());
        sseService.broadcastAll(JSON.toJSONString(jsonObject));
        sseService.message("detail", deviceConfig.getId(), dateFormat.format(new Date()));
        deviceMapper.updateLastUpdate(deviceConfig.getId());
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

        if (msg != null) {
            String channelId = ctx.channel().id().toString();
            System.out.println("channelId=" + channelId);
//            InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
            ByteBuf byteBuf = (ByteBuf) msg;
            InputStream is = new ByteBufInputStream(byteBuf);
            byte[] bytes = HandleRealTimeDataUtil.readStream(is);
            String result = new String(bytes);
            handleMethod(bytes);

            ctx.fireChannelRead(bytes);
        }

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
