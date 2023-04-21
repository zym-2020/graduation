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


    @Test
    void contextLoads() {
        System.out.println("device" + System.currentTimeMillis());
    }

}
