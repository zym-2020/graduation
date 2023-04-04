package nnu.edu.back.pojo.scriptConfig;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/04/04/16:42
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XStreamAlias("script")
public class Script {
    @XStreamAsAttribute
    String id;
    String description;

    @XStreamAlias("parameters")
    ScriptParameters parameters;

    String enter;
}
