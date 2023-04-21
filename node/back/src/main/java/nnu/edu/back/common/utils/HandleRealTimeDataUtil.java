package nnu.edu.back.common.utils;

import nnu.edu.back.dao.monitoring.MonitoringDataMapper;
import nnu.edu.back.pojo.Device;
import nnu.edu.back.pojo.datagram.Datagram;
import nnu.edu.back.pojo.datagram.DatagramMessage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/24/14:48
 * @Description:处理推送到服务端的实时数据
 */
public class HandleRealTimeDataUtil {
    /**
    * @Description:正常保存为报文xml，不做任何处理
    * @Author: Yiming
    * @Date: 2023/3/24
    */
    public static void normalHandle(String fileName, String id, String listen, String type, String address, String port, String data) throws Exception {
        File file = new File(fileName);
        if (!file.exists()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            List<DatagramMessage> messages = new ArrayList<>();
            messages.add(new DatagramMessage(address, port, timeFormat.format(new Date()), data));
            Datagram datagram = new Datagram(id, type, listen, dateFormat.format(new Date()), messages);
            try {
                String content = XmlUtil.toXml(datagram);
                FileUtil.writeFile(fileName, content);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            try {
                Datagram datagram = XmlUtil.fromXml(file, Datagram.class);
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                datagram.getMessages().add(new DatagramMessage(address, port, timeFormat.format(new Date()), data));
                String content = XmlUtil.toXml(datagram);
                FileUtil.writeFile(fileName, content);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    public static void normalHandle(byte[] bytes, MonitoringDataMapper monitoringDataMapper, String tableName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String id = UUID.randomUUID().toString();
        String time = dateFormat.format(new Date());
        monitoringDataMapper.insertMonitoringData(tableName, id, time, bytes);
    }


    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while((len = inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

}
