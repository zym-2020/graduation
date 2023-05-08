package nnu.edu.back.controller;

import com.alibaba.fastjson2.JSONObject;
import nnu.edu.back.common.result.JsonResult;
import nnu.edu.back.common.result.ResultUtils;
import nnu.edu.back.service.DynamicDatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/05/08/10:12
 * @Description:
 */
@RestController
@RequestMapping("/dynamic")
public class DynamicDatasourceController {
    @Autowired
    DynamicDatasourceService dynamicDatasourceService;

    @RequestMapping(value = "/addDatasource", method = RequestMethod.POST)
    public JsonResult addDatasource(@RequestBody JSONObject jsonObject) {
        dynamicDatasourceService.addDatasource(jsonObject);
        return ResultUtils.success();
    }
}
