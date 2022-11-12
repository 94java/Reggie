package top.hellocode.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.hellocode.common.R;
import top.hellocode.dto.SetmealDto;
import top.hellocode.entity.Category;
import top.hellocode.entity.Setmeal;
import top.hellocode.entity.SetmealDish;
import top.hellocode.service.ICategoryService;
import top.hellocode.service.ISetmealDishService;
import top.hellocode.service.ISetmealService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月10日 20:14
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private ISetmealService setmealService;
    @Autowired
    private ISetmealDishService setmealDishService;
    @Autowired
    private ICategoryService categoryService;
    @PostMapping
    public R<String> add(@RequestBody SetmealDto setmealDto){
        // 保存套餐基本信息
        setmealService.save(setmealDto);
        // 保存套餐关联菜品信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            // 设置套餐id
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setmealDishService.saveBatch(setmealDishes);
        return R.success("添加成功");
    }
    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name){
        // 查询套餐基本信息
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(name),Setmeal::getName,name);
        setmealService.page(pageInfo, queryWrapper);
        // 构造套餐dto，封装分类名称信息
        Page<SetmealDto> dtoPage = new Page<>();
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<SetmealDto> dtoList = new ArrayList<>();
        for (Setmeal record : pageInfo.getRecords()) {
            SetmealDto setmealDto = new SetmealDto();
            Category category = categoryService.getById(record.getCategoryId());
            setmealDto.setCategoryName(category.getName());
            BeanUtils.copyProperties(record,setmealDto);
            dtoList.add(setmealDto);
        }
        dtoPage.setRecords(dtoList);
        // 返回dto给页面
        return R.success(dtoPage);
    }

    @DeleteMapping
    public R<String> delete(Long[] ids){
        setmealService.deleteWithDish(ids);
        return R.success("删除成功");
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status,Long[] ids){
        List<Setmeal> setmeals = new ArrayList<>();
        for (Long id : ids) {
            Setmeal setmeal = new Setmeal();
            setmeal.setId(id);
            setmeal.setStatus(status);
            setmeals.add(setmeal);
        }
        setmealService.updateBatchById(setmeals);
        return R.success("修改状态成功");
    }

    @GetMapping("/list")
    public R<List> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getCategoryId,setmeal.getCategoryId());
        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }
}
