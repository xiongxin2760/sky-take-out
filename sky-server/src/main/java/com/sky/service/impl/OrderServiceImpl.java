package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.entity.UserAccount;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.BussinessException;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCardMapper;
import com.sky.mapper.UserAccountMapper;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.beans.beancontext.BeanContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ShoppingCardMapper shoppingCardMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    UserAccountMapper userAccountMapper;

    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {
        // 获取购物车中的所有内容
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCartList = shoppingCardMapper.list(userId);

        // 生成order大订单
        BigDecimal amount = BigDecimal.valueOf(0);
        for (ShoppingCart item : shoppingCartList) {
            amount = amount.add(item.getAmount().multiply(BigDecimal.valueOf(item.getNumber())));
        }

        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setUserId(userId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayMethod(Orders.Platform);
        orders.setPayStatus(Orders.UN_PAID);
        orders.setAmount(amount);

        // 保存大订单
        Long orderId = orderMapper.insert(orders);

        // 生成order_detail
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart item : shoppingCartList) {
            // 将 item 转换为 OrderDetail
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setName(item.getName());
            orderDetail.setOrderId(orderId);
            orderDetail.setDishId(item.getDishId());
            orderDetail.setNumber(item.getNumber());
            orderDetail.setAmount(item.getAmount().multiply(BigDecimal.valueOf(item.getNumber())));
            orderDetails.add(orderDetail);
        }
        // 插入order_detail
        orderDetailMapper.insertBatch(orderDetails);

        // 删除购物车
        shoppingCardMapper.deleteByUserId(userId);

        // 生成返回结构
        OrderSubmitVO orderSubmitVO = new OrderSubmitVO();
        orderSubmitVO.setId(orderId);
        orderSubmitVO.setOrderAmount(orders.getAmount());
        orderSubmitVO.setOrderTime(orders.getOrderTime());
        return orderSubmitVO;
    }

    public List<Orders> orderList(Long userId, Integer status) {
        if (status == null) {
            status = 0;
        }
        return orderMapper.getOrderList(userId, status);
    }

    public void payment(OrdersPaymentDTO ordersPaymentDTO) {
        Long userId = BaseContext.getCurrentId();
        // 获取用户账户
        UserAccount userAccount = userAccountMapper.getByUserId(userId);
        if (userAccount == null) {
            throw new BussinessException(MessageConstant.USER_ACCOUNT_NOT_FOUND);
        }

        // 查询订单信息
        Orders orders = orderMapper.getById(ordersPaymentDTO.getOrderNumber());
        if (orders == null) {
            throw new BussinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        BigDecimal amount = orders.getAmount();
        // 付款
        if (userAccount.getAmount().compareTo(amount) <= 0) {
            throw new BussinessException(MessageConstant.USER_ACCOUNT_NOT_BALANCE);
        }
        userAccount.setAmount(userAccount.getAmount().subtract(amount));

        // 账户信息写回
        userAccountMapper.update(userAccount);

        // 改变订单状态
        orderMapper.setOrderStatus(orders.getId(), Orders.COMPLETED, Orders.PAID);
        return;
    }
}
