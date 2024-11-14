package com.sky.service;

import com.sky.entity.Dish;

import java.util.List;

public interface DishService {
    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);
}
