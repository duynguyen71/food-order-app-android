package com.learn.kdnn.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    private int id;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String avatarUrl;
    private Date dateCreated;
    private List<ShippingAddress> shippingAddress;

}
