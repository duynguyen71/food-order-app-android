package com.learn.kdnn.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {

    private long id;
    private String name;
    private double price;
    private double discountPer;
    private String category;
    private String primaryImgUrl;
    private List<String> thumbnailImgUrl;
}
