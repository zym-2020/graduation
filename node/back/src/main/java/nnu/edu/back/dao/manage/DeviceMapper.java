package nnu.edu.back.dao.manage;

import nnu.edu.back.pojo.Device;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/28/10:59
 * @Description:
 */
@Repository
public interface DeviceMapper {
    Device queryByPort(@Param("port") int port);

    void insertDevice(@Param("device") Device device);

    List<Device> getAllDevice();

    Device queryDeviceById(@Param("id") String deviceId);

    void updateState(@Param("id") String deviceId, @Param("state") int state);

    void updateLastUpdate(@Param("id") String deviceId);
}
