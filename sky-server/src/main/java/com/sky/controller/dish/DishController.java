package com.sky.controller.dish;

import com.sky.dto.DishDTO;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author Moyu
 * @version 1.0
 * @description: 菜品接口
 * @date 2024/4/25 21:38
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    @Transactional
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品Controller:{}",dishDTO);
        dishService.save(dishDTO);
        return Result.success();
    }
}
