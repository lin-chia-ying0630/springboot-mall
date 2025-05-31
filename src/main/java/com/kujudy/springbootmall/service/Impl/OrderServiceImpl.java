package com.kujudy.springbootmall.service.Impl;

import com.kujudy.springbootmall.dao.OrderDao;
import com.kujudy.springbootmall.dao.ProductDao;
import com.kujudy.springbootmall.dao.UserDao;
import com.kujudy.springbootmall.dto.BuyItem;
import com.kujudy.springbootmall.dto.CreateOrderRequest;
import com.kujudy.springbootmall.dto.OrderQueryParms;
import com.kujudy.springbootmall.model.Order;
import com.kujudy.springbootmall.model.OrderItem;
import com.kujudy.springbootmall.model.Product;
import com.kujudy.springbootmall.model.User;
import com.kujudy.springbootmall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        //檢查USER 存不存在
        User user = userDao.getUserById(userId);
        if (user == null) {
            log.warn("該User不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());
            //
            if (product == null) {
                log.warn("商品{}不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品{}庫存{}<購賣數量{}", buyItem.getProductId(), product.getStock(), buyItem.getQuantity());

            }
            productDao.updateSock(product.getProductId(), product.getStock() - buyItem.getQuantity());
            //sum
            int amount = product.getPrice() * buyItem.getQuantity();
            totalAmount += amount;
            //轉換BuyItem = OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }

        //創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);
        orderDao.createOrderItems(orderId, orderItemList);
        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemlist = orderDao.getOrderItemsByOrderId(orderId);
        order.setOrderItemlist(orderItemlist);
        return order;
    }

    @Override
    public List<Order> getOrders(OrderQueryParms orderQueryParms) {
        List<Order> orderList = orderDao.getOrders(orderQueryParms);
        for (Order order : orderList) {
            List<OrderItem> orderItemlist = orderDao.getOrderItemsByOrderId(order.getOrderId());
            order.setOrderItemlist(orderItemlist);
        }
        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryParms orderQueryParms) {
        return orderDao.countOrder(orderQueryParms);
    }
}
