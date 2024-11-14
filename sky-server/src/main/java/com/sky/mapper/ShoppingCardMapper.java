package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCardMapper {
    /**
     * 查询菜品是否存在  某个人，某个菜是否存在
     * @param
     * @return
     */
    @Select("select count(*) from shopping_cart where user_id = #{userId} and dish_id = #{dishId}")
    Integer countDish(Long userId, Long dishId);

    @Select("select * from shopping_cart where user_id = #{userId} and dish_id = #{dishId}")
    List<ShoppingCart> listDish(Long userId, Long dishId);

    @Update("update shopping_cart set number = #{count} where id = #{id}")
    void updateNumber(Long id, Integer count);

    @Insert("insert into shopping_cart (number, amount, create_time, dish_id, user_id, name) VALUES (#{number},#{amount},#{createTime},#{dishId},#{userId},#{name})")
    void saveAdd(ShoppingCart shoppingCart);

    @Select("select * from shopping_cart where user_id = #{userId}")
    List<ShoppingCart> list(Long userId);

    /**
     * 根据用户id删除购物车数据
     * @param userId
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void deleteByUserId(Long userId);
}
