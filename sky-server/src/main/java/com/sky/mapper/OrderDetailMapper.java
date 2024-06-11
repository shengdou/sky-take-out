package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Moyu
 * @version 1.0
 * @description: 订单详细表mapper
 * @date 2024/6/10 20:36
 */
@Mapper
public interface OrderDetailMapper {



    void insertBat(List<OrderDetail> orderDetails);
}
