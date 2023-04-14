package nnu.edu.back.pojo.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/04/04/16:33
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionStep {
    @XStreamAsAttribute
    String id;
    String script;
    String scriptName;

    @XStreamAlias("parameters")
    ActionParameters parameters;

}
