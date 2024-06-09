package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Moyu
 * @version 1.0
 * @description: 购物车相关mapper
 * @date 2024/5/22 0:23
 */
@Mapper
public interface ShoppingCartMapper {

    /**
     * @description: 根据套餐id或菜品id或用户id或口味查询
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/5/22 0:41
     */
    public List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * @description: 更新购物车份数
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/6/9 9:17
     */
    @Update("update shopping_cart set number=#{number} where id=#{id}")
    void updateAmountById(ShoppingCart cart);

    @Insert("insert into shopping_cart(name, image, user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time)" +
            "values (#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{createTime})")
    void insert(ShoppingCart cart);

    //清空购物车
    @Delete("delete from shopping_cart where user_id=#{uid}")
    void clear(Long uid);

    /**
     * 根据id删除购物车数据
     * @param id
     */
    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);
}
