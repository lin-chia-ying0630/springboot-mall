package com.kujudy.springbootmall.dto;

import com.kujudy.springbootmall.constant.ProductCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {
    //private Integer productId;-->前端會傳輸不用因為自動生成
    @NotNull
    private String productName;
    @NotNull
    private ProductCategory category;
    @NotNull
    private String imageUrl;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;

    private String description;
}