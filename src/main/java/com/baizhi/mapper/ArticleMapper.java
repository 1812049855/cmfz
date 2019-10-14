package com.baizhi.mapper;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    //分页查询
    public List<Article> queryAll(@Param("start") Integer start, @Param("rows") Integer rows);
    //查询总条数
    public  Integer count();
    //修改
    public void update(Article article);
    //添加
    public  void save(Article article);
}
