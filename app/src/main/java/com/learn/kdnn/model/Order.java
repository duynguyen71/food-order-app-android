package com.learn.kdnn.model;

import java.util.Date;
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
public class Order {

    private int orderId;
    private Date orderDate;
    private Date deliveryDate;
    private List<Product> products;
    private User user;


}
