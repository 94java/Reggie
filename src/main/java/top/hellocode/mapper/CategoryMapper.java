package top.hellocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.hellocode.entity.Category;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月08日 14:57
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
