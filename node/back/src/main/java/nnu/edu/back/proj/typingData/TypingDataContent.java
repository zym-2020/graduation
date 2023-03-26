package nnu.edu.back.proj.typingData;

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
 * @Date: 2023/03/25/21:19
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypingDataContent {
    @XStreamAsAttribute
    String time;

    @XStreamImplicit(itemFieldName = "key")
    List<TypingKey> keys;
}
