package nnu.edu.back.controller;

import nnu.edu.back.common.result.JsonResult;
import nnu.edu.back.common.result.ResultUtils;
import nnu.edu.back.proj.config.DeviceConfig;
import nnu.edu.back.service.DeviceManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
