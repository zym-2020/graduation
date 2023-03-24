package nnu.edu.back.common.utils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import nnu.edu.back.proj.config.Parameter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/24/21:00
 * @Description:
 */
public class ParameterConverter implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        Parameter parameter = (Parameter) o;
        hierarchicalStreamWriter.addAttribute("name", parameter.getName());
        hierarchicalStreamWriter.setValue(parameter.getValue());
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Parameter parameter = new Parameter();
        parameter.setName(hierarchicalStreamReader.getAttribute("name"));
        parameter.setValue(hierarchicalStreamReader.getValue());
        return parameter;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(Parameter.class);
    }
}
