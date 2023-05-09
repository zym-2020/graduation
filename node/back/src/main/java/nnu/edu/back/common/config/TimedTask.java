package nnu.edu.back.common.config;

import nnu.edu.back.common.utils.FileUtil;
import nnu.edu.back.dao.manage.DynamicDatasourceMapper;
import nnu.edu.back.dao.monitoring.MonitoringDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    DynamicDatasourceMapper dynamicDatasourceMapper;

    @Autowired
    MonitoringDataMapper monitoringDataMapper;

    @Scheduled(cron = "0 0 0 * * ?")
    public void clearTempFile() {
        FileUtil.clearTempFile(tempPath);
    }

    @Scheduled(cron = "0 0 0 30 12,3,6,9 ?")
    public void createTable() {
        String tableName;
        LocalDateTime currentDate = LocalDateTime.now();
        int year = currentDate.getYear();
        int monthValue = currentDate.getMonthValue();
        if (monthValue == 12) {
            tableName = "t" + year + 1 + "13";
        } else if (monthValue == 3) {
            tableName = "t" + year + "46";
        } else if (monthValue == 6) {
            tableName = "t" + year + "79";
        } else {
            tableName = "t" + year + "1012";
        }
        List<String> deviceIdList = dynamicDatasourceMapper.queryAllDeviceId();
        for (String s : deviceIdList) {
            monitoringDataMapper.createTable(s, tableName);
        }
    }
}
