package nnu.edu.back.proj.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/24/21:08
 * @Description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductParameters {
    @XStreamImplicit(itemFieldName = "parameter")
    private List<Parameter> parameters = new ArrayList<>();
}
