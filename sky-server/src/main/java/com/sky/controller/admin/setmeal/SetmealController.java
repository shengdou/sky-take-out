package com.sky.controller.admin.setmeal;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Moyu
 * @version 1.0
 * @description: 套餐相关接口
 * @date 2024/5/9 21:50
 */
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关借口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping()
    @ApiOperation("新增套餐")
    @Transactional
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.save(setmealDTO);
        return Result.success();
    }

    @ApiOperation("套餐分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult pageResult=setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("根据id查询套餐")
    @GetMapping("/{id}")
    public Result<SetmealVO> selectById(@PathVariable Integer id) {
        SetmealVO setmealVO = setmealService.seletctById(id);
        return Result.success(setmealVO);
    }

    @ApiOperation("根据id删除套餐")
    @DeleteMapping()
    public Result deleteSetmeal(@RequestParam List<Integer> ids) {
        setmealService.delete(ids);
        return Result.success();
    }

    @ApiOperation("修改套餐")
    @PutMapping
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.update(setmealDTO);
        return Result.success();
    }

    @ApiOperation("套餐起售停售")
    @PostMapping("/status/{status}")
    public Result updateSetmealStatus(@PathVariable Integer status,  Long id) {
        setmealService.updateStatus(id,status);
        return Result.success();
    }
}
