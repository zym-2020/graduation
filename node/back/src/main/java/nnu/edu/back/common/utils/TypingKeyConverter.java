package nnu.edu.back.common.utils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import nnu.edu.back.pojo.typingData.TypingKey;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/25/21:21
 * @Description:
 */
public class TypingKeyConverter implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        TypingKey typingKey = (TypingKey) o;
        hierarchicalStreamWriter.addAttribute("name", typingKey.getName());
        hierarchicalStreamWriter.setValue(typingKey.getValue());
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        TypingKey typingKey = new TypingKey();
        typingKey.setName(hierarchicalStreamReader.getAttribute("name"));
        typingKey.setValue(hierarchicalStreamReader.getValue());
        return typingKey;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(TypingKey.class);
    }
}
