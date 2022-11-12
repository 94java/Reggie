package top.hellocode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.cache.TransactionalCacheManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import top.hellocode.dto.DishDto;
import top.hellocode.entity.Dish;
import top.hellocode.entity.DishFlavor;
import top.hellocode.mapper.DishMapper;
import top.hellocode.service.IDishFlavorService;
import top.hellocode.service.IDishService;

import java.util.List;
import java.util.Objects;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月08日 15:20
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {
    @Autowired
    private IDishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        // 保存菜品基本信息
        this.save(dishDto);
        // 获取口味信息
        List<DishFlavor> flavors = dishDto.getFlavors();
        // 为每个收集到的口味绑定菜品id
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishDto.getId());
        }
        // 添加口味信息
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public DishDto getWithFlavor(Long id) {
        DishDto dishDto = new DishDto();
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(!Objects.isNull(id),DishFlavor::getDishId,id);
        List<DishFlavor> flavorList = dishFlavorService.list(lqw);
        Dish dish = this.getById(id);
        dishDto.setFlavors(flavorList);
        BeanUtils.copyProperties(dish,dishDto);
        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        // 修改菜品基本信息
        this.updateById(dishDto);
        // 删除关联口味信息
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(lqw);
        // 重新添加口味关联信息
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishDto.getId());
        }
        dishFlavorService.saveBatch(flavors);
    }
}
