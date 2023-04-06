package nnu.edu.back.common.config;

import nnu.edu.back.common.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/04/05/17:17
 * @Description:
 */
@Component
public class TimedTask {
    @Value("${tempPath}")
    String tempPath;

    @Scheduled(cron = "0 0 * * * ?")
    public void clearTempFile() {
        FileUtil.clearTempFile(tempPath);
    }
}
