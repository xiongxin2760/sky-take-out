package com.sky.mapper;

import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    @Select("select * from dish where category_id = #{type}")
    List<Dish> queryByCategoryType(Long type);

    @Select("select * from dish where id = #{id}")
    Dish getDishById(Long id);
}
