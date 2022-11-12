package top.hellocode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.hellocode.entity.Category;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月08日 14:58
 */
public interface ICategoryService extends IService<Category> {
    public void remove(Long ids);
}
