package com.baizhi.mapper;

import com.baizhi.entity.UserMap;

import java.util.List;

public interface UserMapper {
    //查询所有
    public List<UserMap> queryAll();
    //折线图
    public  List<Integer>  query();

}
