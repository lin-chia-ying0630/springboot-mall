package com.kujudy.springbootmall.dao.Impl;

import com.kujudy.springbootmall.dao.UserDao;
import com.kujudy.springbootmall.dto.UserRegisterRequest;
import com.kujudy.springbootmall.model.User;
import com.kujudy.springbootmall.rowmapper.UserRowMapper;
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
public class UserDaoImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User getUserById(Integer userId) {
        String sql="select user_id,email,password,created_date,last_modified_date from user where user_id=:userId";
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("userId",userId);
        List<User> userlist =namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if (userlist.size()>0){
            return userlist.get(0);
        }else{
            return null;
        }
    }
    @Override
    public User getUserByEmail(String email) {
        String sql="select user_id,email,password,created_date,last_modified_date from user where email=:email";
        Map<String,Object> map=new HashMap<String,Object>();

        map.put("email",email);
        List<User> userlist =namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());
        if (userlist.size()>0){
            return userlist.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO user(email, password, created_date, last_modified_date) " +
                "VALUES (:email, :password, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());
        map.put("createdDate", new Date());
        map.put("lastModifiedDate", new Date());  // <-- 改這裡

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }




}
