package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 保存订单
     * @param orders
     */
    Long insert(Orders orders);

    /**
     * 查询订单
     * @param id
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 设置订单状态
     * @param id, status, payStatus
     */
    @Update("update orders set status = #{status}, pay_status = #{payStatus} where id = #{id}")
    void setOrderStatus(Long id, Integer status, Integer payStatus);

    /**
     * 设置订单状态
     * @param userId, status
     */
    @Select("select * from orders where user_id = #{userId} and status = #{status}")
    List<Orders> getOrderList(Long userId, Integer status);
}
