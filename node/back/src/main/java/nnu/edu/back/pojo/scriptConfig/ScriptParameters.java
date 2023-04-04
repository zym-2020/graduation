package nnu.edu.back.pojo.scriptConfig;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/04/04/16:43
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScriptParameters {
    @XStreamImplicit(itemFieldName = "parameter")
    List<ScriptParameter> parameterList;
}
