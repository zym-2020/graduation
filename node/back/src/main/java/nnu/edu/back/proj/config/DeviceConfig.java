package nnu.edu.back.proj.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/24/20:47
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XStreamAlias("device")
public class DeviceConfig {
    @XStreamAlias("id")
    @XStreamAsAttribute
    String id;

    @XStreamAlias("attribute")
    DeviceConfigAttribute deviceConfigAttribute;

    @XStreamAlias("push")
    Push push;
}
