package com.sky.controller.admin.dish;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import com.sky.entity.Dish;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.List;

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

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    @PostMapping
    @Transactional
    @ApiOperation("新增菜品接口")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品Controller:{}",dishDTO);
        dishService.save(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分页查询接口")
    public Result<PageResult> page( DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询{}",dishPageQueryDTO);
        return Result.success(dishService.pageQuery(dishPageQueryDTO));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> findById(@PathVariable Long id){
        log.info("根据id查询菜品{}",id);
        DishVO dishVO=dishService.findById(id);
        return Result.success(dishVO);
    }

    @DeleteMapping
    @ApiOperation("菜品批量删除")
    @Transactional
    public Result delete(@RequestParam List<Long> ids){
        log.info("菜品删除{}",ids);
        dishService.delete(ids);
        return Result.success();
    }

    /**
     * @description: 修改菜品
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/4/29 15:36
     */
    @PutMapping
    @Transactional
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    @Transactional
    public Result updateStatus( Long id, @PathVariable Integer status){
        log.info("菜品起售停售 {},{}",id,status);
        dishService.updateStatus(id,status);
        return Result.success();
    }
}
