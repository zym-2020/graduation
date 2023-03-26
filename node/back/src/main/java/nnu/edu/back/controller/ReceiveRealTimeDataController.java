package nnu.edu.back.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import nnu.edu.back.common.result.JsonResult;
import nnu.edu.back.common.result.ResultUtils;
import nnu.edu.back.service.ReceiveRealTimeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(value = "/startTCPServer/{port}/{deviceId}", method = RequestMethod.POST)
    public JsonResult startTCPServer(@PathVariable int port, @PathVariable String deviceId) throws InterruptedException {
        receiveRealTimeDataService.startTCPServer(port, deviceId);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/stopTCPServer/{port}", method = RequestMethod.POST)
    public JsonResult stopTCPServer(@PathVariable int port) {
        receiveRealTimeDataService.stopTCPServer(port);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/startUDPServer/{port}", method = RequestMethod.POST)
    public JsonResult startUDPServer(@PathVariable int port) {
        receiveRealTimeDataService.startUDPServer(port);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/stopUDPServer/{port}", method = RequestMethod.POST)
    public JsonResult stopUDPServer(@PathVariable int port) {
        receiveRealTimeDataService.stopUDPServer(port);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/typingData/{deviceId}", method = RequestMethod.POST)
    public JsonResult typingData(@PathVariable String deviceId, @RequestBody JSONArray jsonArray) {
        receiveRealTimeDataService.typingData(deviceId, jsonArray);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/typingFileUpload/{tempId}/{fileName}", method = RequestMethod.POST)
    public JsonResult typingFileUpload(@PathVariable String tempId, @PathVariable String fileName, @RequestParam MultipartFile file) {
        receiveRealTimeDataService.typingFileUpload(tempId, file, fileName);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/typingFileMerge/{tempId}/{deviceId}/{fileCount}/{fileName}", method = RequestMethod.POST)
    public JsonResult typingFileMerge(@PathVariable String tempId, @PathVariable String deviceId, @PathVariable int fileCount, @PathVariable String fileName) {
        receiveRealTimeDataService.typingFileMerge(tempId, deviceId, fileCount, fileName);
        return ResultUtils.success();
    }
}