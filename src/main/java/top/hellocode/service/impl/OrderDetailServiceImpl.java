package top.hellocode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hellocode.entity.OrderDetail;
import top.hellocode.mapper.OrderDetailMapper;
import top.hellocode.service.IOrderDetailService;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月11日 15:41
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {
}
