package nnu.edu.back.common.utils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import nnu.edu.back.proj.datagram.DatagramMessage;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/24/15:28
 * @Description:
 */
public class DatagramMessageConverter implements Converter {

    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        DatagramMessage datagramMessage = (DatagramMessage) o;
        hierarchicalStreamWriter.addAttribute("address", datagramMessage.getAddress());
        hierarchicalStreamWriter.addAttribute("port", datagramMessage.getPort());
        hierarchicalStreamWriter.addAttribute("time", datagramMessage.getTime());
        hierarchicalStreamWriter.setValue(datagramMessage.getValue());
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        DatagramMessage datagramMessage = new DatagramMessage();
        datagramMessage.setAddress(hierarchicalStreamReader.getAttribute("address"));
        datagramMessage.setPort(hierarchicalStreamReader.getAttribute("port"));
        datagramMessage.setTime(hierarchicalStreamReader.getAttribute("time"));
        datagramMessage.setValue(hierarchicalStreamReader.getValue());
        return datagramMessage;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(DatagramMessage.class);
    }
}
