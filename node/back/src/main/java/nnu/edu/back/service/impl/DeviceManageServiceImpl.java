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

    @Value("${datagramPath}")
    String datagramPath;

    @Value("${typingDataPath}")
    String typingDataPath;

    @Value("${typingFilePath}")
    String typingFilePath;

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
        deviceMapper.insertDevice(new Device(uuid, attribute.getName(), attribute.getPicture(), attribute.getLongitude(), attribute.getLatitude(), attribute.getDescription(), push == null ? null : Integer.valueOf(push.getPort()), -1, null));
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
    public Map<String, Object> getDeviceData(String deviceId) {
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
        File f = null;
        if (deviceConfig.getPush() != null) {
            f = new File(datagramPath + deviceId);
        }
        if (deviceConfig.getTyping() != null) {
            if (deviceConfig.getTyping().getType().equals("input")) {
                f = new File(typingDataPath + deviceId);
            } else {
                f = new File(typingFilePath + deviceId);
            }
        }
        if (f == null || !f.exists()) {
            result.put("data", new ArrayList<>());
            return result;
        }
        String[] files = f.list();
        List<String> list = new ArrayList<>();
        for (String fileName : files) {
            if (!fileName.equals("file")) {
                list.add(fileName.substring(0, fileName.lastIndexOf(".")));
            }
        }
        result.put("data", list);
        return result;
    }
}
