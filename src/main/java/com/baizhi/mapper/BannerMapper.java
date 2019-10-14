package com.baizhi.mapper;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerMapper {
    //分页查询
    public List<Banner> queryPage(@Param("start") Integer start,@Param("rows") Integer rows);
    //查询总条数
    public  Integer count();
    //添加数据
    public void addBanner(Banner banner);  //添加数据
    //修改路径
    public void updatePath(@Param("id") String id,@Param("path") String path);
    void update(Banner banner);  //修改数据
    void deleteBanner(String[] ids);//批量删除
    //查询所有
    public  List<Banner> queryAll();
}
