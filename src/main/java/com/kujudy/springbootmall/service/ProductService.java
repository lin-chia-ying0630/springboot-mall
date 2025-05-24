package com.kujudy.springbootmall.service;

import com.kujudy.springbootmall.constant.ProductCategory;
import com.kujudy.springbootmall.dto.ProductQueryParams;
import com.kujudy.springbootmall.dto.ProductRequest;
import com.kujudy.springbootmall.model.Product;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductId(Integer productId);

    List<Product> getProducts(ProductQueryParams productQueryParams);
}
