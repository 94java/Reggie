package top.hellocode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hellocode.entity.ShoppingCart;
import top.hellocode.mapper.ShoppingCartMapper;
import top.hellocode.service.IShoppingCartService;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月11日 15:02
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {
}
