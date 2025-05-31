package com.kujudy.springbootmall.service;

import com.kujudy.springbootmall.dto.CreateOrderRequest;
import com.kujudy.springbootmall.dto.OrderQueryParms;
import com.kujudy.springbootmall.model.Order;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;


public interface OrderService {

    Integer createOrder(Integer userId,CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParms orderQueryParms);

    Integer countOrder(OrderQueryParms orderQueryParms);
}
