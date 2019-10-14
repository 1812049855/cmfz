package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    //分页查询
    public Map<String,Object> queryPage(Integer page, Integer rows,String albumId);
    //增删改
    public String edit(Chapter chapter, String oper,String albumId);
    //修改路径
    public void updatePath(String id,String path);
    //修改时间大小
    void  update(String time,String size,String id);
}
