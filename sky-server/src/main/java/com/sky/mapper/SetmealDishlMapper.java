package com.sky.mapper;

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
public interface SetmealDishlMapper {

    @Select("select setmeal_id from setmeal_dish where dish_id=#{id}")
    List<Long> countByDishId(Long id);
}
