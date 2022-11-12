package top.hellocode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hellocode.entity.SetmealDish;
import top.hellocode.mapper.SetmealDishMapper;
import top.hellocode.service.ISetmealDishService;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月10日 20:16
 */
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements ISetmealDishService {
}
