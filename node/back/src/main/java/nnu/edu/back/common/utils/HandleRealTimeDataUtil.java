package nnu.edu.back.common.utils;

import nnu.edu.back.pojo.Device;
import nnu.edu.back.pojo.datagram.Datagram;
import nnu.edu.back.pojo.datagram.DatagramMessage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

}
