package nnu.edu.back.proj.typingFile;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nnu.edu.back.common.utils.TypingFileMapConverter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/26/16:50
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamConverter(TypingFileMapConverter.class)
public class TypingFileMap {
    String time;
    String name;
    String value;
}
