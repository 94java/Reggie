package top.hellocode.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.hellocode.common.BaseContext;
import top.hellocode.common.R;
import top.hellocode.entity.ShoppingCart;
import top.hellocode.service.IShoppingCartService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月11日 15:03
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private IShoppingCartService shoppingCartService;

    /**
     * @Description: 添加到购物车
     * @param: shoppingCart
     * @return R<String>
     **/
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        // 获取用户id
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        // 查询当前菜品或套餐是否在购物车中
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);
        if(dishId != null){
            // 当前为菜品
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
            // 当前为套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart cart = shoppingCartService.getOne(queryWrapper);
        if(cart != null){
            // 当前菜品或套餐在购物车中，数量加一
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartService.updateById(cart);
        }else {
            // 如果不存在，则添加到购物车，默认数量为1
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cart = shoppingCart;
        }
        return R.success(cart);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userId);
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }

    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);
        ShoppingCart cart = null;

        // 获取要减少的菜品或套餐信息
        Long dishId = shoppingCart.getDishId();
        if (dishId == null) {
            // 减少的是套餐数量
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        } else {
            // 减少的是菜品数量
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        }
        cart = shoppingCartService.getOne(queryWrapper);
        Integer number = cart.getNumber();
        if (number > 1) {
            cart.setNumber(number - 1);
            shoppingCartService.updateById(cart);
        } else {
            shoppingCartService.removeById(cart.getId());
        }
        return R.success(cart);
    }

    @DeleteMapping("/clean")
    public R<String> delete(){
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        shoppingCartService.remove(queryWrapper);
        return R.success("删除成功");
    }
}
