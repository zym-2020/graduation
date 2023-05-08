package nnu.edu.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/05/08/9:57
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Datasource {
    String id;
    String jdbcUrl;
    String driverClass;
    String userName;
    String password;
    String deviceId;
}
