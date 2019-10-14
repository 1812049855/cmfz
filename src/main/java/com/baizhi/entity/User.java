package com.baizhi.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
public class User {
    private String id;
    private  String username;
    private  String password;
    private Date   creatDate;
    private  String province;
    private String name;
}
