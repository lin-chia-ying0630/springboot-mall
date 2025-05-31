package com.kujudy.springbootmall.dao.Impl;

import com.kujudy.springbootmall.dao.OrderDao;
import com.kujudy.springbootmall.dto.OrderQueryParms;
import com.kujudy.springbootmall.model.Order;
import com.kujudy.springbootmall.model.OrderItem;
import com.kujudy.springbootmall.rowmapper.OrderItemRowMapper;
import com.kujudy.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order` (user_id,total_amount,created_date,last_modified_date) values(:userId,:totalAmount,:createdDate,:lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);
        map.put("createdDate", new Date());
        map.put("lastModifiedDate", new Date());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int orderId = keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {

        String sql = "INSERT INTO order_item (order_id ,product_id, quantity, amount) values(:orderId,:productId,:quantity,:amount)";
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId", orderId);
            parameterSources[i].addValue("productId", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount", orderItem.getAmount());
        }
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);


    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "select order_id,user_id,total_amount,created_date,last_modified_date from `order` where order_id=:orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        if (orderList.size() != 0) {
            return orderList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "select oi.order_item_id,oi.order_id,oi.product_id,oi.quantity,oi.amount,p.product_name,p.image_url " +
                "from order_item  as oi " +
                "LEFT JOIN product as p ON oi.product_id =p.product_id" +
                " where oi.order_id=:orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
        return orderItemList;
    }

    @Override
    public List<Order> getOrders(OrderQueryParms orderQueryParms) {
        String sql = "select order_id,user_id,total_amount,created_date,last_modified_date from `order`where 1=1";
        Map<String,Object> map = new HashMap<>();
        //查詢條件
        sql = addFilteringSql(sql,map,orderQueryParms);
        //排序
        sql = sql +" ORDER BY created_date DESC";
        //分頁
        sql = sql +" LIMIT :limit OFFSET :offset";
        map.put("limit", orderQueryParms.getLimit());
        map.put("offset", orderQueryParms.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryParms orderQueryParms) {
        String sql = "select count(*) from `order`where 1=1";
        Map<String,Object> map = new HashMap<>();
        sql = addFilteringSql(sql,map,orderQueryParms);
        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return total;
    }

    private String addFilteringSql(String sql, Map<String, Object> map, OrderQueryParms orderQueryParms) {
        if (orderQueryParms.getUserId() != null) {
            sql = sql + " AND user_id = :userId";
            map.put("userId", orderQueryParms.getUserId());
        }
        return sql;
    }
}
