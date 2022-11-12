package top.hellocode.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import top.hellocode.common.R;
import top.hellocode.entity.Employee;
import top.hellocode.service.IEmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author HelloCode
 * @site https://www.hellocode.top
 * @date 2022年11月06日 16:41
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        // 根据用户名查询
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(lqw);
        // 密码加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(Objects.isNull(emp)){
            return R.error("用户名不存在，请重新输入");
        }
        // 判断密码是否正确

        if(!emp.getPassword().equals(password)){
            return R.error("密码错误，请重新输入");
        }
        // 判断账号状态
        if(emp.getStatus() == 0){
            return R.error("账号被禁用，请联系管理员");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> add(@RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employeeService.save(employee);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name){
        Page<Employee> pageInfo = new Page<>(page,pageSize);
         LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper();
        lqw.like(!StringUtils.isEmpty(name),Employee::getName,name);
        Page<Employee> res = employeeService.page(pageInfo, lqw);
        return R.success(res);
    }
    @GetMapping("/{id}")
    public R<Employee> get(@PathVariable Long id){
        Employee emp = employeeService.getById(id);
        return R.success(emp);
    }

    @PutMapping
    public R<String> update(@RequestBody Employee employee){
        employeeService.updateById(employee);
        return R.success("修改成功！");
    }
}
