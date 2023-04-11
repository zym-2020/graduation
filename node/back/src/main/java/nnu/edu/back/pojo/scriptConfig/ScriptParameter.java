package nnu.edu.back.pojo.scriptConfig;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/04/04/16:44
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScriptParameter {
    @XStreamAsAttribute
    Boolean optional;
    String name;
    String description;
    String example;
    String type;
}
