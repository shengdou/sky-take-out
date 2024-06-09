package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

/**
 * @author Moyu
 * @version 1.0
 * @description: 购物车服务类
 * @date 2024/5/22 0:19
 */
public interface ShoppingCartService {

    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

}
