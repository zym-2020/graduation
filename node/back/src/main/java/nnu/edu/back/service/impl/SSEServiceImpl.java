package nnu.edu.back.service.impl;

import nnu.edu.back.service.SSEService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/29/20:08
 * @Description:
 */
@Service
public class SSEServiceImpl implements SSEService {
    /**
     * 两个不同页面的cache
     */
    private static Map<String, SseEmitter> sseCache = new ConcurrentHashMap<>();
    /**
     * 具体的详情页面cache
     */
    private static Map<String, SseEmitter> sseDeviceCache = new ConcurrentHashMap<>();

    @Override
    public SseEmitter subscribe(String type, String id) throws IOException {
        SseEmitter sseEmitter = new SseEmitter(1000 * 60 * 60l);
        sseEmitter.send(SseEmitter.event().name("start").data("start..."));
        if (type.equals("all")) {
            sseCache.put(id, sseEmitter);
            sseEmitter.onTimeout(() -> {
                sseEmitter.complete();
                sseCache.remove(id);
            });
        } else if (type.equals("detail")) {
            sseDeviceCache.put(id, sseEmitter);
            sseEmitter.onTimeout(() -> {
                sseEmitter.complete();
                sseDeviceCache.remove(id);
            });
        }

        return sseEmitter;
    }


    @Override
    public void over(String type, String id) throws IOException {
        if (type.equals("all")) {
            SseEmitter sseEmitter = sseCache.get(id);
            if (sseEmitter != null) {
                sseEmitter.send(SseEmitter.event().name("stop").data("stop!"));
                sseEmitter.complete();
                sseCache.remove(id);
            }
        } else if (type.equals("detail")) {
            SseEmitter sseEmitter = sseDeviceCache.get(id);
            if (sseEmitter != null) {
                sseEmitter.send(SseEmitter.event().name("stop").data("stop!"));
                sseEmitter.complete();
                sseDeviceCache.remove(id);
            }
        }

    }

    @Override
    public void message(String type, String id, String content) throws IOException {
        if (type.equals("all")) {
            SseEmitter sseEmitter = sseCache.get(id);
            if (sseEmitter != null) {
                sseEmitter.send(SseEmitter.event().name("msg").data(content));
            }
        } else if (type.equals("detail")) {
            SseEmitter sseEmitter = sseDeviceCache.get(id);
            if (sseEmitter != null) {
                sseEmitter.send(SseEmitter.event().name("msg").data(content));
            }
        }

    }

    @Override
    public void broadcastAll(String content) throws IOException {
        for (SseEmitter sseEmitter : sseCache.values()) {
            sseEmitter.send(SseEmitter.event().name("msg").data(content));
        }
    }
}
