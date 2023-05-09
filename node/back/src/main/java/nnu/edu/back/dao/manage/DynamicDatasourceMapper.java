package nnu.edu.back.dao.manage;

import nnu.edu.back.pojo.Datasource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/05/08/10:06
 * @Description:
 */
@Repository
public interface DynamicDatasourceMapper {
    void addDatasource(@Param("datasource") Datasource datasource);

    Datasource queryDatasourceById(@Param("deviceId") String deviceId);

    List<String> queryAllDeviceId();
}
