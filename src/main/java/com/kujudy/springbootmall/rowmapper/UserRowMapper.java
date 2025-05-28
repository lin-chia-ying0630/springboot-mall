package com.kujudy.springbootmall.rowmapper;

import com.kujudy.springbootmall.model.User;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {


    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();

        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setCreatedDate(resultSet.getTimestamp("created_date"));
        user.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));
        return user;
    }
}
