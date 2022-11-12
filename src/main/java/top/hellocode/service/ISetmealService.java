package top.hellocode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.hellocode.entity.Setmeal;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月08日 15:20
 */
public interface ISetmealService extends IService<Setmeal> {
    void deleteWithDish(Long[] ids);
}
