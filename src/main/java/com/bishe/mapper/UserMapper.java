package com.bishe.mapper;

import com.bishe.entity.UserMap;

import java.util.List;

public interface UserMapper {
    //查询所有
    public List<UserMap> queryAll();
    //折线图
    public  List<Integer>  query();

}
