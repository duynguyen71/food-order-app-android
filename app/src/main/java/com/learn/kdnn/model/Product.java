package com.learn.kdnn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    private int id;
    private String name;
    private double price;
    private double discountPer;
    private String category;
    private String imgUri;
}
