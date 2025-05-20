package com.kujudy.springbootmall.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class Product {
    private Integer productId;
    private String productName;
    private String category;
    private String imageUrl;
    private Integer price;
    private Integer stork;
    private String description;
    private Date createDate;

    private Date lastModifiedDate;
}
