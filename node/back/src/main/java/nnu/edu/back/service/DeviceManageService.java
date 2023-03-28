package nnu.edu.back.service;

import nnu.edu.back.proj.config.DeviceConfig;
import org.springframework.web.multipart.MultipartFile;

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
}
