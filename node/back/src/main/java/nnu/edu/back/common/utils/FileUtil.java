package nnu.edu.back.common.utils;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
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

    /**
    * @Description:处理临时文件
    * @Author: Yiming
    * @Date: 2023/4/5
    */
    public static void clearTempFile(String tempPath) {
        File f = new File(tempPath);
        File[] files = f.listFiles();
        long nowTime = System.currentTimeMillis();
        for (File file : files) {
            if (nowTime - 1000 * 60 * 60 * 24 > file.lastModified()) {
                deleteFileOrFolder(file);
            }
        }
    }

    /**
    * @Description:文件夹解压
    * @Author: Yiming
    * @Date: 2023/4/11
    */
    public static void unpack(String destination, String to) throws Exception {
        File file = new File(destination);
        if (!file.exists()) {
            throw new Exception();
        }
        ZipFile zipFile = new ZipFile(destination);
        zipFile.extractAll(to);
    }

    /**
    * @Description:从压缩包中获取某文件的文件流
    * @Author: Yiming
    * @Date: 2023/4/11
    */
    public static InputStream getZipStream(String destination, String destinationPath) throws IOException {
        ZipFile zipFile = new ZipFile(destination);
        FileHeader fileHeader = zipFile.getFileHeader(destinationPath);
        return zipFile.getInputStream(fileHeader);
    }
}
