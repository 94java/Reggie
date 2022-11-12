package top.hellocode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.hellocode.common.CustomException;
import top.hellocode.entity.Setmeal;
import top.hellocode.entity.SetmealDish;
import top.hellocode.mapper.SetmealMapper;
import top.hellocode.service.ISetmealDishService;
import top.hellocode.service.ISetmealService;

import java.util.Arrays;
import java.util.List;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月08日 15:21
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements ISetmealService {
    @Autowired
    private ISetmealDishService setmealDishService;

    @Override
    public void deleteWithDish(Long[] ids) {
        // 判断当前套餐是否启售
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId, ids)
                .eq(Setmeal::getStatus, 1);
        int count = this.count(setmealLambdaQueryWrapper);
        if (count > 0) {
            // 启售则不能删除
            throw new CustomException("当前为启售状态，不能删除");
        }
        // 删除套餐基本信息
        this.removeByIds(Arrays.asList(ids));
        // 删除关联的菜品信息
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(queryWrapper);

    }
}
