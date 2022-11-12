package top.hellocode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.hellocode.entity.Orders;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月11日 15:39
 */
public interface IOrderService extends IService<Orders> {
    void submit(Orders orders);
}
