package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Moyu
 * @version 1.0
 * @description: TODO
 * @date 2024/4/25 22:05
 */
@Mapper
public interface DishFlavorMapper {

    void insetBat(List<DishFlavor> flavors);

    void deleteByDish(List<Long> ids);

    @Select("select * from dish_flavor where dish_id=#{id}")
    List<DishFlavor> queryByDishId(Long id);
}
