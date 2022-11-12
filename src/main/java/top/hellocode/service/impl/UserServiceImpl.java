package top.hellocode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hellocode.entity.User;
import top.hellocode.mapper.UserMapper;
import top.hellocode.service.IUserService;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月11日 14:18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
