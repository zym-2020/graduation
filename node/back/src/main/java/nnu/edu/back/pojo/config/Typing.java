package nnu.edu.back.pojo.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/25/21:02
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Typing {
    @XStreamAsAttribute
    String type;

    @XStreamAlias("keys")
    TypingKeys keys;

    String storage;
}
