package nnu.edu.back.service.impl;

import com.alibaba.fastjson2.JSONObject;
import nnu.edu.back.dao.manage.DynamicDatasourceMapper;
import nnu.edu.back.pojo.Datasource;
import nnu.edu.back.service.DynamicDatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/05/08/10:17
 * @Description:
 */
@Service
public class DynamicDatasourceServiceImpl implements DynamicDatasourceService {
    @Autowired
    DynamicDatasourceMapper dynamicDatasourceMapper;

    @Override
    public void addDatasource(JSONObject jsonObject) {
        String jdbcUrl = jsonObject.getString("jdbcUrl");
        String driverClass = jsonObject.getString("driverClass");
        String deviceId = jsonObject.getString("deviceId");
        Datasource datasource = new Datasource(null, jdbcUrl, driverClass, deviceId);
        dynamicDatasourceMapper.addDatasource(datasource);
    }
}
