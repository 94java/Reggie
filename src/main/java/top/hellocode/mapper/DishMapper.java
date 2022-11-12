package top.hellocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.hellocode.entity.Dish;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月08日 15:18
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
