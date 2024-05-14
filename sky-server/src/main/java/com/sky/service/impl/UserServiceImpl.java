package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @author Moyu
 * @version 1.0
 * @description: C端用户Service
 * @date 2024/5/13 16:57
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";


    @Override
    public User login(UserLoginDTO userLoginDTO) {
        //获取openid
        String openId = getOpenId(userLoginDTO.getCode());

        //认证不通过
        if (openId == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        // 判断是否为新用户
        User user = userMapper.getUserByopenid(openId);
        if(user==null){
             user = User.builder()
                    .openid(openId)
                     .createTime(LocalDateTime.now())
                    .build();
             userMapper.insertUser(user);
        }

        return user;
    }

    //获取openid
    private String getOpenId(String code){
        //参数构建
        HashMap<String, String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("grant_type","authorization_code");
        map.put("js_code",code);

        //发送请求获取openid
        String json = HttpClientUtil.doGet(WX_LOGIN, map);

        //获取openid
        String openid = (String) JSONObject.parseObject(json).get("openid");
        return openid;
    }
}
