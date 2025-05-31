package com.kujudy.springbootmall.service.Impl;

import com.kujudy.springbootmall.dao.OrderDao;
import com.kujudy.springbootmall.dao.ProductDao;
import com.kujudy.springbootmall.dto.BuyItem;
import com.kujudy.springbootmall.dto.CreateOrderRequest;
import com.kujudy.springbootmall.model.Order;
import com.kujudy.springbootmall.model.OrderItem;
import com.kujudy.springbootmall.model.Product;
import com.kujudy.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        int totalAmount = 0;
        List<OrderItem> orderItemList=new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product=productDao.getProductById(buyItem.getProductId());
            int amount=product.getPrice()*buyItem.getQuantity();
            totalAmount+=amount;
            //轉換BuyItem = OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }

        //創建訂單
        Integer orderId = orderDao.createOrder(userId,totalAmount);
        orderDao.createOrderItems(orderId,orderItemList);
        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order =orderDao.getOrderById(orderId);
        List<OrderItem> orderItemlist= orderDao.getOrderItemsByOrderId(orderId);
        order.setOrderItemlist(orderItemlist);
        return order;
    }
}
