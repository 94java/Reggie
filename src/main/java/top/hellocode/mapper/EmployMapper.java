package top.hellocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.hellocode.entity.Employee;

/**
 * @author HelloCode
 * @site https://www.hellocode.top
 * @date 2022年11月06日 16:41
 */
@Mapper
public interface EmployMapper extends BaseMapper<Employee> {
}
