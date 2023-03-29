package nnu.edu.back.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/23/22:56
 * @Description:
 */
public interface ReceiveRealTimeDataService {
    void startTCPServer(int port, String deviceId) throws InterruptedException;

    void stopTCPServer(int port, String deviceId);

    void startUDPServer(int port);

    void stopUDPServer(int port);

    void typingData(String deviceId, JSONArray jsonArray);

    void typingFileUpload(String tempId, MultipartFile multipartFile, String fileName);

    void typingFileMerge(String tempId, String deviceId, int count, String fileName);

    int checkPort(int port);
}
