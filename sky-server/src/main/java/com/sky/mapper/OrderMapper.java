package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * @author Moyu
 * @version 1.0
 * @description: TODO
 * @date 2024/6/10 16:58
 */
@Mapper
public interface OrderMapper {


    void insert(Orders orders);
}
