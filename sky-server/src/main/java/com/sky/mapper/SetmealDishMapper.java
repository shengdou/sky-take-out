package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Moyu
 * @version 1.0
 * @description: TODO
 * @date 2024/4/29 22:17
 */
@Mapper
public interface SetmealDishMapper {

    @Select("select setmeal_id from setmeal_dish where dish_id=#{id}")
    List<Long> countByDishId(Long id);

    @Select("select * from setmeal_dish where setmeal_id=#{id}")
    List<SetmealDish> selectDishById(Long id);
    /**
     * @description: 插入套餐-菜品表
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/5/11 9:29
     */
    void insertSetMealDishBat(List<SetmealDish> setmealDishes);

    void deleteSetmealDishBat(List<Integer> ids);

}
