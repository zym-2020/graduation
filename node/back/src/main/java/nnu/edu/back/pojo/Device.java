package nnu.edu.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/28/11:10
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    String id;
    String name;
    String picture;
    Float longitude;
    Float latitude;
    String description;
    Integer port;
    Integer state;
    Date lastUpdate;
    Boolean register;
    String tableName;
}
