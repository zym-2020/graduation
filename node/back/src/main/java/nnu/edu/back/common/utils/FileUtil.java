package nnu.edu.back.common.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/24/15:45
 * @Description:
 */
public class FileUtil {
    public static void writeFile(String filePath, String content) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        bw.write(content);
        bw.close();
    }
}
