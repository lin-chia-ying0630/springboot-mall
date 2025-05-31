package com.kujudy.springbootmall.controller;

import com.kujudy.springbootmall.dto.CreateOrderRequest;
import com.kujudy.springbootmall.dto.OrderQueryParms;
import com.kujudy.springbootmall.model.Order;
import com.kujudy.springbootmall.model.OrderItem;
import com.kujudy.springbootmall.service.OrderService;
import com.kujudy.springbootmall.unit.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private LocalValidatorFactoryBean defaultValidator;
    @Autowired
    private InternalResourceViewResolver defaultViewResolver;

    @GetMapping("/users/{userId}/orders")
    public  ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "10") @Max(1000)@Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0)Integer offset){
        OrderQueryParms orderQueryParms= new OrderQueryParms();
        orderQueryParms.setUserId(userId);
        orderQueryParms.setLimit(limit);
        orderQueryParms.setOffset(offset);
        //取得Orderlist
        List<Order> orberList = orderService.getOrders(orderQueryParms);
        //取得order 總數
        Integer count= orderService.countOrder(orderQueryParms);
        //分頁
        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orberList);
        return  ResponseEntity.status(HttpStatus.OK).body(page);


    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId, @RequestBody @Valid CreateOrderRequest createOrderRequest) {
        Integer orderId =orderService.createOrder(userId,createOrderRequest);
        Order order=orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);

    }

}
