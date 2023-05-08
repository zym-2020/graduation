package nnu.edu.back;

import nnu.edu.back.pojo.Device;
import nnu.edu.back.service.ReceiveRealTimeDataService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAsync
@EnableScheduling
@MapperScan("nnu.edu.back.dao.*")
public class BackApplication implements CommandLineRunner {


    @Autowired
    ReceiveRealTimeDataService receiveRealTimeDataService;

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

    @Override
    @Async("asyncServiceExecutor")
    public void run(String... args) throws Exception {
        receiveRealTimeDataService.initAllDevice();

    }
}
