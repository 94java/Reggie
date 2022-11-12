package top.hellocode.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.hellocode.common.R;
import top.hellocode.dto.DishDto;
import top.hellocode.entity.Category;
import top.hellocode.entity.Dish;
import top.hellocode.entity.DishFlavor;
import top.hellocode.service.ICategoryService;
import top.hellocode.service.IDishFlavorService;
import top.hellocode.service.IDishService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月08日 19:45
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private IDishService dishService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IDishFlavorService dishFlavorService;

    @PostMapping
    public R<String> add(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("添加菜品成功");
    }

    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name){
        // 分页查询dish信息
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(!StringUtils.isEmpty(name),Dish::getName,name);
        lqw.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo, lqw);
        // 构造DishDto对象，封装分类名称
        Page<DishDto> dishDtoPage = new Page<>();
        List<Dish> dishList = pageInfo.getRecords();
        List<DishDto> dtoList = new ArrayList<>();
        // 循环遍历，查询分类名称
        for (Dish dish : dishList) {
            DishDto dishDto = new DishDto();
            Long categoryId = dish.getCategoryId();
            Category category = categoryService.getById(categoryId);
            dishDto.setCategoryName(category.getName());
            BeanUtils.copyProperties(dish,dishDto);
            dtoList.add(dishDto);
        }
        // 对象拷贝，完成赋值
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        dishDtoPage.setRecords(dtoList);
        return R.success(dishDtoPage);
    }
    @GetMapping("/{id}")
    public R<DishDto> getOne(@PathVariable Long id){
        DishDto dishDto = dishService.getWithFlavor(id);
        return R.success(dishDto);
    }
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, Long[] ids){
        for (Long id : ids) {
            Dish dish = new Dish();
            dish.setStatus(status);
            dish.setId(id);
            dishService.updateById(dish);
        }
        return R.success("状态修改成功");
    }

    @DeleteMapping
    public R<String> delete(Long[] ids){
        dishService.removeByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R<List> list(Dish dish){
        // 查询菜品基本信息
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId())
                .eq(Dish::getStatus,1)
                .orderByAsc(Dish::getSort);
        List<Dish> list = dishService.list(queryWrapper);
        // 查询口味基本信息
        List<DishDto> dtoList = new ArrayList<>();
        for (Dish dishInfo : list) {
            // 新建Dto传输对象
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dishInfo,dishDto);
            // 查询口味信息
            LambdaQueryWrapper<DishFlavor> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(DishFlavor::getDishId,dishInfo.getId());
            List<DishFlavor> flavorList = dishFlavorService.list(queryWrapper1);
            dishDto.setFlavors(flavorList);
            dtoList.add(dishDto);
        }
        return R.success(dtoList);
    }
}
