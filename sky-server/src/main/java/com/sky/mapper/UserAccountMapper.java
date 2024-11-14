package com.sky.mapper;

import com.sky.entity.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserAccountMapper {
    /**
     * 查询账户信息
     * @param userId
     * @return
     */
    @Select("select * from user_account where user_id = #{userId}")
    UserAccount getByUserId(Long userId);

    /**
     * 更新账户信息
     * @param userAccount
     * @return
     */
    void update(UserAccount userAccount);
}
