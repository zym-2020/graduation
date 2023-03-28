package nnu.edu.back.pojo.datagram;

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
 * @Date: 2023/03/24/11:25
 * @Description:
 */
@Data
@XStreamAlias("datagram")
@AllArgsConstructor
@NoArgsConstructor
public class Datagram {
    @XStreamAlias("id")
    @XStreamAsAttribute
    private String id;


    @XStreamAlias("type")
    @XStreamAsAttribute
    private String type;

    @XStreamAlias("listen")
    @XStreamAsAttribute
    private String listen;

    @XStreamAlias("date")
    @XStreamAsAttribute
    private String date;

    @XStreamImplicit(itemFieldName = "message")
    private List<DatagramMessage> messages;


}
