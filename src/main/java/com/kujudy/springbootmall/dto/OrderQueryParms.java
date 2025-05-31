package com.kujudy.springbootmall.dto;

import com.kujudy.springbootmall.constant.ProductCategory;
import lombok.Data;

@Data
public class OrderQueryParms {
    private Integer userId;
    private Integer limit;
    private Integer offset;
}
