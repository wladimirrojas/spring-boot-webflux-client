package com.example.springboot.webflux.client.app.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Product {

    private String id;
    private String name;
    private Double price;
    private LocalDateTime createdAt;
    private String picture;
    private Category category;
}
