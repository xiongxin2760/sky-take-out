package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.ShoppingCardMapper;
import com.sky.result.Result;
import com.sky.service.ShoppingCardService;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCard")
@Api(tags = "C端用户相关接口")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCardService shoppingCardService;
    /**
     * 加入购物车
     * @param
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("加入购物车")
    public Result<String> add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shoppingCardService.add(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 获取购物车列表
     * @param
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("购物车列表")
    public Result<List<ShoppingCart>> list(Long userId){
        List<ShoppingCart> itemList = shoppingCardService.list(userId);
        return Result.success(itemList);
    }

}
