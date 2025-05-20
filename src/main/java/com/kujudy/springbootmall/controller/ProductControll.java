package com.kujudy.springbootmall.controller;

import com.kujudy.springbootmall.dao.ProductDao;
import com.kujudy.springbootmall.model.Product;
import com.kujudy.springbootmall.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductControll {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId, HttpServletResponse httpServletResponse) {
        Product product=productService.getProductById(productId);
        if(product!=null){
            return  ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
