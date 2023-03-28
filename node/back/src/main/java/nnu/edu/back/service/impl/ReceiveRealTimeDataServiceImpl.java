package nnu.edu.back.service.impl;

import com.alibaba.fastjson2.JSONArray;
import nnu.edu.back.common.exception.MyException;
import nnu.edu.back.common.result.ResultEnum;
import nnu.edu.back.common.utils.FileUtil;
import nnu.edu.back.common.utils.XmlUtil;
import nnu.edu.back.dao.manage.DeviceMapper;
import nnu.edu.back.netty.TCPServer;
import nnu.edu.back.netty.UDPServer;
import nnu.edu.back.pojo.Device;
import nnu.edu.back.pojo.config.DeviceConfig;
import nnu.edu.back.pojo.config.Typing;
import nnu.edu.back.pojo.typingData.TypingDataContent;
import nnu.edu.back.pojo.typingData.TypingData;
import nnu.edu.back.pojo.typingData.TypingKey;
import nnu.edu.back.pojo.typingFile.TypingFile;
import nnu.edu.back.pojo.typingFile.TypingFileMap;
import nnu.edu.back.service.ReceiveRealTimeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/23/22:56
 * @Description:
 */
@Service
public class ReceiveRealTimeDataServiceImpl implements ReceiveRealTimeDataService {
    private static Map<Integer, TCPServer> cache = new ConcurrentHashMap<>();
    private static Map<Integer, UDPServer> udpCache = new ConcurrentHashMap<>();

    @Value("${configPath}")
    String configPath;

    @Value("${typingDataPath}")
    String typingDataPath;

    @Value("${tempPath}")
    String tempPath;

    @Value("${typingFilePath}")
    String typingFilePath;

    @Autowired
    DeviceMapper deviceMapper;

    @Override
    @Async("asyncServiceExecutor")
    public void startTCPServer(int port, String deviceId) throws InterruptedException {
        TCPServer tcpServer = new TCPServer(configPath + deviceId + ".xml");
        cache.put(port, tcpServer);
        /**
         * 启动该方法会阻塞当前线程
         */
        tcpServer.bind(port);
    }

    @Override
    public void stopTCPServer(int port) {
        TCPServer tcpServer = cache.get(port);
        if (tcpServer != null) {
            tcpServer.stop();
            cache.remove(port);
        }
    }

    @Async("asyncServiceExecutor")
    @Override
    public void startUDPServer(int port) {
        UDPServer udpServer = new UDPServer();
        udpCache.put(port, udpServer);
        udpServer.bind(port);
    }

    @Override
    public void stopUDPServer(int port) {
        UDPServer udpServer = udpCache.get(port);
        if (udpServer != null) {
            udpServer.stop();
            udpCache.remove(port);
        }
    }

    @Override
    public void typingData(String deviceId, JSONArray jsonArray) {
        String path = configPath + deviceId + ".xml";
        File file = new File(path);
        if (!file.exists()) {
            throw new MyException(ResultEnum.NO_OBJECT);
        }
        DeviceConfig deviceConfig = XmlUtil.fromXml(file, DeviceConfig.class);
        Typing typing = deviceConfig.getTyping();
        if (typing == null || !typing.getType().equals("input")) {
            throw new MyException(ResultEnum.CONFIG_ERROR);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        List<TypingDataContent> typingDataContents = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            TypingDataContent typingDataContent = new TypingDataContent();
            typingDataContent.setTime(timeFormat.format(new Date()));
            List<TypingKey> keys = new ArrayList<>();
            for (String key : typing.getKeys()) {
                keys.add(new TypingKey(key, jsonArray.getJSONObject(i).getString(key)));
            }
            typingDataContent.setKeys(keys);
            typingDataContents.add(typingDataContent);
        }
        String dataPath = typingDataPath + deviceId + "/" + dateFormat.format(new Date()) + ".xml";
        File dataFile = new File(dataPath);
        if (dataFile.exists()) {
            TypingData typingData = XmlUtil.fromXml(dataFile, TypingData.class);
            typingData.getTypingDataContents().addAll(typingDataContents);
            String xmlString = XmlUtil.toXml(typingData);
            try {
                FileUtil.writeFile(dataPath, xmlString);
            } catch (Exception e) {
                throw new MyException(ResultEnum.FILE_READ_OR_WRITE_ERROR);
            }
        } else {
            TypingData typingData = new TypingData();
            typingData.setDate(dateFormat.format(new Date()));
            typingData.setId(deviceId);
            typingData.setTypingDataContents(typingDataContents);
            String xmlString = XmlUtil.toXml(typingData);
            try {
                FileUtil.writeFile(dataPath, xmlString);
            } catch (Exception e) {
                throw new MyException(ResultEnum.FILE_READ_OR_WRITE_ERROR);
            }
        }

    }

    @Override
    public void typingFileUpload(String tempId, MultipartFile multipartFile, String fileName) {
        String address = tempPath + tempId;
        try {
            FileUtil.multipartUpload(address, multipartFile, fileName);
        } catch (Exception e) {
            throw new MyException(ResultEnum.FILE_READ_OR_WRITE_ERROR);
        }
    }

    @Override
    public void typingFileMerge(String tempId, String deviceId, int count, String fileName) {
        DeviceConfig deviceConfig = XmlUtil.fromXml(new File(configPath + deviceId), DeviceConfig.class);
        if (!deviceConfig.getTyping().getType().equals("file")) {
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String uid = UUID.randomUUID().toString();
            try {
                FileUtil.multipartMerge(typingDataPath + deviceId + "/file/" + uid + suffix, tempPath + tempId, count);
            } catch (Exception e) {
                throw new MyException(ResultEnum.FILE_READ_OR_WRITE_ERROR);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
            TypingFileMap typingFileMap = new TypingFileMap();
            typingFileMap.setTime(timeFormat.format(new Date()));
            typingFileMap.setName(fileName);
            typingFileMap.setValue(uid + suffix);

            String path = typingFilePath + deviceId + dateFormat.format(new Date()) + ".xml";
            File file = new File(path);
            TypingFile typingFile;
            if (file.exists()) {
                typingFile = XmlUtil.fromXml(file, TypingFile.class);
                typingFile.getMaps().add(typingFileMap);
            } else {
                typingFile = new TypingFile();
                List<TypingFileMap> typingFileMaps = new ArrayList<>();
                typingFileMaps.add(typingFileMap);
                typingFile.setMaps(typingFileMaps);
                typingFile.setId(deviceId);
                typingFile.setDate(dateFormat.format(new Date()));
            }
            String xmlString = XmlUtil.toXml(typingFile);
            try {
                FileUtil.writeFile(path, xmlString);
            } catch (Exception e) {
                e.printStackTrace();
                throw new MyException(ResultEnum.FILE_READ_OR_WRITE_ERROR);
            }
        } else {
            FileUtil.deleteFileOrFolder(new File(tempPath + tempId));
            throw new MyException(ResultEnum.CONFIG_ERROR);
        }
    }

    @Override
    public int checkPort(int port) {
        Device device = deviceMapper.queryByPort(port);
        if (device != null) {
            return -1;
        }
        String host = "localhost";
        try {
            Socket socket = new Socket(host, port);
            socket.close();
            return -1;
        } catch (Exception e) {
            return 0;
        }
    }
}
