package nnu.edu.back.service;

import nnu.edu.back.pojo.Device;
import nnu.edu.back.pojo.config.DeviceConfig;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/27/18:58
 * @Description:
 */
public interface DeviceManageService {
    String uploadDevicePicture(MultipartFile file);

    void initDevice(DeviceConfig deviceConfig);

    List<Device> getAllDevice();
}
