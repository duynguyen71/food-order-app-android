package com.learn.kdnn.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Comment {

    private long id;
    private String username;
    private String userId;
    private String commentText;
    private Date date;

}
