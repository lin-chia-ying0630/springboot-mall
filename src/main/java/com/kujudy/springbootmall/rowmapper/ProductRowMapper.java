package com.kujudy.springbootmall.rowmapper;

import com.kujudy.springbootmall.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Product product = new Product();
        product.setProductId(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));
        product.setCategory(resultSet.getString("category"));
        product.setImageUrl(resultSet.getString("image_url"));
        product.setPrice(resultSet.getInt("price"));
        product.setStork(resultSet.getInt("stork"));
        product.setDescription(resultSet.getString("description"));
        product.setCreateDate(resultSet.getTimestamp("create_date"));
        product.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));
        return product;
    }
}
