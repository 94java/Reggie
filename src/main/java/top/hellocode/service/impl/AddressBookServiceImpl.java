package top.hellocode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hellocode.entity.AddressBook;
import top.hellocode.mapper.AddressBookMapper;
import top.hellocode.service.IAddressBookService;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月11日 15:33
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements IAddressBookService {
}
