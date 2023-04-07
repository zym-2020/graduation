package nnu.edu.back.service.impl;

import com.alibaba.fastjson2.JSONObject;
import nnu.edu.back.common.exception.MyException;
import nnu.edu.back.common.result.ResultEnum;
import nnu.edu.back.common.utils.FileUtil;
import nnu.edu.back.common.utils.XmlUtil;
import nnu.edu.back.dao.manage.DeviceMapper;
import nnu.edu.back.pojo.Device;
import nnu.edu.back.pojo.config.DeviceConfig;
import nnu.edu.back.pojo.config.DeviceConfigAttribute;
import nnu.edu.back.pojo.config.Push;
import nnu.edu.back.service.DeviceManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/27/18:58
 * @Description:
 */
@Service
public class DeviceManageServiceImpl implements DeviceManageService {
    @Value("${picturePath}")
    String picturePath;

    @Value("${configPath}")
    String configPath;

    @Value("${typingDataPath}")
    String typingDataPath;

    @Value("${typingFilePath}")
    String typingFilePath;

    @Value("${dataPath}")
    String dataPath;

    @Autowired
    DeviceMapper deviceMapper;

    @Override
    public String uploadDevicePicture(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String pictureAddress = picturePath + uuid + suffix;
        FileUtil.uploadPicture(file, pictureAddress);
        return uuid;
    }

    @Override
    public void initDevice(DeviceConfig deviceConfig) {
        String uuid = UUID.randomUUID().toString();
        deviceConfig.setId(uuid);
        String xmlString = XmlUtil.toXml(deviceConfig);
        try {
            FileUtil.writeFile(configPath + uuid + ".xml", xmlString);
        } catch (Exception e) {
            throw new MyException(ResultEnum.FILE_READ_OR_WRITE_ERROR);
        }
        DeviceConfigAttribute attribute = deviceConfig.getDeviceConfigAttribute();
        Push push = deviceConfig.getPush();
        deviceMapper.insertDevice(new Device(uuid, attribute.getName(), attribute.getPicture(), attribute.getLongitude(), attribute.getLatitude(), attribute.getDescription(), push == null ? null : Integer.valueOf(push.getPort()), -1, null, false));
    }

    @Override
    public List<Device> getAllDevice() {
        return deviceMapper.getAllDevice();
    }

    @Override
    public void getPicture(String pictureName, HttpServletResponse response) {
        File file = new File(picturePath + pictureName);
        if (!file.exists()) {
            throw new MyException(ResultEnum.NO_OBJECT);
        }
        try {
            InputStream inputStream = new FileInputStream(file);
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            throw new MyException(ResultEnum.FILE_READ_OR_WRITE_ERROR);
        }
    }

    @Override
    public Map<String, Object> getDeviceInfo(String deviceId) {
        Map<String, Object> result = new HashMap<>();
        Device device = deviceMapper.queryDeviceById(deviceId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", device.getState());
        jsonObject.put("lastUpdate", device.getLastUpdate());
        result.put("status", jsonObject);
        File file = new File(configPath + deviceId + ".xml");
        if (!file.exists()) {
            throw new MyException(ResultEnum.NO_OBJECT);
        }
        DeviceConfig deviceConfig = XmlUtil.fromXml(file, DeviceConfig.class);
        result.put("device", deviceConfig);

        return result;
    }

    @Override
    public List<Map<String, Object>> getDeviceData(String deviceId, String path) {
        String destination;
        if (path.equals("/")) {
            destination = dataPath + deviceId;
        } else {
            destination = dataPath + deviceId + "/" + path;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        File file = new File(destination);
        List<Map<String, Object>> result = new ArrayList<>();
        File[] files = file.listFiles();
        for (File f : files) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", f.getName());
            map.put("lastUpdate", dateFormat.format(new Date(f.lastModified())));
            if (f.isFile()) {
                map.put("type", "file");
                map.put("size", f.length());
            } else {
                map.put("type", "folder");
            }
            result.add(map);
        }

        return result;
    }

}
