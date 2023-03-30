package nnu.edu.back.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/29/20:07
 * @Description:
 */
public interface SSEService {
    SseEmitter subscribe(String type, String id) throws IOException;

    void over(String type, String id) throws IOException;

    void message(String type, String id, String content) throws IOException;

    void broadcastAll(String content) throws IOException;
}
