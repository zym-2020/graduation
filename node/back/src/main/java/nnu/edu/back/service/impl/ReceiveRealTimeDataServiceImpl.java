package nnu.edu.back.service.impl;

import nnu.edu.back.common.exception.MyException;
import nnu.edu.back.common.result.ResultEnum;
import nnu.edu.back.netty.TCPServer;
import nnu.edu.back.netty.UDPServer;
import nnu.edu.back.service.ReceiveRealTimeDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
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
}
