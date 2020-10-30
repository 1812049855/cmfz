package com.bishe.service;

import com.bishe.entity.UserMap;

import java.util.List;

public interface UserSercvice {
    public List<UserMap> queryAll();
    //折线图
    public  List<Integer>  query();

}
