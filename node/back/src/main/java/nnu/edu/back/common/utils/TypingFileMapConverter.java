package nnu.edu.back.common.utils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import nnu.edu.back.pojo.typingFile.TypingFileMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/26/16:51
 * @Description:
 */
public class TypingFileMapConverter implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        TypingFileMap typingFileMap = (TypingFileMap) o;
        hierarchicalStreamWriter.addAttribute("name", typingFileMap.getName());
        hierarchicalStreamWriter.addAttribute("time", typingFileMap.getTime());
        hierarchicalStreamWriter.setValue(typingFileMap.getValue());
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        TypingFileMap typingFileMap = new TypingFileMap();
        typingFileMap.setName(hierarchicalStreamReader.getAttribute("name"));
        typingFileMap.setTime(hierarchicalStreamReader.getAttribute("time"));
        typingFileMap.setValue(hierarchicalStreamReader.getValue());
        return typingFileMap;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(TypingFileMap.class);
    }
}
