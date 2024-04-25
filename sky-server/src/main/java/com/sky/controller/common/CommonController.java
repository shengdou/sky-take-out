package com.sky.controller.common;

import com.sky.config.AliOSSConfig;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Moyu
 * @version 1.0
 * @description: 处理公共请求接口
 * @date 2024/4/25 12:46
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "处理公共请求接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @ApiOperation("文件上传接口")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("处理文件上传与回显{}", file);

        //上传云服务器
        try {
            String originalFilename = file.getOriginalFilename();
            //获取文件类型
            String fileType = originalFilename.substring(originalFilename.indexOf('.'));
            //文件名
            String objectName = UUID.randomUUID().toString() + fileType;
            return Result.success(aliOssUtil.upload(file.getBytes(),objectName));
        } catch (IOException e) {
            log.info("文件上传失败{}",e);
        }
        return null;
    }
}
