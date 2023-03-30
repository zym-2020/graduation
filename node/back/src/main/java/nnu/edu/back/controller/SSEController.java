package nnu.edu.back.controller;

import nnu.edu.back.common.result.JsonResult;
import nnu.edu.back.common.result.ResultUtils;
import nnu.edu.back.service.SSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/29/20:05
 * @Description:
 */
@RequestMapping("/SSE")
@RestController
public class SSEController {
    @Autowired
    SSEService sseService;

    @RequestMapping(value = "/subscribe/{type}/{id}", method = RequestMethod.GET)
    public SseEmitter subscribe(@PathVariable String type, @PathVariable String id) throws IOException {
        return sseService.subscribe(type, id);
    }

    @RequestMapping(value = "/over/{type}/{id}", method = RequestMethod.GET)
    public JsonResult over(@PathVariable String type, @PathVariable String id) throws IOException {
        sseService.over(type, id);
        return ResultUtils.success();
    }
}
