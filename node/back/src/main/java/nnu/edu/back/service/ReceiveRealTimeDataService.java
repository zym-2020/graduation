package nnu.edu.back.service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/23/22:56
 * @Description:
 */
public interface ReceiveRealTimeDataService {
    void startTCPServer(int port, String deviceId) throws InterruptedException;

    void stopTCPServer(int port);

    void startUDPServer(int port);

    void stopUDPServer(int port);
}
