package nnu.edu.back.pojo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/24/20:49
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class DeviceConfigAttribute {
    private String name;
    private String model;
    private ProductParameters productParameters;
    private String description;
    private Float longitude;
    private Float latitude;
    private String watercourse;
    private String department;
    private String picture;
}
