package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author Moyu
 * @version 1.0
 * @description: 维护店铺状态
 * @date 2024/5/12 15:09
 */
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺状态相关接口")
public class ShopController {

    private static final String KEY="SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("更新店铺状态")
    @PutMapping("/{status}")
    public Result updateStatus(@PathVariable Integer status){
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }

    @GetMapping("/status")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        return Result.success(status);
    }
}
