package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {
    //分页查询
    public Map<String,Object> queryAll(Integer page, Integer rows);
    //修改
    public void update(Article article);
    //添加
    public  void save(Article article);
}
