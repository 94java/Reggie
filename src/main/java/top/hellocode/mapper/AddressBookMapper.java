package top.hellocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.hellocode.entity.AddressBook;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月11日 15:33
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
