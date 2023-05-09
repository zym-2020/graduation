package nnu.edu.back.common.aspect;

import nnu.edu.back.common.config.DataSourceContextHolder;
import nnu.edu.back.common.config.DynamicDataSource;
import nnu.edu.back.dao.manage.DynamicDatasourceMapper;
import nnu.edu.back.pojo.Datasource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.sqlite.JDBC;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/05/08/9:34
 * @Description:
 */
@Order(1)
@Aspect
@Component
public class DynamicDataSourceAspect {
    @Value("databasePath")
    String databasePath;

    @Autowired
    DynamicDatasourceMapper dynamicDatasourceMapper;

    @Autowired
    @Qualifier("dynamicDataSource")
    private DynamicDataSource dynamicDataSource;

    /**
     * 切换数据源
     */
    @Before("execution(* nnu.edu.back.dao.monitoring.*.*(..))")
    public void switchDataSource(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String deviceId = (String) args[0];
        if (!DataSourceContextHolder.containDataSourceKey(deviceId)) {
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            Datasource datasource = dynamicDatasourceMapper.queryDatasourceById(deviceId);
            dataSourceBuilder.url("jdbc:sqlite:" + databasePath + datasource.getJdbcUrl());
            dataSourceBuilder.driverClassName(datasource.getDriverClass());
            DataSource source = dataSourceBuilder.build();
            dynamicDataSource.addDataSource(deviceId, source);
        }
        // 切换数据源
        DataSourceContextHolder.setDataSourceKey(deviceId);
    }

    /**
     * 重置数据源
     */
    @After("execution(* nnu.edu.back.dao.monitoring.*.*(..))")
    public void restoreDataSource() {
        // 将数据源置为默认数据源
        DataSourceContextHolder.clearDataSourceKey();
    }
}
