package nnu.edu.back.common.aspect;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/29/20:53
 * @Description:
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PushNotice {
    String value();
}
