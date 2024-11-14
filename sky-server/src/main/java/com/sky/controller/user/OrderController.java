package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Orders;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/order")
@Api(tags = "订单相关接口")
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;

    /**
     * 用户下单
     * @param
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<String> submit(@RequestBody OrdersSubmitDTO shoppingCartDTO){
        orderService.submit(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PostMapping("/payment")
    @ApiOperation("订单支付")
    public Result<String> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        orderService.payment(ordersPaymentDTO);
        return Result.success("支付成功");
    }

    /**
     * 查询订单状态 - 用户所有的订单
     *
     * @param userId, status
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("订单支付")
    public Result<List<Orders>> userOrder(Long userId, Integer status) throws Exception {
        List<Orders> orders = orderService.orderList(userId, status);
        return Result.success(orders);
    }
}
