package com.kujudy.springbootmall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer userId;
    private String email;
    @JsonIgnore
    private String password;
    private Date createdDate;
    private Date lastModifiedDate;
}
