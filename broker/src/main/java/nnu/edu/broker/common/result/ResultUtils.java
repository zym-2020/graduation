package nnu.edu.broker.common.result;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/30/10:48
 * @Description:
 */
public class ResultUtils {
    public static JsonResult success(Object obj) {
        return new JsonResult(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), obj);
    }

    public static JsonResult success() {
        return success(null);
    }

    public static JsonResult fail(ResultEnum resultEnum) {
        return new JsonResult(resultEnum.getCode(), resultEnum.getMsg(), null);
    }
    public static JsonResult fail(Integer code, String msg) {
        return new JsonResult(code, msg, null);
    }
}
