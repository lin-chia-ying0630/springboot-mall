package com.kujudy.springbootmall.dto;

import com.kujudy.springbootmall.constant.ProductCategory;
import lombok.Data;

@Data
public class ProductQueryParams {
    private ProductCategory category;
    private String search;
}
