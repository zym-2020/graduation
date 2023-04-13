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

    Map<String, Object> getDeviceInfo(String deviceId);

    List<Map<String, Object>> getDeviceData(String deviceId, String path);

    List<Map<String, Object>> getDeviceFolder(String deviceId, String path);

    List<Map<String, Object>> createFolder(String deviceId, String path, String folder);

    DeviceConfig updateActionParameter(String deviceId, String actionId, String stepId, List<String> parameters);

    DeviceConfig addStep(String deviceId, String actionId, String scriptId, List<String> parameters);
}
