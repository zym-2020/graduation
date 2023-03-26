package nnu.edu.back.proj.typingFile;

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
 * @Date: 2023/03/26/16:42
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("typingFile")
public class TypingFile {
    @XStreamAsAttribute
    String id;
    @XStreamAsAttribute
    String date;

    @XStreamImplicit(itemFieldName = "map")
    List<TypingFileMap> maps;
}
