package com.baizhi.service;

import com.baizhi.entity.Article;
import com.baizhi.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    //分页
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //开始条数
        Integer start = (page-1)*rows;
        //总条数
        Integer count = articleMapper.count();
        //总页数
        Integer total = count%rows==0?count/rows:count/rows+1;
        List<Article> articles = articleMapper.queryAll(start, rows);
        map.put("total",total);//总页数
        map.put("rows",articles);//页面中展示的数据
        map.put("page",page);//当前页数
        map.put("records",count);//总条数
        return map;
    }

    //修改
    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

    //添加
    @Override
    public void save(Article article) {
        articleMapper.save(article);
    }
}
