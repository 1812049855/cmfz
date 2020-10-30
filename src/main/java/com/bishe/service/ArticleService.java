package com.bishe.service;

import com.bishe.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    //分页查询
    public Map<String,Object> queryAll(Integer page, Integer rows);
    //修改
    public void update(Article article);
    //添加
    public  void save(Article article);
    //操作es
    public List<Article> queryByEs(String val);
}
