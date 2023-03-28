package nnu.edu.back.pojo.typingData;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nnu.edu.back.common.utils.TypingKeyConverter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/25/21:20
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamConverter(TypingKeyConverter.class)
public class TypingKey {
    String name;
    String value;
}
