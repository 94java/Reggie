package top.hellocode.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.hellocode.common.R;
import top.hellocode.entity.Category;
import top.hellocode.service.ICategoryService;

import java.util.List;


/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月08日 14:57
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    /**
     * @Description: 添加分类
     * @param: category
     * @return R<String>
     **/
    @PostMapping
    public R<String> save(@RequestBody Category category){
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        categoryService.save(category);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize){
        Page<Category> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);
        Page<Category> res = categoryService.page(pageInfo, lqw);
        return R.success(res);
    }

    @DeleteMapping
    public R<String> remove(Long id){
        categoryService.remove(id);
        return R.success("删除成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Integer type){
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(type != null,Category::getType,type);
        List<Category> list = categoryService.list(lqw);
        return R.success(list);
    }
}
