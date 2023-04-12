package nnu.edu.back.controller;

import com.alibaba.fastjson2.JSONObject;
import nnu.edu.back.common.result.JsonResult;
import nnu.edu.back.common.result.ResultUtils;
import nnu.edu.back.pojo.config.DeviceConfig;
import nnu.edu.back.service.DeviceManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/27/18:57
 * @Description:
 */
@RestController
@RequestMapping("/device")
public class DeviceManageController {
    @Autowired
    DeviceManageService deviceManageService;

    @RequestMapping(value = "/uploadPicture", method = RequestMethod.POST)
    public JsonResult uploadPicture(@RequestParam MultipartFile file) {
        return ResultUtils.success(deviceManageService.uploadDevicePicture(file));
    }

    @RequestMapping(value = "/initDevice", method = RequestMethod.POST)
    public JsonResult initDevice(@RequestBody DeviceConfig deviceConfig) {
        deviceManageService.initDevice(deviceConfig);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/getAllDevice", method = RequestMethod.GET)
    public JsonResult getAllDevice() {
        return ResultUtils.success(deviceManageService.getAllDevice());
    }

    @CrossOrigin
    @RequestMapping(value = "/getPicture/{pictureName}", method = RequestMethod.GET)
    public void getPicture(@PathVariable String pictureName, HttpServletResponse response) {
        deviceManageService.getPicture(pictureName, response);
    }

    @RequestMapping(value = "/getDeviceInfo/{deviceId}", method = RequestMethod.GET)
    public JsonResult getDeviceInfo(@PathVariable String deviceId) {
        return ResultUtils.success(deviceManageService.getDeviceInfo(deviceId));
    }

    @RequestMapping(value = "/getDeviceData/{deviceId}", method = RequestMethod.POST)
    public JsonResult getDeviceData(@PathVariable String deviceId, @RequestBody JSONObject jsonObject) {
        return ResultUtils.success(deviceManageService.getDeviceData(deviceId, jsonObject.getString("path")));
    }

    @RequestMapping(value = "/getDeviceFolder", method = RequestMethod.POST)
    public JsonResult getDeviceFolder(@RequestBody JSONObject jsonObject) {
        return ResultUtils.success(deviceManageService.getDeviceFolder(jsonObject.getString("deviceId"), jsonObject.getString("path")));
    }

    @RequestMapping(value = "/createFolder/{deviceId}", method = RequestMethod.POST)
    public JsonResult createFolder(@PathVariable String deviceId, @RequestBody JSONObject jsonObject) {
        return ResultUtils.success(deviceManageService.createFolder(deviceId, jsonObject.getString("path"), jsonObject.getString("folder")));
    }
}
