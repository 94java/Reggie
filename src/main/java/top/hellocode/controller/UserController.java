package top.hellocode.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.hellocode.common.R;
import top.hellocode.entity.User;
import top.hellocode.service.IUserService;
import top.hellocode.service.impl.UserServiceImpl;

import javax.servlet.http.HttpSession;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月11日 14:20
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    /**
     * @Description: 用户登录
     * @param: user
     * @return R<String>
     **/
    @PostMapping("/login")
    public R<String> login(@RequestBody User user, HttpSession session){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,user.getPhone());
        User user1 = userService.getOne(queryWrapper);
        if(user1 == null){
            // 用户没有注册过，注册
            userService.save(user);
            session.setAttribute("user",user.getId());
        }
        session.setAttribute("user",user1.getId());
        return R.success("登录成功");
    }
}
