package top.hellocode.dto;


import lombok.Data;
import top.hellocode.entity.Setmeal;
import top.hellocode.entity.SetmealDish;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
