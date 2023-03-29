package nnu.edu.back.service;

import nnu.edu.back.pojo.Device;
import nnu.edu.back.pojo.config.DeviceConfig;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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

    void getPicture(String pictureName, HttpServletResponse response);

    Map<String, Object> getDeviceData(String deviceId);
}
