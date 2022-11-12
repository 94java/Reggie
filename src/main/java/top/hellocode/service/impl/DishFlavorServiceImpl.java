package top.hellocode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hellocode.entity.DishFlavor;
import top.hellocode.mapper.DishFlavorMapper;
import top.hellocode.service.IDishFlavorService;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月08日 19:55
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements IDishFlavorService {
}
