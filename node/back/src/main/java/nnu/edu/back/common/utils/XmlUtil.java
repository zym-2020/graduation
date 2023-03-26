package nnu.edu.back.common.utils;

import com.thoughtworks.xstream.XStream;
import nnu.edu.back.common.exception.MyException;
import nnu.edu.back.proj.config.DeviceConfig;
import nnu.edu.back.proj.datagram.Datagram;
import nnu.edu.back.proj.typingData.TypingData;
import nnu.edu.back.proj.typingFile.TypingFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/24/14:20
 * @Description:
 */
public class XmlUtil {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static String toXml(Object obj) {
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);    //自动发现注解
        return xStream.toXML(obj);
    }

    public static <T> T fromXml(InputStream is, Class<T> target) {
        XStream xStream = new XStream();
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(new Class[]{Datagram.class, DeviceConfig.class, TypingData.class, TypingFile.class});
        xStream.processAnnotations(target);
        return (T) xStream.fromXML(is);
    }

    public static <T> T fromXml(File file, Class<T> target) {
        XStream xStream = new XStream();
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(new Class[]{Datagram.class, DeviceConfig.class, TypingData.class, TypingFile.class});
        xStream.processAnnotations(target);
        return (T) xStream.fromXML(file);
    }

    public static <T> T fromXml(String xml, Class<T> target) {
        XStream xStream = new XStream();
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(new Class[]{Datagram.class, DeviceConfig.class, TypingData.class, TypingFile.class});
        xStream.processAnnotations(target);
        return (T) xStream.fromXML(xml);
    }
}
