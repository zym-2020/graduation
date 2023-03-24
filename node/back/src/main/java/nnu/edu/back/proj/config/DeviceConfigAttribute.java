package nnu.edu.back.proj.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private Double longitude;
    private Double latitude;
    private String watercourse;
    private String department;
}
