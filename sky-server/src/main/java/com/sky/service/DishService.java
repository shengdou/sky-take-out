package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * @author Moyu
 * @version 1.0
 * @description: TODO
 * @date 2024/4/25 21:40
 */
public interface DishService {

    public void save(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void delete(List<Long> ids);

    DishVO findById(Long id);

    void updateWithFlavor(DishDTO dishDTO);

    void updateStatus(Long id, Integer status);
}
