package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User getById(Long id);

    @Select("select * from user where openid = #{userId}")
    User getByUserId(String userId);
}
