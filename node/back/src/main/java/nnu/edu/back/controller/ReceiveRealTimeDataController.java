package nnu.edu.back.controller;

import nnu.edu.back.common.result.JsonResult;
import nnu.edu.back.common.result.ResultUtils;
import nnu.edu.back.service.ReceiveRealTimeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/23/22:54
 * @Description:
 */
@RestController
@RequestMapping("/receiveRealTimeData")
public class ReceiveRealTimeDataController {
    @Autowired
    ReceiveRealTimeDataService receiveRealTimeDataService;

    @RequestMapping(value = "/startTCPServer/{port}", method = RequestMethod.POST)
    public JsonResult startTCPServer(@PathVariable int port) {
        receiveRealTimeDataService.startTCPServer(port);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/stopTCPServer/{port}", method = RequestMethod.POST)
    public JsonResult stopTCPServer(@PathVariable int port) {
        receiveRealTimeDataService.stopTCPServer(port);
        return ResultUtils.success();
    }
}
