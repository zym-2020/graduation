package nnu.edu.back.pojo.datagram;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nnu.edu.back.common.utils.DatagramMessageConverter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/24/15:27
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamConverter(DatagramMessageConverter.class)
public class DatagramMessage {
    private String address;
    private String port;
    private String time;
    private String value;
}
