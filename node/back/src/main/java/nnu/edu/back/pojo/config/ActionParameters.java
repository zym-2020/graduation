package nnu.edu.back.pojo.config;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/04/04/16:34
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionParameters {
    @XStreamImplicit(itemFieldName = "parameter")
    List<String> parameterList;
}
