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
public class User {

    private int id;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String avatarUrl;
    private Date dateCreated;
    private String address;
    private List<String> addresses;

}
