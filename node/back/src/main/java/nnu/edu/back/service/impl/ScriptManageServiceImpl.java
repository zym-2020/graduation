package nnu.edu.back.service.impl;

import nnu.edu.back.common.exception.MyException;
import nnu.edu.back.common.result.ResultEnum;
import nnu.edu.back.common.utils.XmlUtil;
import nnu.edu.back.dao.manage.ScriptMapper;
import nnu.edu.back.pojo.Script;
import nnu.edu.back.pojo.scriptConfig.ScriptConfig;
import nnu.edu.back.service.ScriptManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/04/10/19:18
 * @Description:
 */
@Service
public class ScriptManageServiceImpl implements ScriptManageService {
    @Autowired
    ScriptMapper scriptMapper;

    @Value("${scriptConfigPath}")
    String scriptConfigPath;

    @Override
    public Map<String, Object> pageQuery(int size, int page, String keyword) {
        if (keyword.equals("")) {
            keyword = "%" + keyword + "%";
        }
        List<Script> scriptList = scriptMapper.pageQuery(size, size * page, keyword);
        int count = scriptMapper.countAll(keyword);
        Map<String, Object> result = new HashMap<>();
        result.put("list", scriptList);
        result.put("total", count);
        return result;
    }

    @Override
    public ScriptConfig getScriptConfig(String scriptId) {
        String path = scriptConfigPath + scriptId + "/scriptConfig.xml";
        File file = new File(path);
        if (!file.exists()) {
            throw new MyException(ResultEnum.NO_OBJECT);
        }
        return XmlUtil.fromXml(file, ScriptConfig.class);
    }
}
