package com.baizhi.service;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface AlbumService {
    //分页查询
    public Map<String,Object> queryPage(Integer page, Integer rows);
    //增删改
    public String edit(String id, String title,String score, String cover, String author,String beam,Integer count, String content,String oper);
    //修改路径
    public void updatePath(String id,String path);

}
