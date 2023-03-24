package nnu.edu.back.proj.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nnu.edu.back.common.utils.ParameterConverter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/24/20:57
 * @Description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamConverter(value = ParameterConverter.class)
public class Parameter {
    private String name;
    private String value;
}
