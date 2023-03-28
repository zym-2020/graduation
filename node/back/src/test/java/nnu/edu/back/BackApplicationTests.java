package nnu.edu.back;

import nnu.edu.back.common.utils.FileUtil;
import nnu.edu.back.dao.manage.DeviceMapper;
import nnu.edu.back.pojo.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class BackApplicationTests {
    @Autowired
    DeviceMapper deviceMapper;

    @Test
    void contextLoads() {
        deviceMapper.insertDevice(new Device("ec3555c3-60cf-c233-9340-f00d19c77b9d", null, null, null, null, null, null));
    }

}
