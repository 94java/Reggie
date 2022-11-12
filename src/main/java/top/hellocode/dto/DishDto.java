package top.hellocode.dto;

import lombok.Data;
import top.hellocode.entity.Dish;
import top.hellocode.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
