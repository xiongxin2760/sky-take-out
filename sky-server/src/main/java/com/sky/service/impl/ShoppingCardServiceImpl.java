package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.ShoppingCardMapper;
import com.sky.service.ShoppingCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCardServiceImpl implements ShoppingCardService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private ShoppingCardMapper shoppingCardMapper;

    public void add(ShoppingCartDTO shoppingCartDTO) {
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCarts = shoppingCardMapper.listDish(userId, shoppingCartDTO.getDishId());
        if (!CollectionUtils.isEmpty(shoppingCarts)) { // 查询到的历史数据
            ShoppingCart shoppingCart = shoppingCarts.get(0);
            // 更新数据
            Integer number = shoppingCart.getNumber() + 1;
            shoppingCardMapper.updateNumber(shoppingCart.getId(), number);
            return;
        }
        // 不存在这种菜，需要新增
        ShoppingCart shoppingCart = new ShoppingCart();
        Dish dish = dishMapper.getDishById(shoppingCartDTO.getDishId());
        shoppingCart.setNumber(1);
        shoppingCart.setAmount(dish.getPrice());
        shoppingCart.setCreateTime((LocalDateTime.now()));
        shoppingCart.setDishId(dish.getId());
        shoppingCart.setUserId(userId);
        shoppingCart.setName(dish.getName());

        shoppingCardMapper.saveAdd(shoppingCart);
    }
    public List<ShoppingCart> list(Long userId) {
        return shoppingCardMapper.list(userId);
    }

}
