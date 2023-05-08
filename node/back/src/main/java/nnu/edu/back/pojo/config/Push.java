package nnu.edu.back.pojo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/24/21:13
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Push {
    String protocol;
    String port;
}
