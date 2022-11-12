package top.hellocode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hellocode.entity.Employee;
import top.hellocode.mapper.EmployMapper;
import top.hellocode.service.IEmployeeService;

/**
 * @author HelloCode
 * @site https://www.hellocode.top
 * @date 2022年11月06日 16:42
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployMapper, Employee> implements IEmployeeService {
}
