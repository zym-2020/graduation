package nnu.edu.back.service.impl;

import nnu.edu.back.common.exception.MyException;
import nnu.edu.back.common.result.ResultEnum;
import nnu.edu.back.netty.TCPServer;
import nnu.edu.back.service.ReceiveRealTimeDataService;
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
    @Override
    @Async("asyncServiceExecutor")
    public void startTCPServer(int port) {
        TCPServer tcpServer = new TCPServer();
        cache.put(port, tcpServer);
        try {
            /**
             * 启动该方法会阻塞当前线程
             */
            tcpServer.bind(port);
        } catch (Exception e) {
            throw new MyException(ResultEnum.DEFAULT_EXCEPTION);
        }
    }

    @Override
    public void stopTCPServer(int port) {
        TCPServer tcpServer = cache.get(port);
        if (tcpServer != null) {
            tcpServer.stop();
            cache.remove(port);
        }
    }
}
