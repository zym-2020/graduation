package nnu.edu.back.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.SneakyThrows;
import nnu.edu.back.common.utils.ProcessUtil;
import nnu.edu.back.common.utils.ScriptUtil;
import nnu.edu.back.common.utils.XmlUtil;
import nnu.edu.back.pojo.config.Action;
import nnu.edu.back.pojo.config.ActionStep;
import nnu.edu.back.pojo.config.DeviceConfig;
import nnu.edu.back.pojo.scriptConfig.ScriptConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/04/21/14:32
 * @Description:
 */
@ChannelHandler.Sharable
public class ActionChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {

    private static String scriptPath = "D:/zhuomian/毕业/node-manage/scripts/";

    private String config;

    public ActionChannelInboundHandlerAdapter(String config) {
        this.config = config;
    }

    /**
     * 从客户端收到新的数据时，这个方法会在收到消息时被调用
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        File file = new File(this.config);
        if (!file.exists()) {
            throw new Exception();
        }
        DeviceConfig deviceConfig = XmlUtil.fromXml(file, DeviceConfig.class);
        byte[] bytes = (byte[]) msg;
        System.out.println(1);
//        if (deviceConfig.getActions() != null && deviceConfig.getActions().getActionList().size() > 0 && msg != null) {
//
//            String result = (String) msg;
//            List<Action> actions = deviceConfig.getActions().getActionList();
//            for (Action action : actions) {
//                new Thread() {
//                    @Override
//                    @SneakyThrows
//                    public void run() {
//                        List<ActionStep> steps = action.getSteps();
//                        for (ActionStep step : steps) {
//                            String id = step.getScript();
//                            File file = new File(scriptPath + id + "/scriptConfig.xml");
//                            if (!file.exists()) {
//                                throw new Exception();
//                            }
//                            ScriptConfig scriptConfig = XmlUtil.fromXml(file, ScriptConfig.class);
//                            String paramPath = ScriptUtil.actionParamUtil(step, result, deviceConfig.getId());
//                            List<String> command = new ArrayList<>();
//                            command.add("cmd");
//                            command.add("/c");
//                            command.add("d: " + "&&" + " cd " + scriptPath + scriptConfig.getId() + "/code" + " && " + scriptConfig.getEnter() + " " + paramPath);
//                            try {
//                                Process process = ProcessUtil.exeProcess(command);
//                                String result = ProcessUtil.readProcessString(process.getInputStream());
//                                int state = process.exitValue();
//                                System.out.println(result);
//                                if (state != 0) {
//                                    System.out.println("出错了");
//                                }
//                            } catch (Exception e) {
//                                System.out.println("出错了");
//                            }
//                        }
//                    }
//                }.start();
//            }
//
//        }
    }
}
