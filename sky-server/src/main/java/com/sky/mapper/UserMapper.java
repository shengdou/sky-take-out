package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * @author Moyu
 * @version 1.0
 * @description: C端用户相关mapper
 * @date 2024/5/13 17:09
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where openid=#{openid}")
    User getUserByopenid(String openid);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into user(id, openid, name, phone, sex, id_number, avatar, create_time)" +
            "values (#{id},#{openid},#{name},#{phone},#{sex},#{idNumber},#{avatar},#{createTime})")
    void insertUser(User user);
}
