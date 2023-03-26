package nnu.edu.back;

import nnu.edu.back.common.utils.FileUtil;
import nnu.edu.back.common.utils.XmlUtil;
import nnu.edu.back.proj.config.DeviceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class BackApplicationTests {

    @Test
    void contextLoads() {
        String path = "D:\\zhuomian\\毕业\\node-manage\\temp\\1";
        File file = new File(path);
        FileUtil.deleteFileOrFolder(file);
    }

}
