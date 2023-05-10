package nnu.edu.back.common.utils;

import nnu.edu.back.pojo.config.ActionParameters;
import nnu.edu.back.pojo.config.ActionStep;
import nnu.edu.back.pojo.scriptConfig.ScriptConfig;
import nnu.edu.back.pojo.scriptConfig.ScriptParameters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/04/04/16:25
 * @Description:
 */
public class ScriptUtil {
    private static String basePath = "D:/zhuomian/毕业/node-manage/data/";
    private static String scriptPath = "D:/zhuomian/毕业/node-manage/scripts/";
    private static String tempPath = "D:/zhuomian/毕业/node-manage/temp/";

//    private static String basePath = "C:/resource/node-manage/data/";
//    private static String scriptPath = "C:/resource/node-manage/scripts/";
//    private static String tempPath = "C:/resource/node-manage/temp/";

    public static String actionParamUtil(ActionStep actionStep, String data, String deviceId) throws Exception {
        String id = actionStep.getScript();
        File file = new File(scriptPath + id + "/scriptConfig.xml");
        if (!file.exists()) {
            throw new Exception();
        }
        ScriptConfig scriptConfig = XmlUtil.fromXml(file, ScriptConfig.class);
        ScriptParameters scriptParameters = scriptConfig.getParameters();
        ActionParameters actionParameters = actionStep.getParameters();
        List<String> res = new ArrayList<>();
        for (int i = 0; i < actionParameters.getParameterList().size(); i++) {
            if (actionParameters.getParameterList().get(i).equals("#{data}")) {
                String uuid = UUID.randomUUID().toString();
                FileUtil.writeFile(tempPath + uuid + ".txt", data);
                res.add(tempPath + uuid + ".txt");
            } else if (scriptParameters.getParameterList().get(i).getType().equals("path") || scriptParameters.getParameterList().get(i).getType().equals("file")) {
                String path = actionParameters.getParameterList().get(i).equals("/") ? "" : "/" + actionParameters.getParameterList().get(i);
                res.add(basePath + deviceId + path);
            } else if (scriptParameters.getParameterList().get(i).getType().equals("input")) {
                res.add(actionParameters.getParameterList().get(i));
            }
        }
        String content = res.size() + "\n";
        for (String s : res) {
            content += (s + "\n");
        }
        String uuid = UUID.randomUUID().toString();
        FileUtil.writeFile(tempPath + uuid + ".txt", content);
        return tempPath + uuid + ".txt";
    }
}
