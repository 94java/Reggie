package top.hellocode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.hellocode.dto.DishDto;
import top.hellocode.entity.Dish;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月08日 15:19
 */
public interface IDishService extends IService<Dish> {
    /**
     * @Description: 新增菜品，同时插入菜品对应的口味数据
     * @param: dishDto
     * @return: void
     **/
    public void saveWithFlavor(DishDto dishDto);

    /**
     * @Description: 根据菜品id查询菜品dto信息
     * @param: id
     * @return DishDto
     **/
    public DishDto getWithFlavor(Long id);

    /**
     * @Description: 修改菜品和口味信息
     * @param: dishDto
     * @return void
     **/
    public void updateWithFlavor(DishDto dishDto);
}
