package top.hellocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.hellocode.entity.User;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月11日 14:17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
