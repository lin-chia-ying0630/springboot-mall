package com.kujudy.springbootmall.dto;

import com.kujudy.springbootmall.constant.ProductCategory;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class ProductQueryParams {
    private ProductCategory category;
    private String search;
    private String orderBy;
    private String sort;
}
