package top.hellocode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hellocode.common.CustomException;
import top.hellocode.entity.Category;
import top.hellocode.entity.Dish;
import top.hellocode.entity.Setmeal;
import top.hellocode.mapper.CategoryMapper;
import top.hellocode.service.ICategoryService;
import top.hellocode.service.IDishService;
import top.hellocode.service.ISetmealService;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月08日 14:58
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
    @Autowired
    private IDishService dishService;
    @Autowired
    private ISetmealService setmealService;
    /**
     * @Description: 分类移除
     * @param: ids
     * @return void
     **/
    @Override
    public void remove(Long ids) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,ids);
        LambdaQueryWrapper<Setmeal> sermealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sermealLambdaQueryWrapper.eq(Setmeal::getCategoryId,ids);
        if(dishService.count(dishLambdaQueryWrapper) > 0){
            throw new CustomException("当前分类关联商品不为空，删除失败");
        }
        if(setmealService.count(sermealLambdaQueryWrapper) > 0){
            throw new CustomException("当前分类关联套餐不为空，删除失败");
        }
        removeById(ids);
    }
}
