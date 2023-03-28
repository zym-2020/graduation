package nnu.edu.back.pojo.typingData;

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
 * @Date: 2023/03/25/21:16
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("typingData")
public class TypingData {
    @XStreamAsAttribute
    String id;

    @XStreamAsAttribute
    String date;

    @XStreamImplicit(itemFieldName = "content")
    List<TypingDataContent> typingDataContents;
}
