package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 动态条件查询菜品
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 根据套餐id查询菜品
     * @param setmealId
     * @return
     */
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);

    @AutoFill(OperationType.INSERT)
    @Options(useGeneratedKeys = true,keyProperty ="id")
    @Insert("insert into dish(id, name, category_id, price, image, description, status, create_time, update_time, create_user, update_user) " +
            "values (#{id},#{name},#{categoryId},#{price},#{image},#{description},#{status},#{createTime}," +
            "#{updateTime},#{createUser},#{updateUser})")
    void insert(Dish dish);

    /**
     * @description: 菜品分页查询
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/4/29 11:53
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

/**
 * @description: 根据菜品id与状态查找菜品数量
 * @param:
 * @return:
 * @author Moyu
 * @date: 2024/4/29 11:54
 */
    Integer selectByIdAndStatus(List<Long> ids);

/**
 * @description: 根据id批量删除菜品
 * @param:
 * @return:
 * @author Moyu
 * @date: 2024/4/29 11:53
 */
    void deleteBat(List<Long> ids);

    @Select("select * from dish where dish.id=#{id}")
    DishVO queryById(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);
}
