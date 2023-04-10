package nnu.edu.back.controller;

import com.alibaba.fastjson2.JSONObject;
import nnu.edu.back.common.result.JsonResult;
import nnu.edu.back.common.result.ResultUtils;
import nnu.edu.back.service.ScriptManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/04/10/19:16
 * @Description:
 */
@RestController
@RequestMapping("/script")
public class ScriptManageController {
    @Autowired
    ScriptManageService scriptManageService;

    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public JsonResult pageQuery(@RequestBody JSONObject jsonObject) {
        int size = jsonObject.getIntValue("size");
        int page = jsonObject.getIntValue("page");
        String keyword = jsonObject.getString("keyword");
        return ResultUtils.success(scriptManageService.pageQuery(size, page, keyword));
    }

    @RequestMapping(value = "/getScriptConfig/{scriptId}", method = RequestMethod.GET)
    public JsonResult getScriptConfig(@PathVariable String scriptId) {
        return ResultUtils.success(scriptManageService.getScriptConfig(scriptId));
    }
}
