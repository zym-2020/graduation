package nnu.edu.back.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/24/15:45
 * @Description:
 */
public class FileUtil {
    public static void writeFile(String filePath, String content) throws IOException {
        String path = filePath.substring(0, filePath.lastIndexOf("/"));
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        bw.write(content);
        bw.close();
    }

    /**
    * @Description:分片上传
    * @Author: Yiming
    * @Date: 2023/3/26
    */
    public static void multipartUpload(String address, MultipartFile file, String fileName) throws IOException {
        File f = new File(address);
        if (!f.exists()) {
            f.mkdirs();
        }
        try {
            InputStream is = file.getInputStream();
            OutputStream os = new FileOutputStream(address + "/" + fileName);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
    * @Description:分片文件合并，默认文件名为1，2，3，4
    * @Author: Yiming
    * @Date: 2023/3/26
    */
    public static void multipartMerge(String targetFile, String multipartAddress, int fileCount) throws IOException {
        try {
            FileChannel outChannel = new FileOutputStream(targetFile).getChannel();
            long start = 0;
            for (int i = 0; i < fileCount; i++) {
                FileChannel inChannel = new FileInputStream(multipartAddress + "/" + i).getChannel();
                outChannel.transferFrom(inChannel, start, inChannel.size());
                start +=inChannel.size();
                inChannel.close();
            }
            outChannel.close();
            deleteFileOrFolder(new File(multipartAddress));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
    * @Description:上传图片
    * @Author: Yiming
    * @Date: 2023/3/27
    */
    public static void uploadPicture(MultipartFile file, String pictureAddress) {
        try {
            InputStream is = file.getInputStream();
            OutputStream os = new FileOutputStream(pictureAddress);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            os.flush();
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean deleteFileOrFolder(File file) {
        if (!file.exists()) {
            return false;
        }
        if (!file.isFile()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (!deleteFileOrFolder(f)) {
                    return false;
                }
            }
        }
        return file.delete();
    }
}
