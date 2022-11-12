package top.hellocode.dto;


import lombok.Data;
import top.hellocode.entity.OrderDetail;
import top.hellocode.entity.Orders;

import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
