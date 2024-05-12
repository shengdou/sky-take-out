package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

/**
 * @author Moyu
 * @version 1.0
 * @description: 套餐Service
 * @date 2024/5/9 21:56
 */
public interface SetmealService {

    public void save(SetmealDTO setmealDTO);
    public SetmealVO seletctById(Integer id);


    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void delete(List<Integer> ids);

    void update(SetmealDTO setmealDTO);

    void updateStatus(Long id, Integer status);
}
