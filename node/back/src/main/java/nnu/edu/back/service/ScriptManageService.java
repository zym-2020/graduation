package nnu.edu.back.service;

import nnu.edu.back.pojo.Script;
import nnu.edu.back.pojo.scriptConfig.ScriptConfig;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/04/10/19:18
 * @Description:
 */
public interface ScriptManageService {
    Map<String, Object> pageQuery(int size, int page, String keyword);

    ScriptConfig getScriptConfig(String scriptId);
}
