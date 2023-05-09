package nnu.edu.back.dao.monitoring;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/28/11:02
 * @Description:
 */
@Repository
public interface MonitoringDataMapper {
    void insertMonitoringData(String deviceId, @Param("tableName") String tableName, @Param("id") String id, @Param("time") String time, @Param("data") byte[] bytes);

    void createTable(String deviceId, @Param("tableName") String tableName);
}
