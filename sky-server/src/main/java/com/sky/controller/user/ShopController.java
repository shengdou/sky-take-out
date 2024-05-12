package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author Moyu
 * @version 1.0
 * @description: 维护店铺状态
 * @date 2024/5/12 15:09
 */
@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
@Api(tags = "店铺状态相关接口")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String KEY="SHOP_STATUS";

    @ApiOperation("更新店铺状态")
    @PutMapping("/{status}")
    public Result updateStatus(@PathVariable Integer status){
        log.info("更新店铺状态，状态为：",status==1?"营业中":"打样中");
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }

    @GetMapping("/status")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取店铺状态，状态为：",status==1?"营业中":"打样中");
        return Result.success(status);
    }
}
