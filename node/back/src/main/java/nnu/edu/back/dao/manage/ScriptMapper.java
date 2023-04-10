package nnu.edu.back.dao.manage;

import nnu.edu.back.pojo.Script;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/04/10/18:57
 * @Description:
 */
@Repository
public interface ScriptMapper {
    List<Script> pageQuery(@Param("size") int size, @Param("start") int start, @Param("keyword") String keyword);

    int countAll(@Param("keyword") String keyword);
}
