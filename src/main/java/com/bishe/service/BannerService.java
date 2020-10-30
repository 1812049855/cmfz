package com.bishe.service;

import com.bishe.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    //分页查询
    public Map<String,Object> queryPage(Integer page, Integer rows);
    //增删改
    public  String edit(String id,String oper,String title,String status,String desc,String path);
    //修改路径
    public void updatePath(String id,String path);
    //查询所有
    public  List<Banner> queryAll();
}
