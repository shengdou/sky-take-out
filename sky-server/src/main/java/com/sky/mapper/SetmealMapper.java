package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);


    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);


/**
 * @description: 根据dishId查找套餐数量
 * @param:
 * @return:
 * @author Moyu
 * @date: 2024/4/29 22:01
 */
    Integer countByDishId(List<Long> ids);

    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);



    @Select("select * from setmeal where id=#{id}")
    Setmeal selectById(Long id);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);


    @AutoFill(OperationType.INSERT)
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into setmeal(id, category_id, name, price, status, description, image, create_time, update_time, create_user, update_user) " +
            "values (#{id},#{categoryId},#{name},#{price},#{status},#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Setmeal setmeal);



    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    Integer countByIdStatus(List<Integer> ids);


    void deleteSetmealBat(List<Integer> ids);


    @Update("update setmeal set status=#{status} where id =#{id}")
    void updateStatus(Long id, Integer status);

}
