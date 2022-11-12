package top.hellocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.hellocode.entity.ShoppingCart;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月11日 15:01
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
