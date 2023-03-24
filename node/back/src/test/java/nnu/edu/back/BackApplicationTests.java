package nnu.edu.back;

import nnu.edu.back.common.utils.XmlUtil;
import nnu.edu.back.proj.config.DeviceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class BackApplicationTests {

    @Test
    void contextLoads() {
        String path = "D:\\zhuomian\\毕业\\node-manage\\config\\a5f014b1-84fb-4e25-b705-7ca32c0c6d0c.xml";
        DeviceConfig deviceConfig = XmlUtil.fromXml(new File(path), DeviceConfig.class);
        System.out.println(deviceConfig);
    }

}
