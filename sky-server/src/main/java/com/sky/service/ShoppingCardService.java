package com.sky.service;

import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Employee;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCardService {
    /**
     * 添加购物车
     * @param shoppingCartDTO
     * @return
     */
    void add(ShoppingCartDTO shoppingCartDTO);


    /**
     * 查询用户购物车列表
     * @return
     */
    List<ShoppingCart> list(Long userId);
}
