package com.kujudy.springbootmall.dao;

import com.kujudy.springbootmall.dto.OrderQueryParms;
import com.kujudy.springbootmall.model.Order;
import com.kujudy.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {


    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);


    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    List<Order> getOrders(OrderQueryParms orderQueryParms);

    Integer countOrder(OrderQueryParms orderQueryParms);
}
